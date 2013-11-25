package umich.eecs285.towerdefence;

import java.net.*;
import java.io.*;

public class ClientWrapper extends Thread {
  private String serverIp;
  private int port;
  private int id;
  private String message;
  
  public ClientWrapper(String inServerIp, String inPort, int inId, String inMessage) throws IOException {
    serverIp = inServerIp;
    port = Integer.parseInt(inPort);
    id = inId;
    message = inMessage;
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
      out.writeUTF(id + ": " + message);
      InputStream inFromServer = client.getInputStream();
      DataInputStream in =
          new DataInputStream(inFromServer);
      System.out.println("Server: " + in.readUTF());
      client.close();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
  
  public static void main(String [] args)
  {
    for (int i = 0; i < 100000; i++) {
      try
      {
         Thread t = new ClientWrapper(args[0], args[1], Integer.parseInt(args[2]), Integer.toString(i));
         t.start();
         Thread.sleep(10);
      } catch(IOException e) {
         e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } 
    }
  }
}
