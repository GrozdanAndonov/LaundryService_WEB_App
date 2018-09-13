package com.example.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.DBManager;
import com.example.model.pojo.Order;
import com.example.model.pojo.User;
import com.example.util.DateFormatConverter;

@Component
public class OrderDAO {

	@Autowired
	DBManager db;
	
	@Autowired 
	DiscountDAO dd;
	
	public void createOrderForUser(User user, Order order) throws SQLException{
		Connection con = db.getConn();		
		PreparedStatement ps = con.prepareStatement("INSERT INTO orders(email, city, streetAddress, telNum, note, type_of_order, id_user, first_name, last_name) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);",Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, order.getEmail());
		ps.setString(2, order.getCity());
		ps.setString(3, order.getStreetAddress());
		ps.setString(4, order.getTelNumber());
		ps.setString(5, order.getNote());
		ps.setBoolean(6, order.getIsExpress());
		ps.setInt(7, user.getId());
		ps.setString(8, order.getFirstName());
		ps.setString(9, order.getLastName());
		ps.executeUpdate();
		user.insertOrder(order); 
	}
	
	public Set<Order> getOrdersForUser(User user) throws SQLException{
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT id_order, date_creation, date_finished, first_name, last_name, email, city,"
				+ "streetAddress, telNum, cost, note, discount, type_of_order, is_accepted FROM orders WHERE id_user = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, user.getId());
		ps.executeQuery();
		ResultSet rs = ps.getResultSet();
		Set<Order> orders = new HashSet<>();
		while(rs.next()){
			orders.add(new Order(
						rs.getInt("id_order"),
						rs.getTimestamp("date_creation"),
						rs.getTimestamp("date_finished"),
						rs.getDouble("cost"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						dd.getDiscountsForOrder(new Order(rs.getInt("id_order"))),
						rs.getString("email"),
						rs.getString("city"),
						rs.getString("streetAddress"),
						rs.getString("telNum"),
						rs.getString("note"),
						rs.getBoolean("type_of_order"),
						rs.getBoolean("is_accepted"),
						rs.getDouble("discount")
						));
		}
		
		return orders;
	}
	
	public Order findOrderByIdForUser(User user, Order order) throws SQLException {
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT id_order,first_name, last_name, date_creation, date_finished, email, city,"
				+ "streetAddress, telNum, cost, note, discount, type_of_order, is_accepted FROM orders WHERE id_order= ? AND id_user= ?;", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, order.getId());
		ps.setInt(2, user.getId());
		ps.executeQuery();
	
		ResultSet rs = ps.getResultSet();
		rs.next();
		return new Order(
				rs.getInt("id_order"),
				rs.getTimestamp("date_creation"),
				rs.getTimestamp("date_finished"),
				rs.getDouble("cost"),
				rs.getString("first_name"),
				rs.getString("last_name"),
				dd.getDiscountsForOrder(new Order(rs.getInt("id_order"))),
				rs.getString("email"),
				rs.getString("city"),
				rs.getString("streetAddress"),
				rs.getString("telNum"),
				rs.getString("note"),
				rs.getBoolean("type_of_order"),
				rs.getBoolean("is_accepted"),
				rs.getDouble("discount")
				);
	}
	
	public Set<Order> searchCheckedOrdersForUserBetweenDates(User user, String dateFrom, String dateTo) throws SQLException{
		return searchUnfinishedOrders( user,  dateFrom,  dateTo, 1);
	}
	
	
	public Set<Order> searchUncheckedOrdersForUserBetweenDates(User user, String dateFrom, String dateTo) throws SQLException{
		return searchUnfinishedOrders( user,  dateFrom,  dateTo, 0);
	}
	
