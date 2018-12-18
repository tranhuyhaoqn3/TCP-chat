package BUS;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DTO.Client;
import DTO.Server;
import GUI.main;
import Myextension.ServerThread;


public class ServerBUS  {
	   ServerThread listen;
	   ServerSocket serversocket;
	   main Main;
	   
	   public ServerBUS(main Main)
	   {
		   this.Main=Main;
	   }
	   public void connect(Server server)
	   {
		   try {
			   serversocket=new ServerSocket(server.getPort());		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }
	  public void Listen()
	  {
		  listen=new ServerThread(serversocket,Main);
		  Thread thread=new Thread(listen);
		  thread.start();
	  }
	public void Close()
	{
		try {
			serversocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Send(Socket socket,Client client) 
	{
		try {
		    OutputStream dos=socket.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(dos);
				oos.writeObject(client);
				oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int check(Client client,ArrayList<Client> list) {
		for(Client item: list)
		{
			if (item.getName().equals(client.getName())) 
			{
				
				if(client.getMess().equals("login")) return 1;
				return 2;
			}

		}
			return 0;
	}
}