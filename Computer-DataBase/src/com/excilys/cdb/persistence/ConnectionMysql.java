package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMysql {
	
	private static String url = "jdbc:mysql://localhost:3306/computer-database-db";
	
	private static String user ="admincdb";
	
	private static String passwd = "qwerty1234";
	
	private static Connection connect;
	
	
	public static Connection getInstance() {
		if(connect == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
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

}