	private Set<Order> searchUnfinishedOrders(User user, String dateFrom, String dateTo, int isChecked) throws SQLException{
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT id_order,first_name, last_name, date_creation, date_finished, email, city,"
				+ "streetAddress, telNum, cost, note, discount, type_of_order FROM orders WHERE date_creation BETWEEN ? AND  ? AND date_finished IS NULL AND id_user= ? AND is_accepted = "+isChecked+";", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, dateFrom+":00");
		ps.setString(2, dateTo+":59");
		ps.setInt(3, user.getId());
		ResultSet rs = ps.executeQuery();
		Set<Order> orders = new HashSet<>();
		while(rs.next()) {
			orders.add(new Order(
					rs.getInt("id_order"),
					rs.getTimestamp("date_creation"),
					rs.getTimestamp("date_finished"),
					rs.getDouble("cost"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					dd.getDiscountsForOrder(new Order(rs.getInt("id_order"))),
					user
					));
		}
		return orders;
	}
	
	
	public Set<Order> searchFinishedOrdersForUserBetweenDates(User user, String dateFrom, String dateTo) throws SQLException, ParseException {
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT id_order,first_name, last_name, date_creation, date_finished, email, city,"
				+ "streetAddress, telNum, cost, note, discount, type_of_order FROM orders WHERE date_creation BETWEEN ? AND  ? AND date_finished IS NOT NULL AND id_user= ?;", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, dateFrom+":00");
		ps.setString(2, dateTo+":59");
		ps.setInt(3, user.getId());
		ps.executeQuery();
		ResultSet rs = ps.getResultSet();
		Set<Order> orders = new HashSet<>();
		while(rs.next()) {
			orders.add(new Order(
					rs.getInt("id_order"),
					rs.getTimestamp("date_creation"),
					rs.getTimestamp("date_finished"),
					rs.getDouble("cost"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					dd.getDiscountsForOrder(new Order(rs.getInt("id_order"))),
					user
					));
		}
		return orders;
	}
	
	public boolean deleteOrderForUser(Order order, User user) throws SQLException {
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("DELETE FROM orders WHERE id_order = ? AND id_user = ? AND is_accepted = 0;", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, order.getId());
		ps.setInt(2, user.getId());
		 return ps.executeUpdate() == 1;
	}
	
	
	public boolean updateOrder(User user, String firstName, String lastName, String telNum, String city, 
			String strAddress, String email, String note, boolean isExpress, int orderId) throws SQLException {
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("UPDATE orders SET first_name=?,"
				+ " last_name = ?, email = ?, city = ? , streetAddress = ?, telNum = ?,"
				+ " note = ? , type_of_order = ? WHERE id_order = ? AND id_user = ?"
				+ " AND is_accepted = 0;", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, firstName);
		ps.setString(2, lastName);
		ps.setString(3, email);
		ps.setString(4, city);
		ps.setString(5, strAddress);
		ps.setString(6, telNum);
		ps.setString(7, note);
		ps.setBoolean(8, isExpress);
		ps.setInt(9, orderId);
		ps.setInt(10, user.getId());
		 return ps.executeUpdate() == 1;
	}
	
	
	public List<Order> findAllUncheckedUnexpress() throws SQLException{
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT id_order,first_name, last_name, date_creation, date_finished, email, city,"
				+ "streetAddress, telNum, cost, note, discount, type_of_order FROM orders WHERE"
				+ " date_finished IS NULL  AND is_accepted = 0 AND type_of_order = 0;", Statement.RETURN_GENERATED_KEYS);
		ps.executeQuery();
		ResultSet rs = ps.getResultSet();
		List<Order> orders = new LinkedList<Order>();
		while(rs.next()) {
			orders.add(new Order(
					rs.getInt("id_order"),
					rs.getTimestamp("date_creation"),
					rs.getTimestamp("date_finished"),
					rs.getDouble("cost"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					dd.getDiscountsForOrder(new Order(rs.getInt("id_order"))),
					new User()
					));
		}
		return orders;
	}
	
