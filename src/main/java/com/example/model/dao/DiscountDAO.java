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
import com.example.model.pojo.Discount;
import com.example.model.pojo.Order;


@Component
public class DiscountDAO {

	@Autowired
	DBManager db;
	
	public Set<Discount> getDiscountsForOrder(Order order) throws SQLException{
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT d.id_discount, d.date_started, d.date_ended, value"
				+ " FROM discount d JOIN order_discount od on d.id_discount = od.id_discount WHERE od.id_order = ?;",Statement.NO_GENERATED_KEYS);
		ps.setInt(1, order.getId());
		 ps.executeQuery();
		 ResultSet rs = ps.getResultSet();
		 Set<Discount> discounts = new HashSet<Discount>();
		 while(rs.next()){
			 discounts.add(new Discount(rs.getInt("id_discount"),
					 						rs.getDouble("value"),
					 					rs.getDate("date_started"),
					 					rs.getDate("date_ended")
					 					));
		 }
		 return discounts;
	}
	
}
