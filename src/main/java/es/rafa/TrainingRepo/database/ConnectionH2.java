package es.rafa.TrainingRepo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class ConnectionH2 implements ConnectionManager{

	private static final String PARAMETERS = ";INIT=RUNSCRIPT FROM 'classpath:initDatabase.sql'";
	private static final String PASSWORD = "";
	private static final String USER = "sa";
	private static final String ORG_H2_DRIVER = "org.h2.Driver";
	final static Logger logger = Logger.getLogger(ConnectionH2.class);

	public Connection open(String databaseUrl) {
		Connection conn = null;
		try {
			Class.forName(ORG_H2_DRIVER);
		} catch (ClassNotFoundException e) {
			logger.error("Error in H2 Driver", e);
		}
		try {
			conn = DriverManager.getConnection(databaseUrl + PARAMETERS, USER, PASSWORD);
		} catch (SQLException e) {
			logger.error("Error connecting database", e);
		}

		return conn;
	}

	public void close(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			logger.error("Error closing conn", e);
		}
	}

	public void close(PreparedStatement preparedStatement) {
		try {
			preparedStatement.close();
		} catch (SQLException e) {
			logger.error("Error closing preparedStatement", e);
		}
	}

	public void close(ResultSet resultSet) {
		try {
			resultSet.close();
		} catch (SQLException e) {
			logger.error("Error closing resultSet", e);
		}
	}

}