	public List<Order> findAllUncheckedExpress() throws SQLException{
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT id_order,first_name, last_name, date_creation, date_finished, email, city,"
				+ "streetAddress, telNum, cost, note, discount, type_of_order FROM orders WHERE"
				+ " date_finished IS NULL  AND is_accepted = 0 AND type_of_order = 1;", Statement.RETURN_GENERATED_KEYS);
		ps.executeQuery();
		ResultSet rs = ps.getResultSet();
		List<Order> orders = new LinkedList<Order>();
		while(rs.next()) {
			orders.add(new Order(
					rs.getInt("id_order"),
					rs.getTimestamp("date_creation"),
					rs.getTimestamp("date_finished"),
					rs.getDouble("cost"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					dd.getDiscountsForOrder(new Order(rs.getInt("id_order"))),
					new User()
					));
		}
		return orders;
	}
	
	public Order findUncheckedUnExpressOrderById(int id) throws SQLException {
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT id_order,first_name, last_name,"
				+ " date_creation, date_finished, email, city,"
				+ "streetAddress, telNum, cost, note, discount, "
				+ "type_of_order, is_accepted FROM orders WHERE id_order= ? "
				+ "AND date_finished IS NULL  AND is_accepted = 0 "
				+ "AND type_of_order = 0;", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, id);
		ps.executeQuery();
	
