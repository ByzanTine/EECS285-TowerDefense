package umich.eecs285.towerdefence;

import java.io.IOException;
import java.net.UnknownHostException;

import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefense_TransData;

/**
 * This is the highest level abstraction of Client/Server Messager.
 * Main function should interact with this class.
 * @author Jiaxi Luo
 *
 */

public class Messager {
  public final static byte Id_Server = 0;
  public final static byte Id_Client = 1;
  public final static int Port = 8888;
  
  private byte id;
//  private int roundNumber;
  private boolean nextRoundReady;
  private long nextRoundStartTime;
//  private long nextRoundAttackingStartTime;
  private ServerMessager serverMessager;
  private ClientWrapper clientWrapper;
  private String serverIp;
  private TowerDefense_TransData towerDefense_TransData;
  
  public Messager(byte id) {
    this.id = id;
    if (id == Id_Server)
      try {
        serverMessager = new ServerMessager(Port);
      } catch (IOException e) {
        e.printStackTrace();
      }
    nextRoundReady = false;
  }
  
  public void initialization(String serverIp) {
    this.serverIp = serverIp;
    initialization();
  }
  
  public void initialization() {
    if (id == Id_Server) {
      serverMessager.start();
      try {
        serverIp = serverMessager.getHostIp();
      } catch (UnknownHostException e) {
        e.printStackTrace();
      }
    }
    clientWrapper = new ClientWrapper(id, serverIp, Integer.toString(Port));
    towerDefense_TransData = new TowerDefense_TransData(id, 0, TowerDefensedataArray.Transmit_Type_New_Round);
    clientWrapper.setTransData(towerDefense_TransData);
    clientWrapper.transmitData();
    checkNextRoundReady();
  }
  
  public boolean ifNextRoundReady() {
    return nextRoundReady;
  }
  
  public void transmitRoundReady() {
    nextRoundReady = false;
    clientWrapper = new ClientWrapper(id, serverIp, Integer.toString(Port));
    towerDefense_TransData = new TowerDefense_TransData(id, 0, TowerDefensedataArray.Transmit_Type_New_Round);
    clientWrapper.setTransData(towerDefense_TransData);
    clientWrapper.transmitData();
    checkNextRoundReady();
  }
  
  public void transmitRegularData(TowerDefense_TransData towerDefense_TransData) {
    this.towerDefense_TransData = towerDefense_TransData;
    this.towerDefense_TransData.setTransmitType(TowerDefensedataArray.Transmit_Type_Regular);
    if (this.towerDefense_TransData.getTimeStamp() == 0)
      this.towerDefense_TransData.setTimeStamp(System.currentTimeMillis());
    nextRoundReady = false;
    clientWrapper = new ClientWrapper(id, serverIp, Integer.toString(Port));
    clientWrapper.setTransData(this.towerDefense_TransData);
    clientWrapper.transmitData();
  }
  
  public long getNextRoundStartTime() {
    return nextRoundStartTime;
  }
  
  public String getServerIp() {
    return serverIp;
  }
  
  public String getServerPort() {
    return Integer.toString(Port);
  }
  
  
  private void checkNextRoundReady() {
    if (clientWrapper.getReceiveData().getTransmitType() == TowerDefensedataArray.Transmit_Type_New_Round_Ready) {
      nextRoundReady = true;
      nextRoundStartTime = clientWrapper.getReceiveData().getTimeStamp();
    }
  }
  
  
}






