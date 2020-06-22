package com.excilys.cdb.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionMysql {
	
	private static String url;
	
	private static String user;
	
	private static String passwd;
	
	private static String driver;
	
	private static Connection connect;
	
	private static Logger logger = LoggerFactory.getLogger(ConnectionMysql.class);
	
	
	public static Connection getInstance() throws SQLException {
		if(connect == null || connect.isClosed()) {
			try {
				getProperties();
				Class.forName(driver);
				connect = DriverManager.getConnection(url, user, passwd);
			} catch(SQLException eSQL) {
				logger.error("Error connection to DB");
				eSQL.printStackTrace();
				
			} catch (ClassNotFoundException e) {
				logger.error("Driver not found");
				e.printStackTrace();
			}
		}
		return connect;
	}
	
	public static void closeConnection() {
		try {
			connect.close();
		} catch (SQLException e) {
			logger.error("Error to close connection");
			e.printStackTrace();
		}
	}
	
	private static void getProperties() {
		Properties prop = new Properties();
		try(InputStream input = new FileInputStream("src/main/resources/jdbc.properties")){
			prop.load(input);
			url = prop.getProperty("db.url");
			user = prop.getProperty("db.username");
			passwd = prop.getProperty("db.password");
			driver = prop.getProperty("db.driver");
		} catch (FileNotFoundException e) {
			logger.error("File not Found");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IO Exceptions");
			e.printStackTrace();
		}
	}

}