		ResultSet rs = ps.getResultSet();
		rs.next();
		return new Order(
				rs.getInt("id_order"),
				rs.getTimestamp("date_creation"),
				rs.getTimestamp("date_finished"),
				rs.getDouble("cost"),
				rs.getString("first_name"),
				rs.getString("last_name"),
				dd.getDiscountsForOrder(new Order(rs.getInt("id_order"))),
				rs.getString("email"),
				rs.getString("city"),
				rs.getString("streetAddress"),
				rs.getString("telNum"),
				rs.getString("note"),
				rs.getBoolean("type_of_order"),
				rs.getBoolean("is_accepted"),
				rs.getDouble("discount")
				);
	}
	
	public Order findUncheckedExpressOrderById(int id) throws SQLException {
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT id_order,first_name, last_name,"
				+ " date_creation, date_finished, email, city,"
				+ "streetAddress, telNum, cost, note, discount, "
				+ "type_of_order, is_accepted FROM orders WHERE id_order= ? "
				+ "AND date_finished IS NULL  AND is_accepted = 0 "
				+ "AND type_of_order = 1;", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, id);
		ps.executeQuery();
	
		ResultSet rs = ps.getResultSet();
		rs.next();
		return new Order(
				rs.getInt("id_order"),
				rs.getTimestamp("date_creation"),
				rs.getTimestamp("date_finished"),
				rs.getDouble("cost"),
				rs.getString("first_name"),
				rs.getString("last_name"),
				dd.getDiscountsForOrder(new Order(rs.getInt("id_order"))),
				rs.getString("email"),
				rs.getString("city"),
				rs.getString("streetAddress"),
				rs.getString("telNum"),
				rs.getString("note"),
				rs.getBoolean("type_of_order"),
				rs.getBoolean("is_accepted"),
				rs.getDouble("discount")
				);
	}
	
	public Order findCheckedUnExpressOrderById(int id) throws SQLException {
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT id_order,first_name, last_name,"
				+ " date_creation, date_finished, email, city,"
				+ "streetAddress, telNum, cost, note, discount, "
				+ "type_of_order, is_accepted FROM orders WHERE id_order= ? "
				+ "AND date_finished IS NULL  AND is_accepted = 1 "
				+ "AND type_of_order = 0;", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, id);
		ps.executeQuery();
	
		ResultSet rs = ps.getResultSet();
		rs.next();
		return new Order(
				rs.getInt("id_order"),
				rs.getTimestamp("date_creation"),
				rs.getTimestamp("date_finished"),
				rs.getDouble("cost"),
				rs.getString("first_name"),
				rs.getString("last_name"),
				dd.getDiscountsForOrder(new Order(rs.getInt("id_order"))),
				rs.getString("email"),
				rs.getString("city"),
				rs.getString("streetAddress"),
				rs.getString("telNum"),
				rs.getString("note"),
				rs.getBoolean("type_of_order"),
				rs.getBoolean("is_accepted"),
				rs.getDouble("discount")
				);
	}
	
	public Order findCheckedExpressOrderById(int id) throws SQLException {
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT id_order,first_name, last_name,"
				+ " date_creation, date_finished, email, city,"
				+ "streetAddress, telNum, cost, note, discount, "
				+ "type_of_order, is_accepted FROM orders WHERE id_order= ? "
				+ "AND date_finished IS NULL  AND is_accepted = 1 "
				+ "AND type_of_order = 1;", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, id);
		ps.executeQuery();
	
		ResultSet rs = ps.getResultSet();
		rs.next();
		return new Order(
				rs.getInt("id_order"),
				rs.getTimestamp("date_creation"),
				rs.getTimestamp("date_finished"),
				rs.getDouble("cost"),
				rs.getString("first_name"),
				rs.getString("last_name"),
				dd.getDiscountsForOrder(new Order(rs.getInt("id_order"))),
				rs.getString("email"),
				rs.getString("city"),
				rs.getString("streetAddress"),
				rs.getString("telNum"),
				rs.getString("note"),
				rs.getBoolean("type_of_order"),
				rs.getBoolean("is_accepted"),
				rs.getDouble("discount")
				);
	}

	
	public boolean setUncheckedOrderToChecked(int orderId) throws SQLException {
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("UPDATE orders SET is_accepted = 1"
				+ " WHERE id_order = ? AND is_accepted = 0;", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, orderId);
		return ps.executeUpdate() == 1;
	}
	
	public boolean setUnfinishedOrderToFinished(int orderId, double price, double discount) throws SQLException {
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("UPDATE orders SET date_finished = NOW(), cost= ?, discount = ?"
				+ " WHERE id_order = ? AND is_accepted = 1;", Statement.RETURN_GENERATED_KEYS);
		ps.setDouble(1, price);
		ps.setDouble(2, discount);
		ps.setInt(3, orderId);
		return ps.executeUpdate() == 1;
	}
	
	
	public List<Order> findAllCheckedUnExpressOrders() throws SQLException{
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT id_order,first_name, last_name, date_creation, date_finished, email, city,"
				+ "streetAddress, telNum, cost, note, discount, type_of_order FROM orders WHERE"
				+ " date_finished IS NULL  AND is_accepted = 1 AND type_of_order = 0;", Statement.RETURN_GENERATED_KEYS);
		ps.executeQuery();
		ResultSet rs = ps.getResultSet();
		List<Order> orders = new LinkedList<Order>();
		while(rs.next()) {
			orders.add(new Order(
					rs.getInt("id_order"),
					rs.getTimestamp("date_creation"),
					rs.getTimestamp("date_finished"),
					rs.getDouble("cost"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					dd.getDiscountsForOrder(new Order(rs.getInt("id_order"))),
					new User()
					));
		}
		return orders;
	}
	
	public List<Order> findAllCheckedExpressOrders() throws SQLException{
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT id_order,first_name, last_name, date_creation, date_finished, email, city,"
				+ "streetAddress, telNum, cost, note, discount, type_of_order FROM orders WHERE"
				+ " date_finished IS NULL  AND is_accepted = 1 AND type_of_order = 1;", Statement.RETURN_GENERATED_KEYS);
		ps.executeQuery();
		ResultSet rs = ps.getResultSet();
		List<Order> orders = new LinkedList<Order>();
		while(rs.next()) {
			orders.add(new Order(
					rs.getInt("id_order"),
					rs.getTimestamp("date_creation"),
					rs.getTimestamp("date_finished"),
					rs.getDouble("cost"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					dd.getDiscountsForOrder(new Order(rs.getInt("id_order"))),
					new User()
					));
		}
		return orders;
	}
	
	public List<Order> findAllFinishedUnExpressOrdersBetweenDates(String dateFrom, String dateTo) throws SQLException{
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT id_order,first_name, last_name, date_creation, date_finished, email, city,"
				+ "streetAddress, telNum, cost, note, discount, type_of_order FROM orders WHERE date_creation BETWEEN ? AND  ? AND date_finished IS NOT NULL AND is_accepted = 1 AND type_of_order = 0;", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, dateFrom+":00");
		ps.setString(2, dateTo+":59");
		ps.executeQuery();
		ResultSet rs = ps.getResultSet();
		List<Order> orders = new LinkedList<>();
		while(rs.next()) {
			orders.add(new Order(
					rs.getInt("id_order"),
					rs.getTimestamp("date_creation"),
					rs.getTimestamp("date_finished"),
					rs.getDouble("cost"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					dd.getDiscountsForOrder(new Order(rs.getInt("id_order"))),
					new User()
					));
		}
		return orders;
	}
	
	
	public List<Order> findAllFinishedExpressOrdersBetweenDates(String dateFrom, String dateTo) throws SQLException{
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT id_order,first_name, last_name, date_creation, date_finished, email, city,"
				+ "streetAddress, telNum, cost, note, discount, type_of_order FROM orders WHERE date_creation BETWEEN ? AND  ? AND date_finished IS NOT NULL AND is_accepted = 1 AND type_of_order = 1;", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, dateFrom+":00");
		ps.setString(2, dateTo+":59");
		ps.executeQuery();
		ResultSet rs = ps.getResultSet();
		List<Order> orders = new LinkedList<>();
		while(rs.next()) {
			orders.add(new Order(
					rs.getInt("id_order"),
					rs.getTimestamp("date_creation"),
					rs.getTimestamp("date_finished"),
					rs.getDouble("cost"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					dd.getDiscountsForOrder(new Order(rs.getInt("id_order"))),
					new User()
					));
		}
		return orders;
	}
	
	public Order findFinishedUnExpressOrderById(int id) throws SQLException {
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT id_order,first_name, last_name,"
				+ " date_creation, date_finished, email, city,"
				+ "streetAddress, telNum, cost, note, discount, "
				+ "type_of_order, is_accepted FROM orders WHERE id_order= ? "
				+ "AND date_finished IS NOT NULL  AND is_accepted = 1 "
				+ "AND type_of_order = 0;", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, id);
		ps.executeQuery();
	
		ResultSet rs = ps.getResultSet();
		rs.next();
		return new Order(
				rs.getInt("id_order"),
				rs.getTimestamp("date_creation"),
				rs.getTimestamp("date_finished"),
				rs.getDouble("cost"),
				rs.getString("first_name"),
				rs.getString("last_name"),
				dd.getDiscountsForOrder(new Order(rs.getInt("id_order"))),
				rs.getString("email"),
				rs.getString("city"),
				rs.getString("streetAddress"),
				rs.getString("telNum"),
				rs.getString("note"),
				rs.getBoolean("type_of_order"),
				rs.getBoolean("is_accepted"),
				rs.getDouble("discount")
				);
	}

	public Order findFinishedExpressOrderById(int id) throws SQLException {
		Connection con = db.getConn();
		PreparedStatement ps = con.prepareStatement("SELECT id_order,first_name, last_name,"
				+ " date_creation, date_finished, email, city,"
				+ "streetAddress, telNum, cost, note, discount, "
				+ "type_of_order, is_accepted FROM orders WHERE id_order= ? "
				+ "AND date_finished IS NOT NULL  AND is_accepted = 1 "
				+ "AND type_of_order = 1;", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, id);
		ps.executeQuery();
	
		ResultSet rs = ps.getResultSet();
		rs.next();
		return new Order(
				rs.getInt("id_order"),
				rs.getTimestamp("date_creation"),
				rs.getTimestamp("date_finished"),
				rs.getDouble("cost"),
				rs.getString("first_name"),
				rs.getString("last_name"),
				dd.getDiscountsForOrder(new Order(rs.getInt("id_order"))),
				rs.getString("email"),
				rs.getString("city"),
				rs.getString("streetAddress"),
				rs.getString("telNum"),
				rs.getString("note"),
				rs.getBoolean("type_of_order"),
				rs.getBoolean("is_accepted"),
				rs.getDouble("discount")
				);
	}
	
}
