package umich.eecs285.towerdefence;

import java.net.*;
import java.io.*;

import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefenseObject;
import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefense_TransData;

public class ServerMessager extends Thread {
  public final static int Num_Of_Clients = 2;
  private TowerDefense_TransData[] clientMessageBufferList;
  private ServerSocket serverSocket;
  
  public ServerMessager(int port) throws IOException {
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
      InetAddress hostIp;
      hostIp = InetAddress.getLocalHost();
      System.out.println("Waiting for client on " + hostIp.getHostAddress() + ":" +
      serverSocket.getLocalPort() + "...");
      Socket server = serverSocket.accept();
      System.out.println("Just connected to "
           + server.getRemoteSocketAddress());
      DataInputStream in =
           new DataInputStream(server.getInputStream());
      System.out.println(in.readUTF());
     
      DataOutputStream out =
          new DataOutputStream(server.getOutputStream());
      out.writeUTF("Thank you for connecting to "
                  + server.getLocalSocketAddress() + "\nGoodbye!");
       
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
