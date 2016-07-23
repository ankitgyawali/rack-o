import java.net.URL;
import java.io.*;
import java.net.*;

public class rackoServer {

    public static void main(String []args) throws Exception
    {

	System.out.println("Server has started running!");



	int clientNumber = 0;

	ServerSocket listener = new ServerSocket(15008);
	try {
	    while(true){
		new rackOThread(listener.accept(),clientNumber++).start();
		
	    }
	}
	finally {
	    listener.close();
	}

	
	
}
 
  
    public static class rackOThread extends Thread{
	public Socket socket;
	public int clientNumber;


	public rackOThread(Socket socket, int clientNumber)
        {
	    this.socket = socket;
	    this.clientNumber = clientNumber;
	    System.out.println("A new client, client#"+clientNumber+" has connected on socket!");

	}


	public void run()
	{
	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream());
       	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			
	    System.out.println("O Hallo!!!! Client num#"+clientNumber);
	     System.out.println();
		 out.println(clientNumber);
	}

    }

}