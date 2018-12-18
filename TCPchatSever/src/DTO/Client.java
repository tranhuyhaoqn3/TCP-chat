package DTO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client implements Serializable{

	  public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

	public boolean isConnect() {
		return isConnect;
	}
	public void setConnect(boolean isConnect) {
		this.isConnect = isConnect;
	}
	private String name;
	  private String ipaddress;
	  private int port;
	 private boolean isConnect;
	 public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMess() {
		return mess;
	}
	public void setMess(String mess) {
		this.mess = mess;
	}
	private String type;
	 private String mess;
	 private String localsocketaddress;
		public String getLocalsocketaddress() {
			return localsocketaddress;
		}
		public void setLocalsocketaddress(String localsocketaddress) {
			this.localsocketaddress = localsocketaddress;
		}
}