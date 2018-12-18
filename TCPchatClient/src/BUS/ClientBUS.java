package BUS;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import DTO.Client;
import GUI.login;
import GUI.Chat;
import MyExtension.ThreadClient;

public class ClientBUS {
	 Socket clientSocket;
	login l;
	Chat chat;
	private ThreadClient threadclientSocket;
	public void receive()
	{
		Thread thread=new Thread(threadclientSocket);
		thread.start();
	}
	public void connect(Client client)
	{
		try {
			clientSocket=new Socket(client.getIpaddress(),client.getPort());
			client.setLocalsocketaddress(clientSocket.getLocalSocketAddress().toString());
			threadclientSocket=new ThreadClient(clientSocket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Close()
	{
		try {
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Send(Client client) 
	{
		try {
		    OutputStream dos=clientSocket.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(dos);
				oos.writeObject(client);
				oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setlogin(login l)
	{
		this.l=l;
		threadclientSocket.setlogin(l);
	}
	public void setprivate(Chat chat)
	{
		this.chat=chat;
		threadclientSocket.setprivate(chat);
	}
}
