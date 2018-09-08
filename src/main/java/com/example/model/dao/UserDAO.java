package com.example.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.DBManager;
import com.example.model.pojo.User;
import com.example.util.DateFormatConverter;

@Component
public class UserDAO {

	@Autowired
	DBManager db;
	@Autowired
	OrderDAO od;
	@Autowired
	CommentDAO cd;

	public void insertUser(User user) throws SQLException {
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement(
				"INSERT INTO user(first_name,last_name,password,email,city,streetAddress,zipCode,rating, avatar_url, isAdmin) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, user.getFirstName());
		ps.setString(2, user.getLastName());
		ps.setString(3, user.getPassword());
		ps.setString(4, user.getEmail());
		ps.setString(5, user.getCity());
		ps.setString(6, user.getStreetAddress());
		ps.setInt(7, user.getZipCode());
		ps.setInt(8, user.getRating());
		ps.setString(9, user.getAvatarUrl());
		ps.setBoolean(10, user.getIsAdmin());
		ps.executeUpdate();
	}

	public int insertAvatarUrl(User user, String url) throws SQLException {
		if (url == null || url.isEmpty()) {
			throw new IllegalArgumentException();
		}
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("UPDATE user SET avatar_url = ? WHERE id_user = ?",
				Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, url);
		ps.setInt(2, user.getId());
		return ps.executeUpdate();
	}

	public void deleteUser(User user) throws SQLException {
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("DELETE FROM user where id_user = ?;",
				Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, user.getId());
		ps.executeQuery();
	}

	public User getUserById(int id) throws SQLException {
		Connection c = db.getConn();
		PreparedStatement ps = c.prepareStatement(
				"SELECT  id_user, first_name, last_name, password, email, city,"
				+ " streetAddress, rating, avatar_url,isAdmin, date_created, zipCode, telNum,"
				+ "bulstat, last_login_date, default_language FROM user WHERE id_user = ?;",
				Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		String avatarUrl = rs.getString("avatar_url");
		User user = new User(rs.getInt("id_user"), rs.getString("first_name"), rs.getString("last_name"),
				rs.getString("password"), rs.getString("email"), rs.getString("city"), rs.getInt("rating"), avatarUrl,
				rs.getString("telNum"));
		return new User(rs.getInt("id_user"), rs.getString("first_name"), rs.getString("last_name"),
				rs.getString("email"), rs.getString("city"), rs.getInt("zipCode"), rs.getBoolean("isAdmin"),
				rs.getString("telNum"), rs.getString("streetAddress"), avatarUrl, rs.getDate("date_created"),
				rs.getTimestamp("last_login_date"), rs.getString("bulstat"), rs.getString("default_language"),
				rs.getInt("rating"), od.getOrdersForUser(user), cd.getAllCommentsForUser(user));
	}

	/**
	 * if email is not being used
	 * 
	 * @return true
	 * @param String
	 */
	public boolean checksIfEmailIsNotFree(String email) throws SQLException {
		if (this.getResultForEmail(email) == 0) {
			return false;
		}
		return true;
	}

	public boolean checksIfEmailExists(String email) throws SQLException {
		if (this.getResultForEmail(email) == 1) {
			return true;
		}
		return false;
	}

	public boolean checksIfPasswordAndEmailMatchesForUser(String password, String email) throws SQLException {
		Connection c = db.getConn();
		PreparedStatement ps = c.prepareStatement("SELECT email, password FROM user WHERE email = ?;",
				Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			if (password.equals(rs.getString("password"))) {
				return true;
			}
		}
		return false;
	}

	public User getFullUserByEmail(String email) throws SQLException, ParseException {
		Connection c = db.getConn();
		PreparedStatement ps = c.prepareStatement(
				"SELECT id_user, first_name, last_name, password, email, city, streetAddress,zipCode, rating, avatar_url,isAdmin, date_created, telNum, default_language, bulstat, last_login_date  FROM user WHERE email = ?;",
				Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		rs.next();
		String avatarUrl = rs.getString("avatar_url");
		User user = new User(rs.getInt("id_user"), rs.getString("first_name"), rs.getString("last_name"),
				rs.getString("password"), rs.getString("email"), rs.getString("city"), rs.getInt("rating"), avatarUrl,
				rs.getString("telNum"));

		return new User(rs.getInt("id_user"), rs.getString("first_name"), rs.getString("last_name"),
				rs.getString("email"), rs.getString("city"), rs.getInt("zipCode"), rs.getBoolean("isAdmin"),
				rs.getString("telNum"), rs.getString("streetAddress"), avatarUrl, rs.getTimestamp("date_created"),
				rs.getTimestamp("last_login_date"), rs.getString("bulstat"), rs.getString("default_language"),
				rs.getInt("rating"), od.getOrdersForUser(user), cd.getAllCommentsForUser(user));
	}

	private int getResultForEmail(String email) throws SQLException {
		Connection c = db.getConn();
		PreparedStatement ps = c.prepareStatement("SELECT COUNT(email) AS count FROM user where email = ?;",
				Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt("count");
	}

	public List<User> findAllUsersForAdminList() throws SQLException {
		Connection c = db.getConn();
		PreparedStatement ps = c.prepareStatement(
				"SELECT id_user, first_name, last_name, password, email, city, streetAddress,zipCode, rating, avatar_url,"
				+ "isAdmin,telNum date_created, last_login_date, bulstat, default_language FROM user;",
				Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = ps.executeQuery();
		LinkedList<User> users = new LinkedList<>();
		while (rs.next()) {
			users.add(new User(rs.getInt("id_user"), rs.getString("first_name"), rs.getString("last_name"),
					rs.getString("email"), rs.getString("city"), rs.getInt("zipCode"), rs.getBoolean("isAdmin"),
					rs.getString("telNum"), rs.getString("streetAddress"), rs.getString("avatar_url"),
					rs.getDate("date_created"),rs.getTimestamp("last_login_date"), rs.getString("bulstat"), rs.getString("default_language"),
					rs.getInt("rating"), new HashSet<>(), new HashSet<>()));
		}
		return users;
	}

	public boolean updateUser(int userId, String firstName, String lastName, String email, String streetAddress,
			String city, int zip, String telNumber, String bulstat) throws SQLException {
		Connection c = db.getConn();
		PreparedStatement ps = c.prepareStatement(
				"UPDATE user SET first_name = ?,"
						+ "last_name= ?, email = ?, streetAddress = ?, city = ?, zipCode= ?, telNum = ?, bulstat = ? WHERE id_user = ?",
				Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, firstName);
		ps.setString(2, lastName);
		ps.setString(3, email);
		ps.setString(4, streetAddress);
		ps.setString(5, city);
		ps.setInt(6, zip);
		ps.setString(7, telNumber);
		ps.setString(8, bulstat);
		ps.setInt(9, userId);
		
		int rs = ps.executeUpdate();

		return rs == 1;
	}
	
	public boolean setLastLoginDateForUser(User user) throws SQLException {
		Connection c = db.getConn();
		PreparedStatement ps = c.prepareStatement(
				"UPDATE user SET last_login_date = NOW()  WHERE id_user = ?",
				Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, user.getId());
		int rs = ps.executeUpdate();
		return rs == 1;
	}

}
