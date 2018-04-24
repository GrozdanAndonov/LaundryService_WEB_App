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
import com.example.model.pojo.Order;
import com.example.model.pojo.User;

@Component
public class OrderDAO {

	@Autowired
	DBManager db;
	
	@Autowired 
	DiscountDAO dd;
	
	public void insertOrderForUser(User user, Order order) throws SQLException{
		Connection con = db.getConn();		
		PreparedStatement ps = con.prepareStatement("INSERT INTO orders(cost, id_user) VALUES(?, ?);",Statement.RETURN_GENERATED_KEYS);
		ps.setDouble(1, order.getCost());
		ps.setInt(2, user.getId());
		ps.executeQuery();
		ResultSet rs = ps.getResultSet();
		order.setId(rs.getInt(1));
		user.insertOrder(order);
	}
	
	public void deleteOrder(Order order) throws SQLException{
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("DELETE FROM orders WHERE id_order = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, order.getId());
		ps.executeQuery();
	}
	
	public Set<Order> getOrdersForUser(User user) throws SQLException{
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT * FROM orders WHERE id_user = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, user.getId());
		ps.executeQuery();
		ResultSet rs = ps.getResultSet();
		Set<Order> orders = new HashSet<>();
		while(rs.next()){
			orders.add(new Order(
						rs.getInt("id_order"),
						rs.getDate("date_creation"),
						rs.getDate("date_finished"),
						rs.getDouble("cost"),
						dd.getDiscountsForOrder(new Order(rs.getInt("id_order"))),
						user));
		}
		
		return orders;
	}
	
	
}
