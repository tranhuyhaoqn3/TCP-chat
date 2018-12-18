package Myextension;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import DTO.Server;
import GUI.main;

public class ServerThread implements Runnable{

	private ServerSocket server;
	main Main;
	public ServerThread(ServerSocket server,main Main)
	{
		this.server=server;
		this.Main=Main;
	}
	@Override
	public void run() {
			// TODO Auto-generated method stub
			while(true)
			{
				try {
					Socket socket = server.accept();

					SocketThread run1=new SocketThread(socket,Main);
					Thread receive=new Thread(run1);
					receive.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

}