package GUI;


import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import BUS.ClientBUS;
import DTO.Client;

import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.DropMode;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class Chat extends JFrame {

	public Chat() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				String ObjButtons[] = {"Yes","No"};
		        int PromptResult = JOptionPane.showOptionDialog(null,"Are you sure you want to exit?","Online Examination System",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
		        if(PromptResult==JOptionPane.YES_OPTION)
		        {
		        	clientoff();
		            System.exit(0);
		        }
			}
		});
		init();
		
	}
	private void Load()
	{
		String text=client.getMess();
			appendOnlineList(text);
	}
	private JTextArea txtChat;
	private JTextArea txtMess;
	private JScrollPane scroll = new JScrollPane();
private void init() {
	setTitle("Private chat");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 551, 412);
	JPanel	pnMain = new JPanel();
	pnMain.setBackground(new Color(255, 255, 255));
	pnMain.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(pnMain);
	pnMain.setLayout(null);
	Border border = BorderFactory.createLineBorder(Color.gray);
	 border = BorderFactory.createLineBorder(Color.gray);
	
	JButton btSend = new JButton("Send");
	btSend.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			SendMessenger();
		}
	});
	btSend.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
		}
	});
	btSend.setForeground(new Color(255, 255, 255));
	btSend.setBackground(new Color(0,120,215));
	btSend.setBounds(310, 276, 73, 79);
	pnMain.add(btSend);
	
	JScrollPane scpChat = new JScrollPane();
	scpChat.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	scpChat.setBounds(11, 276, 289, 79);
	pnMain.add(scpChat);
	
	
	
	 txtChat = new JTextArea();
	txtChat.setColumns(10);
	txtChat.setRows(1);
	txtChat.setLineWrap(true);
	txtChat.setWrapStyleWord(true);
	
	scpChat.setViewportView(txtChat);
	
	JScrollPane scpMess = new JScrollPane();
	scpMess.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	scpMess.setBounds(11, 27, 372, 229);
	pnMain.add(scpMess);
	
	 txtMess = new JTextArea();
	txtMess.setEditable(false);
	
	txtMess.setColumns(10);
	txtMess.setRows(1);
	txtMess.setLineWrap(true);
	txtMess.setWrapStyleWord(true);
	scpMess.setViewportView(txtMess);

	
	JLabel lblOnline = new JLabel("Online");
	lblOnline.setBackground(new Color(124, 252, 0));
	lblOnline.setForeground(new Color(124, 252, 0));
	lblOnline.setFont(new Font("Tahoma", Font.BOLD, 20));
	lblOnline.setBounds(404, 24, 87, 24);
	pnMain.add(lblOnline);
	
	
	JScrollPane scpOnline = new JScrollPane();
	scpOnline.setBounds(393, 59, 121, 296);
	pnMain.add(scpOnline);
	 
	 jlist = new JList();
	scpOnline.setViewportView(jlist);
	

	this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	this.setResizable(false);
	

}
private JList jlist;
public void appendOnlineList(String text){
	Vector list=new Vector();
	 var s=text.split("&");
	 for(String temp:s)
	 {
		 if(!client.getName().equals(temp))
		 list.add(temp);
	 }
	jlist.removeAll();
	jlist.setListData(list);
}
private ClientBUS clientbus;
private Client client;
public void setBUS(ClientBUS clientbus) {
	this.clientbus=clientbus;
	this.clientbus.setprivate(this);

}
public void setDTO(Client client) {
this.client=client;
Load();
this.setTitle("Login with name: "+client.getName());
}
SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
 void appendMessage(String msg)
{
	  Date date = new Date();
	  txtMess.append(sdf.format(date) +": "+ msg +"\n");
	  txtMess.setCaretPosition(txtMess.getText().length() - 1);
}
 
void SendMessenger()
{
	if(txtChat.getText().equals(""))
	{
		return;
	}
	else {
		client.setConnect(true);
		appendMessage(this.client.getName()+" : "+txtChat.getText());
	  String s=txtChat.getText().split(" ")[0];
	  if(s.charAt(0)=='@')
	  {
		  client.setType(s);
		  String mess="";
		  for(int i=1;i<txtChat.getText().split(" ").length;i++)
		  {
			  mess+=txtChat.getText().split(" ")[i]+" ";
		  }
	client.setMess(mess);
	  }else
		{
		  client.setType("CHAT");
		  client.setMess(txtChat.getText());
		}
		this.clientbus.Send(client);
		txtChat.setText("");
	}
}
void clientoff()
{
	client.setConnect(false);
	client.setType("OFF");
	clientbus.Send(client);
}
public void offline(String mess) {
	appendOnlineList(mess);
}
public void recivemess(Client client2) {
	// TODO Auto-generated method stub
	if(!client2.getType().equals("CHAT"))
	{
		appendMessage(client2.getName()+"(only you) : "+client2.getMess());
	}
	else	appendMessage(client2.getName()+" : "+client2.getMess());
}
}
