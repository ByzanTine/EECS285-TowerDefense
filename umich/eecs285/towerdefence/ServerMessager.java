package umich.eecs285.towerdefence;

import java.net.*;
import java.io.*;

import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefense_TransData;

public class ServerMessager extends Thread {
  public final static int Num_Of_Clients = 2;
  private TowerDefense_TransData[] clientMessageBufferList;
  private ServerSocket serverSocket;
  
  public ServerMessager(int port) throws IOException {
    // TODO figure out proper server settings
    serverSocket = new ServerSocket(port, 100000);
    serverSocket.setSoTimeout(1000);
    
    clientMessageBufferList = new TowerDefense_TransData[Num_Of_Clients];
    for (int i = 0; i < Num_Of_Clients; i++) {
      clientMessageBufferList[i] = TowerDefense_TransData.createEmptyTransData(i);
    }
  }

  public void run() {
    while(true) {
    try {
      // initialize server
      InetAddress hostIp;
      hostIp = InetAddress.getLocalHost();
      System.out.println("Waiting for client on " + hostIp.getHostAddress() + ":" +
      serverSocket.getLocalPort() + "...");
      
      // setup connection
      Socket server = serverSocket.accept();
      
      // get connection
      System.out.println("Just connected to "
           + server.getRemoteSocketAddress());
      
      // get input message
      DataInputStream in =
           new DataInputStream(server.getInputStream());
      TowerDefense_TransData towerDefense_TransData = JSONUtility.JOSNToArray(in.readUTF());
      clientMessageBufferList[towerDefense_TransData.getId()] = towerDefense_TransData;
      // TODO need to change for more than two players
     
      // send out output message
      int sendId;
      if (towerDefense_TransData.getId() == 1)
        sendId = 0;
      else
        sendId = 1;
      DataOutputStream out =
          new DataOutputStream(server.getOutputStream());
      out.writeUTF(JSONUtility.arrayToJSON(clientMessageBufferList[sendId]));
      clientMessageBufferList[sendId] = TowerDefense_TransData.createEmptyTransData(sendId);
       
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
