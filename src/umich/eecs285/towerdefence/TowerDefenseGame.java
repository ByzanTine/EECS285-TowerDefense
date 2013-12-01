package umich.eecs285.towerdefence;

import java.util.Timer;

import javax.swing.JFrame;

import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefense_TransData;

public class TowerDefenseGame extends Thread implements TowerDefensedataArray {
  public static long Preparation_Time = 10000;
  
  public static void main(String args[]) {
    TowerDefenseGame gameSever = new TowerDefenseGame();
    TowerDefenseGame gameClient = new TowerDefenseGame();
    gameSever.setClientId(Messager.Id_Server);
    gameClient.setClientId(Messager.Id_Client);
    gameSever.start();
    try {
      Thread.sleep(50);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    gameClient.start();
  }

  private Controller control;
  private TowerDefenseDataBase DB;
  private ClientBridge client_bridge;
  private MainFrame mainFrame;
  private Player player;
  private int turn;
  private long timestamp;

  private TowerDefense_TransData towerDefense_TransData;
  private TowerDefense_TransData opponentData;
  private boolean draw_state=false;
  private Messager messager;
  private byte clientId;

  public void setClientId(byte clientId) {
    this.clientId = clientId;
  }

  private void init() {
    messager = new Messager(clientId);
    // TODO change client side initialization to get ip from player
    messager.initialization();
    mainFrame = new MainFrame();
    mainFrame.setVisible(true);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    player = new Player();
    turn = 1;
    control = new Controller();
    control.init();
    DB = new TowerDefenseDataBase();
    DB.init();
    client_bridge = new ClientBridge();
    mainFrame.setClientBridge(client_bridge);
  }

  private void setTimestamp() {
    timestamp = System.currentTimeMillis();
  }

  public void run() {
    init();

    // Round start
    setTimestamp();
    while (!messager.ifNextRoundReady()) {
      System.out.println("Client: Wait for NextRoundReady "
          + System.currentTimeMillis());
      try {
        Thread.sleep(50); // wait 50 ms
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      messager.transmitRoundReady();
      // do something with messager.getReceivedData()
      System.out.println(messager.getReceivedData().toString());
    }

    long nextRoundStartTime = messager.getNextRoundStartTime();
    // wait till nextRoundStartTime
    while (System.currentTimeMillis() < nextRoundStartTime) {
      System.out.println("Client: wait till nextRoundStartTime "
          + System.currentTimeMillis());
      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    // Preparation time
    while (System.currentTimeMillis() < nextRoundStartTime + Preparation_Time) {
      System.out.println("Client: round start! " + System.currentTimeMillis());
      setTimestamp();
      checkprepBridge();
      towerDefense_TransData = control.getInfo(clientId, timestamp);
      System.out.println(towerDefense_TransData.toString());
      towerDefense_TransData.setTransmitType(Transmit_Type_Regular);
      messager.transmitRegularData(towerDefense_TransData);
      opponentData = messager.getReceivedData();

      // paint data
      if(draw_state==false)
    	  mainFrame.nextFrame(towerDefense_TransData);
      if(draw_state==true)
    	  mainFrame.nextFrame(opponentData);
    	  
    }

    // Running
    int[] attackingUnits = player.getAttackingId();
    control.startTurn(turn, attackingUnits.length, attackingUnits);
    while (!control.isEnd()) {
      setTimestamp();
      checkRunBridge();
      control.run();
      towerDefense_TransData = control.getInfo(clientId, timestamp);
      towerDefense_TransData.setTransmitType(Transmit_Type_Regular);
      messager.transmitRegularData(towerDefense_TransData);
      opponentData = messager.getReceivedData();
      
      
       // paint
      if(draw_state==false)
    	  mainFrame.nextFrame(towerDefense_TransData);
      if(draw_state==true)
    	  mainFrame.nextFrame(opponentData);
     

      System.out.println(towerDefense_TransData.toString());
      if (control.isDead()) {
        cushion();
        break;
      }
    }
    
    cushion();
    // round end
    System.out.println("Client: round end " + System.currentTimeMillis());
    
    control.endTurn();
    cushion();
    player.addCandy(1, control.hasReachedKing());
    // TODO player automatically increase money
  }
  
  private void cushion() {
    final int CushionRound = 16;
    for (int i = 0; i < CushionRound; i++) {
      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      control.run();
      towerDefense_TransData = control.getInfo(clientId, timestamp);
      towerDefense_TransData.setTransmitType(Transmit_Type_Regular);
      messager.transmitRegularData(towerDefense_TransData);
      opponentData = messager.getReceivedData();
      // paint
      if(draw_state==false)
    	  mainFrame.nextFrame(towerDefense_TransData);
      if(draw_state==true)
    	  mainFrame.nextFrame(opponentData);
    }
  }

  private void checkprepBridge() {

    // TODO Auto-generated method stub
    if (client_bridge.isCreateAttackUnitRequest()) {
      if (player.canCreateAttackingUnit(client_bridge.getAttackUnitId())) {
        player.createAttackingUnit(client_bridge.getAttackUnitId());

      }
    }
    if (client_bridge.isCreateUnitRequest()) {

      if (player.canCreateUnit(client_bridge.getId())) {
        System.out.println("Can Create ");
        if (control.addUnit(client_bridge.getId(), client_bridge.getX(),
            client_bridge.getY(), 1)) {

          player.createUnit(client_bridge.getId());
        }
      }
      client_bridge.setCreateUnitRequest(false);
    }
    if (client_bridge.isMeoMeoNumIncreaseRequest()) {
      if (player.canCreateMeoMeo()) {
        player.createMeoMeo();
      }
    }
    if (client_bridge.isMeoMeoTechUpgradeRequest()) {
      if (player.canUpdateMeoMeo()) {
        player.updateMeoMeo();
      }
    }
    if (client_bridge.isUnitLevelupRequest()) {
      if (player.canUpdateUnit(client_bridge.getLevelupId())) {
        player.updateUnit(client_bridge.getLevelupId());
        control.levelUp(client_bridge.getLevelupId());
      }
    }
    if(client_bridge.isChangeViewRequest()){
    	draw_state=(!draw_state);
    	client_bridge.setChangeViewRequest(false);
    }
  }

  private void checkRunBridge() {
    // TODO Auto-generated method stub
    if (client_bridge.isCreateAttackUnitRequest()) {
      if (player.canCreateAttackingUnit(client_bridge.getAttackUnitId())) {
        player.createAttackingUnit(client_bridge.getAttackUnitId());

      }
    }
    if (client_bridge.isMeoMeoNumIncreaseRequest()) {
      if (player.canCreateMeoMeo()) {
        player.createMeoMeo();
      }
    }
    if (client_bridge.isMeoMeoTechUpgradeRequest()) {
      if (player.canUpdateMeoMeo()) {
        player.updateMeoMeo();
      }
    }
    if(client_bridge.isChangeViewRequest()){
    	draw_state=(!draw_state);
    	client_bridge.setChangeViewRequest(false);
    }

  }
}
