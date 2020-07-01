package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionMysql implements Connector {
	
	private static Connection connect;
	
	private static Logger logger = LoggerFactory.getLogger(ConnectionMysql.class);
	
	private static HikariDataSource ds = new HikariDataSource(new HikariConfig("/datasource.properties"));
	
	
	public synchronized Connection getInstance() throws SQLException {
		if(connect == null || connect.isClosed()) {
			try {
				connect = ds.getConnection();
			} catch(SQLException eSQL) {
				logger.error("Error connection to DB");
				eSQL.printStackTrace();
				
			}
		}
		return connect;
	}
}
