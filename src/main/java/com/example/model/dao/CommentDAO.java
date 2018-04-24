package com.example.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.DBManager;
import com.example.model.pojo.Comment;
import com.example.model.pojo.User;

@Component
public class CommentDAO {

	@Autowired
	DBManager db ;
	
	public void insertComment(User user, Comment comment) throws SQLException{
		Connection con = db.getConn();
		PreparedStatement ps  = con.prepareStatement("INSERT INTO comments(text_comment,id_user) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, comment.getText());
		ps.setInt(2, user.getId());
		ps.executeQuery();
	}
	
	public Set<Comment> getAllCommentsForUser(User user) throws SQLException{
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT * FROM comments where id_user = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, user.getId());
		ResultSet rs = ps.executeQuery();		
		HashSet<Comment> comments = new HashSet<>();
		while(rs.next()){
			comments.add(new Comment(
					rs.getInt("id_comment"),
					rs.getString("text_comment"),
					rs.getInt("points"),
					rs.getDate("date_created"),
					user
					));
		}
		return comments;
	}
}
