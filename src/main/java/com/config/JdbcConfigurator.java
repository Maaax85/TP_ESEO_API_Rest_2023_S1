package com.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConfigurator {

	private Connection con;
	private String url = "jdbc:mysql://localhost:3306/maven?serverTimezone=UTC&useSSL=false";
	private String name = "root";
	private String password = "";

	public JdbcConfigurator() {
		try {
			this.con = DriverManager.getConnection(this.url, this.name, this.password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeDatabase() {
		try {
			this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return this.con;
	}

}
