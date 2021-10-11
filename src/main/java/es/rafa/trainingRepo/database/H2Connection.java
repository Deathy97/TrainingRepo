package es.rafa.trainingRepo.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import es.rafa.trainingRepo.exception.DatabaseException;

public class H2Connection implements ConnectionManager {

	@Autowired
	DataSource dataSource;

	public Connection openConnection() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			throw new DatabaseException("Error creating database connection", e);
		}

		return conn;
	}

	public void close(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new DatabaseException("Error closing connection", e);
		}
	}

	public void close(PreparedStatement preparedStatement) {
		try {
			preparedStatement.close();
		} catch (SQLException e) {
			throw new DatabaseException("Error closing preparedStatement", e);
		}
	}

	public void close(ResultSet resultSet) {
		try {
			resultSet.close();
		} catch (SQLException e) {
			throw new DatabaseException("Error closing resultSet", e);
		}
	}

}
