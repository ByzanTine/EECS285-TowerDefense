package umich.eecs285.test;

import umich.eecs285.towerdefence.Messager;
import umich.eecs285.towerdefence.TowerDefensedataArray;
import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefenseObject;
import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefense_TransData;

public class MessagerServerExample extends Thread {
  public void run() {
    // initialization
    System.out.println("Client0: initialization " + System.currentTimeMillis());
    Messager messager = new Messager(Messager.Id_Server);
    messager.initialization();
    while(!messager.ifNextRoundReady()) {
      System.out.println("Client0: Wait for NextRoundReady " + System.currentTimeMillis());
      try {
        Thread.sleep(50); // wait 50 ms
      } catch (InterruptedException e) {
        e.printStackTrace();
      } 
      messager.transmitRoundReady();
      // do something with messager.getReceivedData()
      System.out.println(messager.getReceivedData().toString());
    }
    TowerDefense_TransData towerDefense_TransData;
    
    for (int i = 0; i < 3; i++) { // 3 rounds
      long nextRoundStartTime = messager.getNextRoundStartTime();
      // wait till nextRoundStartTime
      while (System.currentTimeMillis() < nextRoundStartTime) {
        System.out.println("Client0: wait till nextRoundStartTime " + System.currentTimeMillis());
        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      
      // round start!
      System.out.println("Client0: round start! " + System.currentTimeMillis());
      for (int k = 0; k < 3; k++) {
        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        // some fake data
        towerDefense_TransData = new TowerDefense_TransData(Messager.Id_Server, 5, TowerDefensedataArray.Transmit_Type_Regular);
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
      System.out.println("Client0: round end " + System.currentTimeMillis());
      // sending out some units
      towerDefense_TransData = new TowerDefense_TransData(Messager.Id_Server, 2, TowerDefensedataArray.Transmit_Type_New_Round);
      // some fake data
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
      messager.transmitRoundReady(towerDefense_TransData);
      // do something with messager.getReceivedData()
      System.out.println(messager.getReceivedData().toString());
      while(!messager.ifNextRoundReady()) {
        System.out.println("Client0: wait for NextRoundReady " + System.currentTimeMillis());
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
