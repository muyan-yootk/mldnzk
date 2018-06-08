package cn.mldn.mldnzk;

import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import cn.mldn.util.zookeeper.ZooKeeperConnection;

public class TestCreateNode {
	private static final String GROUPNODE = "/mldndata" ; // 创建公共的父节点
	public static void main(String[] args) throws Exception {
		ZooKeeper zkClient = ZooKeeperConnection.getConnection() ;
		if (zkClient.exists(GROUPNODE, false) == null) { // 节点不存在
			zkClient.create(GROUPNODE, "MLDNJava".getBytes(),  
					ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		} 
		TimeUnit.SECONDS.sleep(5);
		zkClient.close(); // 连接关闭
	}
}
