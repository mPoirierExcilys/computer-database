package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectionMysql implements Connector {
	
	private static Connection connect;
	
	private static Logger logger = LoggerFactory.getLogger(ConnectionMysql.class);
	
	private DataSource hikariDataSource;
	
	@Autowired
	public ConnectionMysql(DataSource hikariDataSource) {
		this.hikariDataSource = hikariDataSource;
	}
	
	
	public synchronized Connection getInstance() throws SQLException {
		if(connect == null || connect.isClosed()) {
			try {
				connect = hikariDataSource.getConnection();
			} catch(SQLException eSQL) {
				logger.error("Error connection to DB",eSQL);		
			}
		}
		return connect;
	}
}
