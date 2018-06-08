package cn.mldn.mldnzk.server.main;

import java.net.InetAddress;

import cn.mldn.mldnzk.server.ServerListener;

public class ServerMain {
	public static void main(String[] args) throws Exception {
		String ip = InetAddress.getLocalHost().getHostAddress() ;
		new ServerListener(ip) ;
	}
}
