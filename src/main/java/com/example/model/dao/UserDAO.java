package com.example.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.DBManager;
import com.example.model.pojo.User;

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
				"INSERT INTO user(first_name,last_name,password,email,address) VALUES(?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, user.getFirstName());
		ps.setString(2, user.getLastName());
		ps.setString(3, user.getPassword());
		ps.setString(4, user.getEmail());
		ps.setString(5, user.getAddress());
		ps.executeUpdate();
	}
	
	public int insertAvatarUrl(User user, String url) throws SQLException{
		if(url==null || url.isEmpty()){
			throw new IllegalArgumentException();
		}
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("UPDATE user SET avatar_url = ? WHERE id_user = ?",Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, url);
		ps.setInt(1, user.getId());
		return ps.executeUpdate();
	}
	
	
	
	public void deleteUser(User user) throws SQLException{
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("DELETE FROM user where id_user = ?;",Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, user.getId());
		ps.executeQuery();
	}
	
	public User getUserById(int id) throws SQLException{
		Connection c = db.getConn();
		PreparedStatement ps = c.prepareStatement("SELECT * FROM user WHERE id_user = ?;",Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return new User(rs.getInt("id_user"), rs.getString("first_name"), 
				rs.getString("last_name"), 
				rs.getString("password"),
				rs.getString("email"),
				rs.getString("address"),
				rs.getInt("rating"));
	}
	
			/** if email is not being used
			 * @return true
			 * @param String
			*/
	public boolean checksIfEmailIsNotFree(String email) throws SQLException {
		if(this.getResultForEmail(email)==0){
			return false;
		}
		return true;
	}
	
	public boolean checksIfEmailExists(String email) throws SQLException{
		if(this.getResultForEmail(email)==1){
			return true;
		}
		return false;	
	}
	
	
	public boolean checksIfPasswordAndEmailMatchesForUser(String password, String email) throws SQLException{
		Connection c = db.getConn();
		PreparedStatement ps = c.prepareStatement("SELECT email, password FROM user WHERE email = ?;",Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
		if(password.equals(rs.getString("password"))){
			return true;
		}
		}
		return false;
	}
	
	public User getFullUserByEmail(String email) throws SQLException{
		Connection c = db.getConn();
		PreparedStatement ps = c.prepareStatement("SELECT * FROM user WHERE email = ?;",Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		rs.next();
		User user = new User(rs.getInt("id_user"), rs.getString("first_name"), rs.getString("last_name"),rs.getString("password"), rs.getString("email"),
				rs.getString("address"), rs.getInt("rating"));
		return new User(rs.getInt("id_user"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString("email"),
						rs.getString("address"),
						rs.getString("avatar_url"),
						rs.getDate("date_created"),
						rs.getInt("rating"),
						od.getOrdersForUser(user),
						cd.getAllCommentsForUser(user));
	}
	
	private int getResultForEmail(String email) throws SQLException{
		Connection c = db.getConn();
		PreparedStatement ps = c.prepareStatement("SELECT COUNT(email) AS count FROM user where email = ?;",Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt("count");
	}

}
