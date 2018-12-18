package DTO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class Server {
	  private int port;
	  private  String Ipaddress;
	  public String getIpaddress() {
		return Ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		Ipaddress = ipaddress;
	}
	private ArrayList<Client> listclient;
	  public ArrayList<Client> getListclient() {
		return listclient;
	}
	public void setListclient(ArrayList<Client> listclient) {
		this.listclient = listclient;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
}