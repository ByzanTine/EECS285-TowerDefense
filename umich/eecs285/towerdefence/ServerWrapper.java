package umich.eecs285.towerdefence;

import java.net.*;
import java.io.*;

public class ServerWrapper extends Thread {
  private ServerSocket serverSocket;
  
  public ServerWrapper(int port) throws IOException
  {
     serverSocket = new ServerSocket(port, 2000000);
     serverSocket.setSoTimeout(10000000);
  }

  public void run()
  {
     while(true)
     {
        try
        {
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
        } catch(SocketTimeoutException s)
        {
           System.out.println("Socket timed out!");
           break;
        } catch(IOException e)
        {
           e.printStackTrace();
           break;
        }
     }
  }
  public static void main(String [] args)
  {
     int port = Integer.parseInt(args[0]);
     try
     {
        Thread t = new ServerWrapper(port);
        t.start();
     }catch(IOException e)
     {
        e.printStackTrace();
     }
  }
}
