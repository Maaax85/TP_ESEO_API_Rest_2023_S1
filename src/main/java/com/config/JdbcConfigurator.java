package com.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcConfigurator {

	private static final String ERROR_SQL_STRING = "An SQL exception occurred";
	private static final Logger LOGGER = LoggerFactory.getLogger(JdbcConfigurator.class);
	
	private Connection con;
	private String url = "jdbc:mysql://localhost:3306/maven?serverTimezone=UTC&useSSL=false";
	private String name = "root";
	private String password = "";

	public JdbcConfigurator() {
		try {
			this.con = DriverManager.getConnection(this.url, this.name, this.password);
		} catch (SQLException e) {
			LOGGER.error(ERROR_SQL_STRING, e);
		}
	}

	public void closeDatabase() {
		try {
			this.con.close();
		} catch (SQLException e) {
			LOGGER.error(ERROR_SQL_STRING, e);
		}
	}

	public Connection getConnection() {
		return this.con;
	}

}
