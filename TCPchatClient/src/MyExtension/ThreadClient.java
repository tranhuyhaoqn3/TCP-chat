package MyExtension;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import DTO.Client;
import GUI.login;
import GUI.Chat;

public class ThreadClient implements Runnable {

	private Socket socketclient;
	login l;
	Chat chat;
	public ThreadClient(Socket socketclient)
	{
		this.socketclient=socketclient;
		
	}
	public void setlogin(login l)
	{
		this.l=l;
	}
	public void setprivate(Chat privatechat)
	{
		this.chat=privatechat;
		listprivate.add(this.chat);
	}
	private List<Chat> listprivate=new ArrayList<Chat>();
	@Override
	public void run() {
		try {
			
			 while (true)
			 {
				 Client client;
				 InputStream is = socketclient.getInputStream();
				 ObjectInputStream ois = new ObjectInputStream(is);
				client = (Client)ois.readObject();
				String type=client.getType();
				if(type.charAt(0)=='@') chat.recivemess(client);
				 switch(type){
				
				  case "CHECK":
						   l.check(client.getMess());
						  break;
				  case "OFF":
					  chat.offline(client.getMess());
					  break;
				  case "CHAT":
					  chat.recivemess(client);
				  }
			}}
			 catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	