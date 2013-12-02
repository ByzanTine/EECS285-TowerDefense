package umich.eecs285.towerdefence;

import java.io.IOException;
import java.util.Timer;

import javax.swing.JFrame;

import com.sun.org.apache.bcel.internal.generic.InstructionConstants.Clinit;

import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefense_TransData;

public class TowerDefenseGame extends Thread implements TowerDefensedataArray {
  public static long Preparation_Time = 10000;
  public static int delay = 40;

  private Controller control;
  private TowerDefenseDataBase DB;
  private ClientBridge client_bridge;
  private MainFrame mainFrame;
  private Player player;
  private int turn;
  private long timestamp;

  private TowerDefense_TransData towerDefense_TransData;
  private TowerDefense_TransData opponentData;
  private TowerDefense_TransData receivedData=null;
  private boolean draw_state = false;
  private Messager messager;
  private byte clientId;

  public static void main(String args[]) {

    TowerDefenseGame game = new TowerDefenseGame();
    game.initConnection();

    while (!game.checkInitalBridge()) {
      try {
        Thread.sleep(delay);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Try Create");
    }

  }

  public void setClientId(byte clientId) {
    this.clientId = clientId;
  }

  public void initConnection() {
    this.mainFrame = new MainFrame();
    mainFrame.turnOnInput();
    this.client_bridge = new ClientBridge();
    this.mainFrame.setClientBridge(client_bridge);
  }

  private void init() {

    messager = new Messager(clientId);
    // TODO change client side initialization to get ip from player
    mainFrame.start();
    if (clientId == Messager.Id_Server) {
      messager.initialization();
      mainFrame.turnOnRound("Wait player to join: " + messager.getServerIp());
    } else {
      String serverIp = client_bridge.getIp();
      messager.initialization(serverIp);
    }
    player = new Player();
    turn = 1;
    control = new Controller();
    control.init();
    DB = new TowerDefenseDataBase();
    DB.init();
    receivedData = null;
  }

  private void setTimestamp() {
    timestamp = System.currentTimeMillis();
  }

  public void run() {
    init();
    
    for (; turn < 9; turn++) {
      // Round start
      setTimestamp();
      while (!messager.ifNextRoundReady()) {
        System.out.println("Client: Wait for NextRoundReady "
            + System.currentTimeMillis());
        try {
          Thread.sleep(delay); // wait delay ms
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        setTimestamp();
        checkRunBridge();
        messager.transmitRoundReady();
        if (messager.getReceivedData().getTransmitType() == Transmit_Type_Regular)
          opponentData = messager.getReceivedData();
        else if (messager.getReceivedData().getSize() > 0)
          receivedData = messager.getReceivedData();
        // paint data
        if (draw_state == false && towerDefense_TransData != null)
          mainFrame.nextFrame(towerDefense_TransData);
        if (draw_state == true && opponentData != null)
          mainFrame.nextFrame(opponentData);
      }
      if (clientId == Messager.Id_Server)
        mainFrame.turnOffRound();
      long nextRoundStartTime = messager.getNextRoundStartTime();
      // wait till nextRoundStartTime
      mainFrame.turnOnRound("Round" + turn);
      while (System.currentTimeMillis() < nextRoundStartTime) {
        System.out.println("Client: wait till nextRoundStartTime "
            + System.currentTimeMillis());
        try {
          Thread.sleep(delay);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      mainFrame.turnOffRound();
      
      // Preparation time
      while (System.currentTimeMillis() < nextRoundStartTime + Preparation_Time) {
        System.out
            .println("Client: round start! " + System.currentTimeMillis());
        setTimestamp();
        checkprepBridge();
        towerDefense_TransData = control.getInfo(clientId, timestamp);
        System.out.println(towerDefense_TransData.toString());
        towerDefense_TransData.setTransmitType(Transmit_Type_Regular);
        messager.transmitRegularData(towerDefense_TransData);
        if (messager.getReceivedData().getTransmitType() == Transmit_Type_Regular)
          opponentData = messager.getReceivedData();
        else if (messager.getReceivedData().getSize() > 0)
          receivedData = messager.getReceivedData();

        // paint data
        if (draw_state == false)
          mainFrame.nextFrame(towerDefense_TransData);
        if (draw_state == true)
          mainFrame.nextFrame(opponentData);

      }

      // Running
    
      if(receivedData!=null){
        try {
          System.out.println(JSONUtility.arrayToJSON(receivedData));
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
    	  int attackingUnits[]=new int[receivedData.getSize()];
    	  for(int i=0;i<receivedData.getSize();i++){
    		  attackingUnits[i]=receivedData.TowerDefense_TransArray[i].getId();
    	  }
    	  control.startTurn(turn, attackingUnits.length, attackingUnits);
    	receivedData = null;
      }
      else{
    	  control.startTurn(turn, 0, null);
      }
      while (!control.isEnd()) {
        setTimestamp();
        checkRunBridge();
        control.run();
        towerDefense_TransData = control.getInfo(clientId, timestamp);
        towerDefense_TransData.setTransmitType(Transmit_Type_Regular);
        messager.transmitRegularData(towerDefense_TransData);
        if (messager.getReceivedData().getTransmitType() == Transmit_Type_Regular)
          opponentData = messager.getReceivedData();
        else if (messager.getReceivedData().getSize() > 0)
          receivedData = messager.getReceivedData();

        // paint
        if (draw_state == false)
          mainFrame.nextFrame(towerDefense_TransData);
        if (draw_state == true)
          mainFrame.nextFrame(opponentData);

        System.out.println(towerDefense_TransData.toString());
        if (control.isDead()) {
          cushion();
          break;
        }
      }

      // round end
      System.out.println("Client: round end " + System.currentTimeMillis());
      cushion();
      control.endTurn();
      cushion();
      messager.transmitRoundReady(player.getAttackingData(clientId));
      if (messager.getReceivedData().getTransmitType() == Transmit_Type_Regular)
        opponentData = messager.getReceivedData();
      else if (messager.getReceivedData().getSize() > 0)
        receivedData = messager.getReceivedData();
      player.addCandy(1, control.hasReachedKing());
      
    }
    // TODO player automatically increase money
  }

  private void cushion() {
    final int CushionRound = 16;
    for (int i = 0; i < CushionRound; i++) {
      try {
        Thread.sleep(delay);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      checkRunBridge();
      control.run();
      towerDefense_TransData = control.getInfo(clientId, timestamp);
      towerDefense_TransData.setTransmitType(Transmit_Type_Regular);
      messager.transmitRegularData(towerDefense_TransData);
      if (messager.getReceivedData().getTransmitType() == Transmit_Type_Regular)
        opponentData = messager.getReceivedData();
      else if (messager.getReceivedData().getSize() > 0)
        receivedData = messager.getReceivedData();
      // paint
      if (draw_state == false)
        mainFrame.nextFrame(towerDefense_TransData);
      if (draw_state == true)
        mainFrame.nextFrame(opponentData);
    }
  }

  private void checkprepBridge() {
	client_bridge.setCandy(player.getCandy());
	client_bridge.setMoney(player.getMoney());
    // TODO Auto-generated method stub
    if (client_bridge.isCreateAttackUnitRequest()) {
      if (player.canCreateAttackingUnit(client_bridge.getAttackUnitId())) {
        player.createAttackingUnit(client_bridge.getAttackUnitId());
        client_bridge.setCreateAttackUnitRequest(false);
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
    
    if (client_bridge.isUnitLevelupRequest()) {
      if (player.canUpdateUnit(client_bridge.getLevelupId())) {
        player.updateUnit(client_bridge.getLevelupId());
        control.levelUp(client_bridge.getLevelupId());
       
      }
    }
    
    if (client_bridge.isCreateAttackUnitRequest()) {
        if (player.canCreateAttackingUnit(client_bridge.getAttackUnitId())) {
          player.createAttackingUnit(client_bridge.getAttackUnitId());
          client_bridge.setCreateAttackUnitRequest(false);
        }
      }
      if (client_bridge.isMeoMeoNumIncreaseRequest()) {
        if (player.canCreateMeoMeo()) {
          player.createMeoMeo();
          client_bridge.setMeoMeoNumIncreaseRequest(false);
        }
      }
      if (client_bridge.isMeoMeoTechUpgradeRequest()) {
        if (player.canUpdateMeoMeo()) {
          player.updateMeoMeo();
          client_bridge.setMeoMeoNumIncreaseRequest(false);
        }
      }
      if (client_bridge.isChangeViewRequest()) {
        draw_state = (!draw_state);
        client_bridge.setChangeViewRequest(false);
      }
  }

  public boolean checkInitalBridge() {
	  
    if (client_bridge.isCreateGameRequest()) {

      client_bridge.setGameCreated(true);
      client_bridge.setCreateGameRequest(false);
      setClientId(Messager.Id_Server);
      start();
      return true;

    }
    if (client_bridge.isJoinGameRequest()) {
      try {
        sleep(1000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      client_bridge.setGameConnected(true);
      setClientId(Messager.Id_Client);
      start();
      return true;

    }
    return false;
  }

  private void checkRunBridge() {
	client_bridge.setCandy(player.getCandy());
	client_bridge.setMoney(player.getMoney());
    // TODO Auto-generated method stub
    if (client_bridge.isCreateAttackUnitRequest()) {
      if (player.canCreateAttackingUnit(client_bridge.getAttackUnitId())) {
        player.createAttackingUnit(client_bridge.getAttackUnitId());
        client_bridge.setCreateAttackUnitRequest(false);
      }
    }
    if (client_bridge.isMeoMeoNumIncreaseRequest()) {
      if (player.canCreateMeoMeo()) {
        player.createMeoMeo();
        client_bridge.setMeoMeoNumIncreaseRequest(false);
      }
    }
    if (client_bridge.isMeoMeoTechUpgradeRequest()) {
      if (player.canUpdateMeoMeo()) {
        player.updateMeoMeo();
        client_bridge.setMeoMeoNumIncreaseRequest(false);
      }
    }
    if (client_bridge.isChangeViewRequest()) {
      draw_state = (!draw_state);
      client_bridge.setChangeViewRequest(false);
    }

  }
}
