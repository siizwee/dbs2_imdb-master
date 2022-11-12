package de.hsh.dbs2.imdb.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static final boolean debug = true;
	private static Connection conn = null;

	private static String dbconnectString = null;
	private static String username = null;
	private static String password = null;

	public static void setDbconnectString(String dbconnectString) {
		DBConnection.dbconnectString = dbconnectString;
	}

	public static void setUsername(String username) {
		DBConnection.username = username;
	}

	public static void setPassword(String password) {
		DBConnection.password = password;
	}


	public static Connection getConnection() throws SQLException {
		if (dbconnectString == null || username == null || password == null)
			throw new SQLException("Failed to connect to database, connection details not set");

		try {
			if (conn == null || conn.isClosed()) {
				conn = null;
				conn = DriverManager.getConnection(
						dbconnectString, username, password);
				conn.setAutoCommit(false);
			}
		} catch (SQLException e) {
			throw new SQLException("Failed to connect to database: " + e.getMessage());
		}
		return conn;
	}

	public static void log_stderr(String msg, Exception e) {
		if (!debug)
			return;

		System.err.println(msg);
		e.printStackTrace();
	}

	public static void log_stdio(String msg) {
		if (!debug)
			return;

		System.out.println(msg);
	}
}
