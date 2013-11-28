package umich.eecs285.towerdefence;

import java.io.IOException;

import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefense_TransData;
/**
 * Interface used to transmit data
 * @author Jiaxi Luo
 *
 */

public class ClientWrapper {
  private String serverIp;
  private String port;
  private int id;
  private TowerDefense_TransData towerDefense_TransData;
  private TowerDefense_TransData receiveTowerDefense_TransData;
  
  public ClientWrapper(int id, String serverIp, String port, TowerDefense_TransData towerDefense_TransData) {
    this.serverIp = serverIp;
    this.port = port;
    this.id = id;
    this.towerDefense_TransData = towerDefense_TransData;
  }
  
  public ClientWrapper(int id, String serverIp, String port) {
    this.serverIp = serverIp;
    this.port = port;
    this.id = id;
  }
  
  public synchronized void setTransData(TowerDefense_TransData towerDefense_TransData) {
    this.towerDefense_TransData = towerDefense_TransData;
  }
  
  public synchronized TowerDefense_TransData getTransData() {
    return towerDefense_TransData;
  }
  
  public synchronized void setReceiveData(TowerDefense_TransData receiveTowerDefense_TransData) {
    this.receiveTowerDefense_TransData = receiveTowerDefense_TransData;
  }
  
  public synchronized TowerDefense_TransData getReceiveData() {
    return receiveTowerDefense_TransData;
  }
  
  public void transmitData() {
    try {
      ClientMessager clientMessager = new ClientMessager( serverIp, port, id, JSONUtility.arrayToJSON(getTransData()) );
      clientMessager.start();
      String receiveMessage = clientMessager.getReceiveMessage();
      setReceiveData(JSONUtility.JOSNToArray(receiveMessage));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
}
