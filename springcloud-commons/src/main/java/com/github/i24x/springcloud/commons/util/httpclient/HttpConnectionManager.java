package com.github.i24x.springcloud.commons.util.httpclient;

import java.nio.charset.CodingErrorAction;

import javax.annotation.PostConstruct;

import org.apache.http.Consts;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

//@Component
public class HttpConnectionManager {
	PoolingHttpClientConnectionManager connManager = null;
	@PostConstruct
	public void init() {
		/*** 创建连接管理器，并设置相关参数 */
		// 连接管理器，使用无惨构造
		connManager = new PoolingHttpClientConnectionManager();
		/**连接数相关设置*/
		// 最大连接数
		connManager.setMaxTotal(200);
		// 默认的每个路由的最大连接数
		connManager.setDefaultMaxPerRoute(100);
		// 设置到某个路由的最大连接数，会覆盖defaultMaxPerRoute
//		connManager.setMaxPerRoute(new HttpRoute(new HttpHost("somehost", 80)), 150);
		/**socket配置（默认配置 和 某个host的配置*/
		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true) 
				// 是否立即发送数据，设置为true会关闭Socket缓冲，默认为false
				.setSoReuseAddress(true) 
				// 是否可以在一个进程关闭Socket后，即使它还没有释放端口，其它进程还可以立即重用端口
				.setSoTimeout(5000) 
				// 接收数据的等待超时时间，单位ms
				.setSoLinger(60) 
				// 关闭Socket时，要么发送完所有数据，要么等待60s后，就关闭连接，此时socket.close()是阻塞的
				.setSoKeepAlive(true) 
				// 开启监视TCP连接是否有效
				.build();
		connManager.setDefaultSocketConfig(socketConfig);
		/**
		 * HTTP connection相关配置（默认配置 和 某个host的配置） 一般不修改HTTP connection相关配置，故不设置
		 */
		// 消息约束 一般不修改HTTP connection相关配置，故不设置
//		MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200)
//				.setMaxLineLength(2000).build();
//		ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
//				.setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
//				.setMessageConstraints(messageConstraints).build();
		
		// connManager.setDefaultConnectionConfig(connectionConfig);
    	//connManager.setConnectionConfig(new HttpHost("somehost", 80),
		// ConnectionConfig.DEFAULT);;
	}
}