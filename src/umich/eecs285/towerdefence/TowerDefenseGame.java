package umich.eecs285.towerdefence;

import java.util.Timer;

import javax.swing.JFrame;

import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefense_TransData;

public class TowerDefenseGame extends Thread implements TowerDefensedataArray {
  private static Controller control;
  private static TowerDefenseDataBase DB;
  private static ClientBridge client_bridge;
  private static MainFrame mainFrame;
  private static Player player;
  private static int turn;
  private static long timestamp;
  
  private static TowerDefense_TransData towerDefense_TransData;
  private static Messager messager;
  private static byte clientId;
  // TODO create game: Messager.Id_Server
  // TODO join game: Messager.Id_Client
  public void setClientId(byte clientId) {
    TowerDefenseGame.clientId = clientId;
  }

  private void init() {
    messager = new Messager(clientId);
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

  public static void main(String args[]) {
    TowerDefenseGame game = new TowerDefenseGame();
    game.start();
  }
  
  public void run() {
    init();
    
    // Round 1
    setTimestamp();
    // Prep
    long prep = timestamp;
    long begin = timestamp;
    while (true) {
      setTimestamp();
      if (prep + 50 <= timestamp) {
        // Look up Bridge
        prep = timestamp;
        checkprepBridge();
        towerDefense_TransData = control.getInfo(clientId, timestamp);
        
        System.out.println(towerDefense_TransData.toString());
        // paint data
        mainFrame.nextFrame(towerDefense_TransData);
      }
      if (begin + 10000 <= timestamp) {
        // Time control will be determined by the Timer
        break;
      }

    }
    // Running
    int[] attackingUnits = player.getAttackingId();
    control.startTurn(turn, attackingUnits.length, attackingUnits);
    long run = timestamp;
    while (!control.isEnd()) {
      setTimestamp();
      if (run + 50 <= timestamp) {
        run = timestamp;
        checkRunBridge();
        control.run();
        towerDefense_TransData = control.getInfo(clientId, timestamp);
        // paint

        try {
          mainFrame.nextFrame(towerDefense_TransData);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        System.out.println(towerDefense_TransData.toString());
        if (control.isDead()) {
          for (int i = 0; i < 3000; i++) {

            control.run();
            towerDefense_TransData = control.getInfo(clientId, timestamp);
            // paint
            mainFrame.nextFrame(towerDefense_TransData);
          }
          break;
        }

      }
      // System.out.println(timestamp);
      // if (control.isDead()) {
      // break;
      // }
    }
    for (int i = 0; i < 3000; i++) {

      control.run();
      towerDefense_TransData = control.getInfo(clientId, timestamp);
      // paint
      mainFrame.nextFrame(towerDefense_TransData);
    }
    System.out.print("End Round");
    control.endTurn();
    player.addCandy(1, control.hasReachedKing());
    // TODO player automatically increase money
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

  }
}
