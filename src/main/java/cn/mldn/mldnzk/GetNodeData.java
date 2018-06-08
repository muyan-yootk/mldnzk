package cn.mldn.mldnzk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import cn.mldn.util.zookeeper.ZooKeeperConnection;

public class GetNodeData {
	private static final String GROUPNODE = "/mldndata" ; // 创建公共的父节点
	public static void main(String[] args) throws Exception {
		ZooKeeper zkClient = ZooKeeperConnection.getConnection() ;
		if (zkClient.exists(GROUPNODE, false) == null) { // 节点不存在
			zkClient.create(GROUPNODE, "MLDNJava".getBytes(),  
					ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} 
		Stat stat = new Stat() ; // 保存所有节点的相关信息
		byte[] data = zkClient.getData(GROUPNODE, false, stat) ;
		System.out.println("*** 节点数据：" + new String(data));
		System.out.println("*** 节点元信息、创建时间：" + new java.util.Date(stat.getCtime()));
		System.out.println("*** 节点元信息、创建版本：" + stat.getVersion());
		zkClient.close(); // 连接关闭
	}
}
