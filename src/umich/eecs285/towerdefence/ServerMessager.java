package umich.eecs285.towerdefence;

import java.net.*;
import java.io.*;

import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefense_TransData;

public class ServerMessager extends Thread {
  public final static int Num_Of_Clients = 2;
  private TowerDefense_TransData[] clientMessageBufferList;
  private TowerDefense_TransData[] nextRoundReadyBufferList;
  private boolean[] newRoundReady;
  private boolean[] waitForNewRound;
  private ServerSocket serverSocket;
  
  public ServerMessager(int port) throws IOException {
    // TODO figure out proper server settings
    serverSocket = new ServerSocket(port, 100000);
    serverSocket.setSoTimeout(1000000);
    
    clientMessageBufferList = new TowerDefense_TransData[Num_Of_Clients];
    nextRoundReadyBufferList = new TowerDefense_TransData[Num_Of_Clients];
    newRoundReady = new boolean[Num_Of_Clients];
    waitForNewRound = new boolean[Num_Of_Clients];
    for (int i = 0; i < Num_Of_Clients; i++) {
      clientMessageBufferList[i] = TowerDefense_TransData.createEmptyTransData(i, TowerDefensedataArray.Transmit_Type_Regular);
      nextRoundReadyBufferList[i] = null;
      newRoundReady[i] = false;
      waitForNewRound[i] = false;
    }
  }
  
  private boolean isNewRoundReady() {
    boolean result = true;
    for (int i = 0; i < Num_Of_Clients; i++) {
      result &= newRoundReady[i];
    }
    return result;
  }
  
  public String getHostIp() throws UnknownHostException {
    return InetAddress.getLocalHost().getHostAddress();
  }
  
  private void resetNewRoundReady() {
    for (int i = 0; i < Num_Of_Clients; i++) {
      newRoundReady[i] = false;
    }
  }

  public void run() {
    while(true) {
      try {
        // initialize server
        InetAddress hostIp;
        hostIp = InetAddress.getLocalHost();
        System.out.println("Server: waiting for client on " + hostIp.getHostAddress() + ":" +
        serverSocket.getLocalPort());
        
        // setup connection
        Socket server = serverSocket.accept();
        
        // get connection
        System.out.println("Server: connected to "
             + server.getRemoteSocketAddress());
        
        // get input message
        DataInputStream in =
             new DataInputStream(server.getInputStream());
        TowerDefense_TransData towerDefense_TransData = JSONUtility.JOSNToArray(in.readUTF());
        if (towerDefense_TransData.isNewRoundTransmit()) {
          if (!waitForNewRound[towerDefense_TransData.getClientId()]) {
            waitForNewRound[towerDefense_TransData.getClientId()] = true;
            newRoundReady[towerDefense_TransData.getClientId()] = true;
          }
          if (towerDefense_TransData.getSize() != 0 
              || nextRoundReadyBufferList[towerDefense_TransData.getClientId()] == null) {
            nextRoundReadyBufferList[towerDefense_TransData.getClientId()] = towerDefense_TransData;
          }
        } else {
          waitForNewRound[towerDefense_TransData.getClientId()] = false;
          clientMessageBufferList[towerDefense_TransData.getClientId()] = towerDefense_TransData;
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
        if (nextRoundReadyBufferList[sendId] != null 
            && nextRoundReadyBufferList[sendId].getTransmitType() == TowerDefensedataArray.Transmit_Type_New_Round_Ready) {
          out.writeUTF(JSONUtility.arrayToJSON(nextRoundReadyBufferList[sendId]));
          nextRoundReadyBufferList[sendId] = null;
        }
        else
          out.writeUTF(JSONUtility.arrayToJSON(clientMessageBufferList[sendId]));
        
        // set new round ready signal
        if (isNewRoundReady()) {
          for (int i = 0; i < Num_Of_Clients; i++) {
            nextRoundReadyBufferList[i].setTransmitType(TowerDefensedataArray.Transmit_Type_New_Round_Ready);
            nextRoundReadyBufferList[i].setTimeStamp(System.currentTimeMillis() + 1000);
          }
          resetNewRoundReady();
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
