package com.example.model.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class User {

	private int id;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String address;
	private String avatarUrl;
	private Date dateCreated;
	private int rating;
	private Set<Order> orders;
	private Set<Comment> comments;
	
	
	
	
	public User() {
		// used in order constructor
	}
	
	public User(int id, String firstName, String lastName,  String email, 
			String address, String avatarUrl, Date dateCreated, int rating, Set<Order> orders, Set<Comment> comments) {
		this.setId(id);
		
		this.setFirstName(firstName);
		
		this.setLastName(lastName);
		
		this.setAddress(address);
	
		this.setAvatarUrl(avatarUrl);
		
		this.setEmail(email);
		
		this.setDateCreated(dateCreated);
		
		this.setRating(rating);
		
		this.setOrders(orders);
		
		this.setComments(comments);
		
	}
	/**Constructor used for registration
	 * @param String, String, String, String ,String
	 * Date must be set later
	*/
	
	public User(String firstName, String lastName, String password, String email, String address, int rating){
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setPassword(password);
		this.setEmail(email);
		this.setAddress(address);
		this.avatarUrl = new String();
		this.dateCreated = new Date();// must be set later!!!
		this.rating = rating;
		this.orders = new HashSet<>();
		this.comments = new HashSet<>();
	}
	
	public User(int id, String firstName, String lastName, String password, String email, String address, int rating){
		this(firstName, lastName, password, email, address,rating);
		this.setId(id);
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		if(avatarUrl == null || avatarUrl.isEmpty()){
			throw new IllegalArgumentException("Invalid User.avatarUrl entered!");
		}
		this.avatarUrl = avatarUrl;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		if(dateCreated == null){
			throw new IllegalArgumentException("User.dateCreated is null");
		}
		this.dateCreated = dateCreated;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		if(orders == null){
			throw new IllegalArgumentException("User.orders are null");
		}
		this.orders = orders;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		if(comments == null){
			throw new IllegalArgumentException("User.comments are null");
		}
		this.comments = comments;
	}

	public void setFirstName(String firstName) {
		if(firstName == null || firstName.isEmpty()){
			throw new IllegalArgumentException("Invalid User.firstName entered!");
		}
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		if(lastName == null || lastName.isEmpty()){
			throw new IllegalArgumentException("Invalid User.lastName entered!");
		}
		this.lastName = lastName;
	}

	public void setPassword(String password) {
		if(password == null || password.isEmpty()){
			throw new IllegalArgumentException("Invalid User.password entered!");
		}
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		if(address == null || address.isEmpty()){
			throw new IllegalArgumentException("Invalid User.address entered!");
		}
		this.address = address;
	}



	@Override
	public String toString() {
		return this.firstName+"::"+this.lastName+ "::"+this.email;
	}
	
	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public String getEmail() {
		return this.email;
	}

	public String getAddress() {
		return this.address;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		if(id < 1){
			throw new IllegalArgumentException("Invalid User.id!");
		}else{
			this.id = id;
		}
	}

	public void insertOrder(Order order) {
		if(order != null){
			this.orders.add(order);
		}else{
			throw new IllegalArgumentException();
		}
		
	}
	
	
	
	
	
}
