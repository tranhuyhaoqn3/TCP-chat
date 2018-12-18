package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BUS.ServerBUS;
import DTO.Client;
import DTO.Server;
import Myextension.IOFile;

import javax.swing.JTextArea;

public class main extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main frame = new main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public main() throws IOException {
		init();
		Load();
	
	}
	SimpleDateFormat sdf;
	ServerBUS serverBUS;
	Server server;
	ArrayList<Socket> listsocket=new ArrayList<Socket>();
	private JTextArea textArea;
	void init()
	{
		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 395, 416);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	 textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setRows(1);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setColumns(10);
		textArea.setBounds(10, 11, 359, 355);
		contentPane.add(textArea);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setResizable(false);
		
		
	}
	ArrayList<Client> listclient=new ArrayList<Client>();
	void Load() throws IOException
	{
		server=new Server();
		sdf = new SimpleDateFormat("hh:mm:ss a");
		server.setPort(9014);
		var temp=IOFile.ReadFile("Client/list.txt");
		if(temp!=null) {
			for(String s:temp)
			{
				Client cl=new Client();
				cl.setName(s);
				listclient.add(cl);
			}
		}
		server.setListclient(listclient);
		serverBUS=new ServerBUS(this);
		serverBUS.connect(server);
		appendMessage("Starting server in port "+server.getPort());
		serverBUS.Listen();
	}
	 void appendMessage(String msg)
	 {
		  Date date = new Date();
		  textArea.append(sdf.format(date) +": "+ msg +"\n");
		  textArea.setCaretPosition(textArea.getText().length() - 1);
	 }
	 String Messislist()
	 {
		 String temp="";
		 for(Client s:server.getListclient())
		 {
			 if(s.isConnect()==true)
			 {
				 temp+=s.getName()+"&";
			 }
		 }
		 return temp;
	 }
	public void check(Client client,Socket socket) throws IOException {
		int rs=serverBUS.check(client, listclient);
		if(rs==0) {
			client.setMess(Messislist());
				   client.setConnect(true);
					listclient.add(client);
					listsocket.add(socket);
					server.setListclient(listclient);
					appendMessage(client.getName() +" join server");
					IOFile.WriteFile("Client/list.txt",client.getName());
					   serverBUS.Send(socket, client);
						for(Socket s:listsocket)
						{
								client.setType("OFF");
								client.setMess(Messislist());
								serverBUS.Send(s,client);
						}
			  }
	
			  else { 
				 for(Client c:listclient)
				 {
					 if(c.getName().equals(client.getName()))
					 {
						 if(c.isConnect()==false)
						 {
							 c.setConnect(true);
							 c.setIpaddress(client.getIpaddress());
							 c.setLocalsocketaddress(client.getLocalsocketaddress());
							 listsocket.add(socket);
							 c.setType(client.getType());
							 c.setPort(client.getPort());
							 c.setMess(Messislist());
								appendMessage(c.getName() +" join server");
								serverBUS.Send(socket,c);
								for(Socket s:listsocket)
								{
										client.setType("OFF");
										client.setMess(Messislist());
										serverBUS.Send(s,client);
								}
								c.setMess(Sendhistorymess(c.getName()));
								c.setType("CHAT");
								serverBUS.Send(socket,c);
								IOFile.DeleteFile("Client/"+c.getName()+".txt");
								return;
						 }
					 }
				 }
					client.setMess("Unsuccess");
					serverBUS.Send(socket,client);
			  
			  }
	}
	String Sendhistorymess(String name) throws IOException
	{
		String s="(History) ";
		for(String temp:IOFile.ReadFile("Client/"+name+".txt"))
		{
			s+=temp+"	";
		}
		return s;
	}

	public void offline(Client client) {
		for(Client item:listclient)
		{
			if(item.getName().equals(client.getName()))
				{
				item.setConnect(false);
				appendMessage(item.getName() +" offline");
				break;
				}
		}
		for(int i=0;i<listsocket.size();i++)
		{
			int port=Integer.parseInt(client.getLocalsocketaddress().split(":")[1]);
			if(listsocket.get(i).getPort()==port)
			{
				listsocket.remove(listsocket.get(i));
				break;
			}

		}
		for(Socket s:listsocket)
		{
				client.setType("OFF");
				client.setMess(Messislist());
				serverBUS.Send(s,client);
		}
		}
	
	public void chat(Client client)
	{
		int port=0;
		for(Client c:server.getListclient())
		{
			if(c.getName().equals(client.getName()))
				port=Integer.parseInt(client.getLocalsocketaddress().split(":")[1]);
		}
		for(Socket s: listsocket)
		{
			
			if(s.getPort()!=port)
			serverBUS.Send(s, client);
		}
	}

	public void chatprivate(Client client) throws IOException {
		var s=client.getType().split("@");
		int port=0;
		for(Client c:server.getListclient())
		{
			if(c.getName().equals(s[1]))
				{
				var test=c.getLocalsocketaddress();
				if(test!=null)
				{port=Integer.parseInt(c.getLocalsocketaddress().split(":")[1]);
				for(Socket item: listsocket)
				{
					
					if(item.getPort()==port)
					{serverBUS.Send(item, client);
					return;
					}
				}
				}
				 Date date = new Date();
				 String ms=sdf.format(date) +": "+client.getName()+" :" +client.getMess();
				 IOFile.WriteFile("Client/"+s[1]+".txt", ms);
				 return;
				}
		}
	}
	}