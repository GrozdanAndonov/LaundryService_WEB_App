package com.example.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

@Component
public class DBManager {
	Connection conn;

	public DBManager() {
		final String DB_IP = "localhost";
		final String DB_PORT = "3306";
		final String DB_DBNAME = "laundry_service";
		final String DB_USER = "root";
		final String DB_PASS = "";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://"+DB_IP+":"+DB_PORT+"/"+DB_DBNAME+"?characterEncoding=UTF-8", DB_USER, DB_PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public Connection getConn() {
		return this.conn;
	}
	
	public void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
