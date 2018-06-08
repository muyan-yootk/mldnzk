package cn.mldn.mldnzk.server;

import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import cn.mldn.common.ZooKeeperInfo;

public class ServerListener {
	private ZooKeeper zkClient ; 
	public ServerListener(String ip) throws Exception {	// 每一个节点下面记录的是ip数据
		this.connection();  // 进行ZooKeeper连接
		this.handle(ip);
		TimeUnit.DAYS.sleep(Long.MAX_VALUE);
		this.zkClient.close();
	}
	public void handle(String ip) throws Exception {
		// 如果要创建瞬时节点则必须保证父节点存在，父节点是一个持久化节点，如果不存在则创建
		if (this.zkClient.exists(ZooKeeperInfo.GROUPNODE, false) == null) {
			this.zkClient.create(ZooKeeperInfo.GROUPNODE, "server-list".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT);
		}	// 所有的持久化节点之中再存放有一堆的临时节点信息
		this.zkClient.create(ZooKeeperInfo.SUBNODE, ip.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL_SEQUENTIAL);	// 序列子节点
	}
	private void connection() throws Exception {
		this.zkClient = new ZooKeeper(ZooKeeperInfo.ZK_HOSTS, ZooKeeperInfo.TIMEOUT, new Watcher() {
			public void process(WatchedEvent event) {
			}}) ;
		this.zkClient.addAuthInfo("digest", ZooKeeperInfo.AUTH_INFO.getBytes());
	}
}
