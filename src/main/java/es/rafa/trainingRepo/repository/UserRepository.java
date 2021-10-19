package es.rafa.trainingRepo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.rafa.trainingRepo.bean.UserBean;
import es.rafa.trainingRepo.database.ConnectionManager;
import es.rafa.trainingRepo.entityMapper.UserMapper;
import es.rafa.trainingRepo.exception.DatabaseException;

public class UserRepository {

	@Autowired
	ConnectionManager manager;

	@Autowired
	UserMapper usermapper;

	private final String SQL_INSERT = "INSERT INTO USERS (NAME, AVATAR) VALUES (?, ?)";
	private final String SQL_FIND_ALL = "SELECT * FROM USERS";

	private final int PAGINATION_ROWS = 5;
	private final String SQL_GET_NUMBER_OF_PAGES = "SELECT COUNT(1)/ ? AS NUMROWS FROM USERS";
	private final String SQL_GET_PAGINATION_USERS = "SELECT * FROM USERS LIMIT ? OFFSET ?*?";

	public void insertUser(UserBean user) {
		Connection conn = manager.openConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(SQL_INSERT);

			ps.setString(1, user.getLogin());
			ps.setString(2, user.getAvatar_url());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseException("Error inserting user: " + user.getLogin(), e);
		} finally {
			manager.close(ps);
			manager.close(conn);
		}

	}

	public List<UserBean> findAll() {
		List<UserBean> userList = new ArrayList<>();
		Connection conn = manager.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(SQL_FIND_ALL);
			rs = ps.executeQuery();

			while (rs.next()) {
				userList.add(usermapper.toBean(rs.getInt("id"), rs.getString("NAME"), rs.getString("AVATAR")));
			}

		} catch (SQLException e) {
			throw new DatabaseException("Error getting all users ", e);
		} finally {
			manager.close(rs);
			manager.close(ps);
			manager.close(conn);
		}

		return userList;
	}

	public List<UserBean> findPagination(int page) {
		List<UserBean> userList = new ArrayList<>();
		Connection conn = manager.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(SQL_GET_PAGINATION_USERS);
			ps.setInt(1, PAGINATION_ROWS);
			ps.setInt(2, PAGINATION_ROWS);
			ps.setInt(3, page);
			rs = ps.executeQuery();

			while (rs.next()) {
				userList.add(usermapper.toBean(rs.getInt("id"), rs.getString("NAME"), rs.getString("AVATAR")));
			}

		} catch (SQLException e) {
			throw new DatabaseException("Error getting findPagination", e);
		} finally {
			manager.close(rs);
			manager.close(ps);
			manager.close(conn);
		}

		return userList;
	}

	public int getNumberOfPages() {
		int numPages = 0;
		Connection conn = manager.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(SQL_GET_NUMBER_OF_PAGES);
			ps.setInt(1, PAGINATION_ROWS);
			rs = ps.executeQuery();

			if (rs.next()) {
				numPages = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			manager.close(rs);
			manager.close(ps);
			manager.close(conn);
		}
		return numPages;
	}

}
