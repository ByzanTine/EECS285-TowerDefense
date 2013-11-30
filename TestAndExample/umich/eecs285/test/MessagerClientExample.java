package umich.eecs285.test;

import umich.eecs285.towerdefence.Messager;
import umich.eecs285.towerdefence.TowerDefensedataArray;
import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefenseObject;
import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefense_TransData;

public class MessagerClientExample {
  // TODO Local data! Subject to change!
  public final static String serverIp = "10.0.0.44"; 
  
  public static void main(String args[]) {
    // initialization
    System.out.println("initialization");
    Messager messager = new Messager(Messager.Id_Client);
    messager.initialization(serverIp);
    while(!messager.ifNextRoundReady()) {
      System.out.println("Wait for NextRoundReady");
      try {
        Thread.sleep(50); // wait 50 ms
      } catch (InterruptedException e) {
        e.printStackTrace();
      } 
      messager.transmitRoundReady();
    }
    long nextRoundStartTime = messager.getNextRoundStartTime();
    TowerDefense_TransData towerDefense_TransData;
    
    for (int i = 0; i < 5; i++) { // 5 rounds
      System.out.println("wait till nextRoundStartTime");
      // wait till nextRoundStartTime
      while (System.currentTimeMillis() < nextRoundStartTime) {
        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      
      // round start!
      System.out.println("round start!");
      for (int k = 0; k < 3; k++) {
        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        // some fake data
        towerDefense_TransData = new TowerDefense_TransData(Messager.Id_Client, 100, TowerDefensedataArray.Transmit_Type_Regular);
        for (int j = 0; j < towerDefense_TransData.getSize(); j++) {
          towerDefense_TransData.TowerDefense_TransArray[j] = new TowerDefenseObject();
          int id = j;
          int life = j*2;
          int x = j*3;
          int y = j*4;
          int action = j*5;
          towerDefense_TransData.TowerDefense_TransArray[j].setId(id);
          towerDefense_TransData.TowerDefense_TransArray[j].setLife(life);
          towerDefense_TransData.TowerDefense_TransArray[j].setX(x);
          towerDefense_TransData.TowerDefense_TransArray[j].setY(y);
          towerDefense_TransData.TowerDefense_TransArray[j].setAction(action);
        } // some fake data end
        messager.transmitRegularData(towerDefense_TransData);
        // do something with messager.getReceivedData()
        System.out.println(messager.getReceivedData().toString());
      }
      
      // round end
      System.out.println("round end");
      // sending out some units
      towerDefense_TransData = new TowerDefense_TransData(Messager.Id_Client, 10, TowerDefensedataArray.Transmit_Type_New_Round);
      // some fake data
      towerDefense_TransData = new TowerDefense_TransData(Messager.Id_Client, 100, TowerDefensedataArray.Transmit_Type_Regular);
      for (int j = 0; j < towerDefense_TransData.getSize(); j++) {
        towerDefense_TransData.TowerDefense_TransArray[j] = new TowerDefenseObject();
        int id = j;
        int life = j*2;
        int x = j*3;
        int y = j*4;
        int action = j*5;
        towerDefense_TransData.TowerDefense_TransArray[j].setId(id);
        towerDefense_TransData.TowerDefense_TransArray[j].setLife(life);
        towerDefense_TransData.TowerDefense_TransArray[j].setX(x);
        towerDefense_TransData.TowerDefense_TransArray[j].setY(y);
        towerDefense_TransData.TowerDefense_TransArray[j].setAction(action);
      } // some fake data end
      messager.transmitRegularData(towerDefense_TransData);
      while(!messager.ifNextRoundReady()) {
        System.out.println("wait for NextRoundReady");
        try {
          Thread.sleep(50); // wait 50 ms
        } catch (InterruptedException e) {
          e.printStackTrace();
        } 
        messager.transmitRoundReady();
      }
      // do something with messager.getReceivedData()
      System.out.println(messager.getReceivedData().toString());
    }
    
  }
}
