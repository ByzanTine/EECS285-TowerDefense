package umich.eecs285.towerdefence;

import java.net.*;
import java.io.*;

import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefense_TransData;

public class ServerMessager extends Thread {
  public final static int Num_Of_Clients = 2;
  private TowerDefense_TransData[] clientMessageBufferList;
  private boolean[] newRoundReady;
  private ServerSocket serverSocket;
  
  public ServerMessager(int port) throws IOException {
    // TODO figure out proper server settings
    serverSocket = new ServerSocket(port, 100000);
    serverSocket.setSoTimeout(10000);
    
    clientMessageBufferList = new TowerDefense_TransData[Num_Of_Clients];
    newRoundReady = new boolean[Num_Of_Clients];
    for (int i = 0; i < Num_Of_Clients; i++) {
      clientMessageBufferList[i] = TowerDefense_TransData.createEmptyTransData(i, TowerDefensedataArray.Transmit_Type_Regular);
      newRoundReady[i] = false;
    }
  }
  
  private boolean isNewRoundReady() {
    boolean result = false;
    for (int i = 0; i < Num_Of_Clients; i++) {
      result &= newRoundReady[i];
    }
    return result;
  }
  
  public String getHostIp() throws UnknownHostException {
    return InetAddress.getLocalHost().getHostAddress();
  }

  public void run() {
    while(true) {
    try {
      // initialize server
      InetAddress hostIp;
      hostIp = InetAddress.getLocalHost();
      System.out.println("Server: waiting for client on " + hostIp.getHostAddress() + ":" +
      serverSocket.getLocalPort() + "...");
      
      // setup connection
      Socket server = serverSocket.accept();
      
      // get connection
      System.out.println("Server: connected to "
           + server.getRemoteSocketAddress());
      
      // get input message
      DataInputStream in =
           new DataInputStream(server.getInputStream());
      TowerDefense_TransData towerDefense_TransData = JSONUtility.JOSNToArray(in.readUTF());
      clientMessageBufferList[towerDefense_TransData.getClientId()] = towerDefense_TransData;
      if (towerDefense_TransData.isNewRoundTransmit()) {
        newRoundReady[towerDefense_TransData.getClientId()] = true;
      }
      
      // TODO need to change for more than two players
      // send out output message
      int sendId;
      if (towerDefense_TransData.getClientId() == 1)
        sendId = 0;
      else
        sendId = 1;
      DataOutputStream out =
          new DataOutputStream(server.getOutputStream());
      out.writeUTF(JSONUtility.arrayToJSON(clientMessageBufferList[sendId]));
      clientMessageBufferList[sendId] = TowerDefense_TransData.createEmptyTransData(sendId, TowerDefensedataArray.Transmit_Type_Regular);
      
      // set new round ready signal
      if (isNewRoundReady()) {
        for (int i = 0; i < Num_Of_Clients; i++) {
          clientMessageBufferList[i].setTransmitType(TowerDefensedataArray.Transmit_Type_New_Round_Ready);
          clientMessageBufferList[i].setTimeStamp(System.currentTimeMillis() + 1000);
        }
      }
      
      // close server
      server.close();
    } catch(SocketTimeoutException s) {
        System.out.println("Socket timed out!");
        break;
      } catch (IOException e) {
        e.printStackTrace();
        break;
      }
    }
  }
}
