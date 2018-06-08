package cn.mldn.common;

public interface ZooKeeperInfo { 
	public static final String GROUPNODE = "/mldn-server" ; // 创建公共的父节点
	public static final String AUTH_INFO = "zkuser:mldnjava" ; // 认证信息
	public static final String ZK_HOSTS = "192.168.188.144:2181,192.168.188.145,192.168.188.146" ;
	public static final int TIMEOUT = 100 ; // 超时时间
	public static final String SUBNODE = GROUPNODE + "/server-" ;
}
