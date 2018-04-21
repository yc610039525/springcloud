package com.github.i24x.springcloud.commons.zk.base;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

public class ZkClientAPIBuilder {
	public static ZkClientConfig zkClientConfig;
	 
	public static ZkClient buildZkClientAPI(ZkClientConfig config){
		zkClientConfig = config;
		ZkConnection zkConn = new ZkConnection(zkClientConfig.getConnectionAddr());
		ZkClient zk = new ZkClient(zkConn, zkClientConfig.getSessionOutTime());
		return zk;
	}
}
