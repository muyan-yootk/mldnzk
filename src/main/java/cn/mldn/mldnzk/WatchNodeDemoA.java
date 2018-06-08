package cn.mldn.mldnzk;

import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import cn.mldn.util.zookeeper.ZooKeeperConnection;

public class WatchNodeDemoA {
	private static final String GROUPNODE = "/mldndata" ; // 创建公共的父节点
	private static ZooKeeper zkClient = null ;
	static {
		try {
			zkClient = ZooKeeperConnection.getConnection() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void bind() throws Exception {
		Stat stat = new Stat() ;
		byte data [] = zkClient.getData(GROUPNODE, new Watcher() {
			public void process(WatchedEvent event) {
				System.out.println("### 操作路径：" + event.getPath());
				System.out.println("### 操作路径：" + event.getType());
				System.out.println("### 操作类型：" + event.getClass());
				try {
					bind() ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		},stat) ;
		System.out.println("监听到的数据：" + new String(data));
	}
	public static void main(String[] args) throws Exception {
		if (zkClient.exists(GROUPNODE, false) == null) { // 父节点不存在
			zkClient.create(GROUPNODE, "MLDNJava".getBytes(),  
					ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		bind() ;
		TimeUnit.DAYS.sleep(Long.MAX_VALUE); 
		zkClient.close(); // 连接关闭
	}
}
