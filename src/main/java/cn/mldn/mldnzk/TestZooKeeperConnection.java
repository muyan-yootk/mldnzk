package cn.mldn.mldnzk;

import java.util.List;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class TestZooKeeperConnection {
	private static final String AUTH_INFO = "zkuser:mldnjava" ; // 认证信息
	// 需要编写上在整个集群之中所有ZooKeeper的访问地址
	private static final String ZK_HOSTS = "192.168.188.144:2181,192.168.188.145,192.168.188.146" ;
	private static final int TIMEOUT = 100 ; // 超时时间
	public static void main(String[] args) throws Exception {
		ZooKeeper zkClient = new ZooKeeper(ZK_HOSTS, TIMEOUT, new Watcher() {
			public void process(WatchedEvent event) {
				System.out.println(event);
			}}) ;
		zkClient.addAuthInfo("digest", AUTH_INFO.getBytes());
		List<String> list = zkClient.getChildren("/", false) ;
		System.out.println(list);
		zkClient.close(); // 连接关闭
	}
}
