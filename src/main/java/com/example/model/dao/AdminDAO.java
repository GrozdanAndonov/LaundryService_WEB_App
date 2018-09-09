package com.example.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.DBManager;
import com.example.model.pojo.User;

@Component
public class AdminDAO {

	@Autowired
	DBManager db;
	@Autowired
	OrderDAO od;
	@Autowired
	UserDAO ud;
	
	
	public List<User> findAllUsersForAdminList() throws SQLException {
		Connection c = db.getConn();
		PreparedStatement ps = c.prepareStatement(
				"SELECT id_user, first_name, last_name, password, email, city, streetAddress,zipCode, rating, avatar_url,"
				+ "isAdmin,telNum, date_created, last_login_date, bulstat, default_language FROM user WHERE isAdmin = 0;",
				Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = ps.executeQuery();
		LinkedList<User> users = new LinkedList<>();
		while (rs.next()) {
			users.add(new User(rs.getInt("id_user"), rs.getString("first_name"), rs.getString("last_name"),
					rs.getString("email"), rs.getString("city"), rs.getInt("zipCode"), rs.getBoolean("isAdmin"),
					rs.getString("telNum"), rs.getString("streetAddress"), rs.getString("avatar_url"),
					rs.getDate("date_created"),rs.getTimestamp("last_login_date"), rs.getString("bulstat"), rs.getString("default_language"),
					rs.getInt("rating"), od.getOrdersForUser(new User(rs.getInt("id_user"))), new HashSet<>()));
		}
		return users;
	}
	
	
	public void deleteUser(User user) throws SQLException {
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("DELETE FROM user where id_user = ?;",
				Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, user.getId());
		ps.executeUpdate();
	}
	
	public boolean updateAdminSettings(User user , String firstName, String lastName, String email, String telNum) throws SQLException {
		Connection c = db.getConn();
		PreparedStatement ps = c.prepareStatement(
				"UPDATE user SET first_name = ?, last_name = ?, email = ?, telNum = ? WHERE id_user = ?",
				Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, firstName);
		ps.setString(2, lastName);
		ps.setString(3, email);
		ps.setString(4, telNum);
		ps.setInt(5, user.getId());
		int result = ps.executeUpdate();
		return result == 1;
	}
}
