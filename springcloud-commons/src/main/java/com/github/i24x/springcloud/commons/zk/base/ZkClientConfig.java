package com.github.i24x.springcloud.commons.zk.base;

public class ZkClientConfig {
	/**zookeeper地址 */
	public String CONNECT_ADDR = "192.168.1.8:2181," +
			"192.168.1.9:2181," +
			"192.168.1.10:2181";
	/**session超时时间 */
	public int SESSION_OUTTIME = 5000;
	public  String getConnectionAddr() {
		return CONNECT_ADDR;
	}
	public void setConnectionAddr(String addr) {
		CONNECT_ADDR = addr;
	}
	public int getSessionOutTime() {
		return SESSION_OUTTIME;
	}
	public void setSessionOutTime(int sessionOutTime) {
		SESSION_OUTTIME = sessionOutTime;
	}
	public ZkClientConfig(String addr, int sessionOutTime) {
		CONNECT_ADDR = addr;
		SESSION_OUTTIME = sessionOutTime;
	}
	public ZkClientConfig() {
	}
	
	
}
