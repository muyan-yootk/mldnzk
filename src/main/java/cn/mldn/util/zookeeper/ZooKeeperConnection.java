package cn.mldn.util.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZooKeeperConnection {
	private static final String GROUPNODE = "/mldndata" ; // 创建公共的父节点
	private static final String AUTH_INFO = "zkuser:mldnjava" ; // 认证信息
	// 需要编写上在整个集群之中所有ZooKeeper的访问地址
	private static final String ZK_HOSTS = "192.168.188.144:2181,192.168.188.145,192.168.188.146" ;
	private static final int TIMEOUT = 100 ; // 超时时间
	private static ZooKeeper zkClient = null ;
	public static ZooKeeper getConnection() throws Exception {
		zkClient = new ZooKeeper(ZK_HOSTS, TIMEOUT, new Watcher() {
			public void process(WatchedEvent event) {
				try {
					zkClient.getChildren(GROUPNODE, true) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("连接处：" + event.getPath());
			}}) ;
		zkClient.addAuthInfo("digest", AUTH_INFO.getBytes());
		return zkClient ;
	}

}
