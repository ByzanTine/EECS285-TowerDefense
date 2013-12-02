package umich.eecs285.test;

import java.io.IOException;

import umich.eecs285.towerdefence.ClientWrapper;
import umich.eecs285.towerdefence.ServerMessager;
import umich.eecs285.towerdefence.TowerDefenseGame;
import umich.eecs285.towerdefence.TowerDefensedataArray;
import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefenseObject;
import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefense_TransData;

public class ServerTest {
//  // clientWrapper Test
//  public static void main(String [] args)
//  {
//    for (int i = 0; i < 100000; i++) {
//      try
//      {
//         Thread t = new ClientWrapper(args[0], args[1], Integer.parseInt(args[2]), Integer.toString(i));
//         t.start();
//         Thread.sleep(10);
//      } catch(IOException e) {
//         e.printStackTrace();
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      } 
//    }
//  }
  
//  // ServerWrapper Test
//  public static void main(String [] args)
//  {
//     int port = Integer.parseInt(args[0]);
//     try
//     {
//        Thread t = new ServerWrapper(port);
//        t.start();
//     }catch(IOException e)
//     {
//        e.printStackTrace();
//     }
//  }
  
  // JSONUtility Test
//  public static void main(String [] args)
//  {
//    TowerDefense_TransData towerDefense_TransData = new TowerDefense_TransData(0, 123456, 100, TowerDefensedataArray.Transmit_Type_Regular);
//    String json = null;
//    for(int i = 0; i < towerDefense_TransData.getSize(); i++) {
//      towerDefense_TransData.TowerDefense_TransArray[i] = new TowerDefenseObject();
//      int id = i;
//      int life = i*2;
//      int x = i*3;
//      int y = i*4;
//      int action = i*5;
//      towerDefense_TransData.TowerDefense_TransArray[i].setId(id);
//      towerDefense_TransData.TowerDefense_TransArray[i].setLife(life);
//      towerDefense_TransData.TowerDefense_TransArray[i].setX(x);
//      towerDefense_TransData.TowerDefense_TransArray[i].setY(y);
//      towerDefense_TransData.TowerDefense_TransArray[i].setAction(action);
//    }
//    try {
//      json = JSONUtility.arrayToJSON(towerDefense_TransData);
//      System.out.println(json);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    towerDefense_TransData = JSONUtility.JOSNToArray(json);
//    System.out.println(towerDefense_TransData.getClientId());
//    System.out.println(towerDefense_TransData.getTimeStamp());
//    for(int i = 0; i < towerDefense_TransData.getSize(); i++) {
//      System.out.println(towerDefense_TransData.TowerDefense_TransArray[i].toString());
//    }
//  }
  
//  // Server Test
//  public static void main(String args[]) {
//    ServerMessager sm;
//    ClientWrapper cw0;
//    ClientWrapper cw1;
//    TowerDefense_TransData t1, t2;
//    try {
//      sm = new ServerMessager(8888);
//      sm.start();
//      String serverIp = sm.getHostIp();
//      
//      while (true) {
//        Thread.sleep(50);
//        cw0 = new ClientWrapper(0, serverIp, "8888");
//        cw1 = new ClientWrapper(1, serverIp, "8888");
//        t1 = TowerDefense_TransData.createEmptyTransData(0, TowerDefensedataArray.Transmit_Type_Regular);
//      
//        t2 = new TowerDefense_TransData(1, 50, TowerDefensedataArray.Transmit_Type_Regular);
//        for (int i = 0; i < t2.getSize(); i++) {
//          t2.TowerDefense_TransArray[i] = new TowerDefenseObject();
//          int id = i;
//          int life = i*2;
//          int x = i*3;
//          int y = i*4;
//          int action = i*5;
//          t2.TowerDefense_TransArray[i].setId(id);
//          t2.TowerDefense_TransArray[i].setLife(life);
//          t2.TowerDefense_TransArray[i].setX(x);
//          t2.TowerDefense_TransArray[i].setY(y);
//          t2.TowerDefense_TransArray[i].setAction(action);
//        }
//        cw0.setTransData(t1);
//        cw0.transmitData();
//
//        cw1.setTransData(t2);
//        cw1.transmitData();
//        
//        System.out.println(cw0.getReceiveData().toString());
//        System.out.println(cw1.getReceiveData().toString());
//      }
//    } catch (IOException e) {
//      e.printStackTrace();
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//  }
  // Messager Test
//  public static void main(String args[]) {
//    MessagerServerExample mse = new MessagerServerExample();
//    MessagerClientExample mce = new MessagerClientExample();
//    
//    mse.start();
//    try {
//      Thread.sleep(50);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//    mce.start();
//  }
  public static void main(String args[]) {

    TowerDefenseGame gameServer = new TowerDefenseGame();
    gameServer.initConnection();

    while (!gameServer.checkInitalBridge()) {
      try {
        Thread.sleep(TowerDefenseGame.delay);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Try Create");
    }
    
    TowerDefenseGame gameClient = new TowerDefenseGame();
    gameClient.initConnection();

    while (!gameClient.checkInitalBridge()) {
      try {
        Thread.sleep(TowerDefenseGame.delay);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Try Create");
    }

  }
  
}
