package com.excilys.cdb.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionMysql {
	
	private static String url;
	
	private static String user;
	
	private static String passwd;
	
	private static String driver;
	
	private static Connection connect;
	
	
	public static Connection getInstance() throws SQLException {
		if(connect == null || connect.isClosed()) {
			try {
				getProperties();
				Class.forName(driver);
				connect = DriverManager.getConnection(url, user, passwd);
			} catch(SQLException eSQL) {
				System.out.println("Error connection to DB");
				eSQL.printStackTrace();
				
			} catch (ClassNotFoundException e) {
				System.out.println("Driver not found");
				e.printStackTrace();
			}
		}
		return connect;
	}
	
	public static void closeConnection() {
		try {
			connect.close();
		} catch (SQLException e) {
			System.out.println("Error to close connection");
			e.printStackTrace();
		}
	}
	
	private static void getProperties() {
		Properties prop = new Properties();
		try(InputStream input = new FileInputStream("ressources/jdbc.properties")){
			prop.load(input);
			url = prop.getProperty("db.url");
			user = prop.getProperty("db.username");
			passwd = prop.getProperty("db.password");
			driver = prop.getProperty("db.driver");
		} catch (FileNotFoundException e) {
			System.out.println("File not Found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exceptions");
			e.printStackTrace();
		}
	}

}
