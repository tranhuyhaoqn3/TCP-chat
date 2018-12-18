 package GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import BUS.ClientBUS;
import DTO.Client;
import MyExtension.ThreadClient;
import javafx.scene.Group;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class login extends JFrame {

	/**
	 * Create the panel.
	 */
	public login() {	
		init();
		client=new Client();
		Loadform();
	}
	private JTextField txtUsername;
	private ClientBUS clientBUS;
	private Client client;
	private void init()
	{	
		
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		setBackground(Color.WHITE);
		getContentPane().setLayout(null);
	
		JLabel lbLogin = new JLabel("Chat");
		lbLogin.setFont(new Font("Tahoma", Font.BOLD, 25));
		lbLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lbLogin.setBounds(158, 23, 74, 50);
		getContentPane().add(lbLogin);
		
		JButton btnLogin = new JButton("Start");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLogin.setBackground(new Color(88,177,88));
		btnLogin.setBounds(104, 166, 201, 33);
		getContentPane().add(btnLogin);
		
		 txtUsername = new JTextField();
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtUsername.setForeground(Color.BLACK);
		txtUsername.setBounds(104, 117, 201, 22);
		getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("Name");
		lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsername.setBounds(104, 84, 79, 22);
		getContentPane().add(lblUsername);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setResizable(false);
	}
	void Loadform()
	{
		client.setIpaddress("127.0.0.1");
		client.setPort(9014);
		client.setConnect(false);
		client.setType("CHECK");
		client.setMess("login");
	}
	public void login()
	{
		if(txtUsername.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Input name",
	                "About", JOptionPane.WARNING_MESSAGE);
		}
		else {
			client.setName(txtUsername.getText());
			clientBUS=new ClientBUS();
			clientBUS.connect(client);
			clientBUS.setlogin(this);
			clientBUS.Send(client);
			clientBUS.receive();
		}
	}
	public void check(String text)
	{
		if(!text.equals("Unsuccess"))
		{
		Chat chat=new Chat();
		chat.setBUS(clientBUS);
		client.setMess(text);
		chat.setDTO(client);
		this.dispose();
		chat.setVisible(true);
		}
		else JOptionPane.showMessageDialog(null, "Name is using",
                "About", JOptionPane.WARNING_MESSAGE);
	}
}
