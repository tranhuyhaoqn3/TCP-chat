package Myextension;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import DTO.Client;
import GUI.main;

public class SocketThread implements Runnable {
	private Socket socket;
	main Main;

	public SocketThread(Socket socket, main Main) {
		this.Main = Main;
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
		 while (true)
		 {	
			 InputStream is = socket.getInputStream();
			 ObjectInputStream ois = new ObjectInputStream(is);
			Client client = (Client)ois.readObject();
			String type=client.getType();
			if(type.charAt(0)=='@') Main.chatprivate(client);
			 switch(type){
			  case "CHECK":
				   Main.check(client,socket);
				  break;
			  case "OFF":
				  Main.offline(client);
				  return;
			  case "CHAT":
				  Main.chat(client);
				  break;
				  default:
					  break;
			  }
		 }
		}
		 catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}