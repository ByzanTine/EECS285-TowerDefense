package umich.eecs285.towerdefence;

import java.net.*;
import java.io.*;

public class ClientMessager extends Thread {
  private String serverIp;
  private int port;
  private String sendMessage;
  private String receiveMessage;
  
  public ClientMessager(String inServerIp, String inPort, int inId, String inSendMessage) throws IOException {
    serverIp = inServerIp;
    port = Integer.parseInt(inPort);
    sendMessage = inSendMessage;
  }
  
  public void run() {
    try {
      System.out.println("Connecting to " + serverIp
          + " on port " + port);
      Socket client = new Socket(serverIp, port);
      System.out.println("Just connected to "
          + client.getRemoteSocketAddress());
      OutputStream outToServer = client.getOutputStream();
      DataOutputStream out =
          new DataOutputStream(outToServer);
      out.writeUTF(sendMessage);
      InputStream inFromServer = client.getInputStream();
      DataInputStream in =
          new DataInputStream(inFromServer);
      receiveMessage = in.readUTF();
      client.close();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
  
  public String getReceiveMessage() {
    return receiveMessage;
  }
  
}
