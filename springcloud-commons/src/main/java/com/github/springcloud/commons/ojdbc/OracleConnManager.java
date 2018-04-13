package com.github.springcloud.commons.ojdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OracleConnManager {
	private static Logger logger = LoggerFactory.getLogger(OracleConnectionUtil.class);
	public static ThreadLocal<Connection> localConnection = new ThreadLocal<Connection>();
	public static Connection getConnection(){
		Connection conn = localConnection.get();
		try {
			if(conn==null||conn.isClosed()){
				conn = OracleConnectionUtil.getConnection();
				localConnection.set(conn);
			}
		} catch (SQLException e) {
			logger.info("",e);
			e.printStackTrace();
		}
		return conn;
	}
	public static void beginTrans(){
		Connection conn = localConnection.get();
		try {
			if(conn!=null&&!conn.isClosed()&&conn.getAutoCommit()){
				conn.setAutoCommit(false);
			}
		} catch (SQLException e) {
			logger.info("",e);
			e.printStackTrace();
		}
	}
	
	public static void commitTrans(){
		Connection conn = localConnection.get();
		try {
			if(conn!=null&&!conn.isClosed()&&!conn.getAutoCommit()){
				conn.commit();
			}
		} catch (SQLException e) {
			logger.info("",e);
			e.printStackTrace();
		}
	}
	public static void rollBack(){
		Connection conn = localConnection.get();
		try {
			if(conn!=null&&!conn.isClosed()){
				conn.rollback();
			}
		} catch (SQLException e) {
			logger.info("",e);
			e.printStackTrace();
		}
	}
	
}
