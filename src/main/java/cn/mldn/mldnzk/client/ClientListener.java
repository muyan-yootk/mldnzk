package cn.mldn.mldnzk.client;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import cn.mldn.common.ZooKeeperInfo;

public class ClientListener {
	private static int count = 1 ;
	private ZooKeeper zkClient ;
	public ClientListener() throws Exception {
		this.connection(); 
		System.out.println("【初始化连接信息】" + this.handleServerListUpdate());
		TimeUnit.DAYS.sleep(Long.MAX_VALUE);
		this.zkClient.close(); 
	}
	
	public Map<String,String> handleServerListUpdate() throws Exception {	// 服务器端的列表更新方法
		Map<String,String> serverMap = new LinkedHashMap<String, String>() ;
		List<String> all = this.zkClient.getChildren(ZooKeeperInfo.GROUPNODE, true) ; // 进行子节点的监听
		Iterator<String> iter = all.iterator() ;
		while (iter.hasNext()) {
			String nodeName = iter.next() ;
			String subnodePath = ZooKeeperInfo.GROUPNODE + "/" + nodeName ;
			serverMap.put(nodeName, new String(this.zkClient.getData(subnodePath, false, new Stat()))) ;
		}
		return serverMap ;
	}
	
	private void connection() throws Exception {
		this.zkClient = new ZooKeeper(ZooKeeperInfo.ZK_HOSTS, ZooKeeperInfo.TIMEOUT, new Watcher() {
			public void process(WatchedEvent event) {
				if (event.getType().equals(EventType.NodeChildrenChanged)) {	// 子节点发生改变
					try {
						System.err.println("【服务器状态发生改变】第" + count ++ + "次获取服务器端的服务列表数据信息：" + handleServerListUpdate());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}}) ; 
		this.zkClient.addAuthInfo("digest", ZooKeeperInfo.AUTH_INFO.getBytes()); 
	}
}
