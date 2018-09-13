package com.example.model.pojo;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.util.DateFormatConverter;

public class Order {

	private int id;
	private Date dateCreated;
	private Date dateFinished;
	private double cost;
	private Set<Discount> discounts;
	private User creator;
	private String firstName;
	private String lastName;
	private String email;
	private String city;
	private String streetAddress;
	private String telNumber;
	private String note;
	private boolean isExpress;
	private boolean isAccepted;
	private double totalDiscount;
	private double discount;

	private String dateCreatedForView;
	private String dateFinishedForView;
	
	/**
	 * Constructor needed for searching discount for order where only the id is needed.
	 * @param id needed id for searching
	 */
	public Order(int id) {
		this.setId(id);
		this.dateCreated = new Date();
		this.dateFinished = new Date();
		this.cost = 0;
		this.totalDiscount = 0;
		this.discounts = new HashSet<>();
		this.creator = new User();
	}
	
	
	public Order(User creator, String firstName, String lastName, String email, String city,
			String streetAddress, String telNumber, String note, boolean isExpress, boolean isAccepted) {
		this.setCreator(creator);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setCity(city);
		this.setStreetAddress(streetAddress);
		this.setTelNumber(telNumber);
		this.setNote(note);
		this.setIsExpress(isExpress);
		this.setAccepted(isAccepted);
	}
	
	
	
	
	/**
	 * Constructor for viewing orderDetails
	 */
	public Order(int id, Date dateCreated, Date dateFinished, double cost, String firstName,
			String lastName, Set<Discount> discounts,
			String email, String city, String streetAddress, String telNumber, String note,
			boolean isExpress,boolean isAccepted, double discount) {
		this(id, dateCreated, dateFinished, cost, firstName, lastName, discounts,new User());
		this.setEmail(email);
		this.setCity(city);
		this.setStreetAddress(streetAddress);
		this.setTelNumber(telNumber);
		this.setNote(note);
		this.setIsExpress(isExpress);
		this.setDiscount(discount);
		this.setTotalDiscount();
		this.setAccepted(isAccepted);
		try {
			this.setDateCreatedForView(DateFormatConverter.convertFromDBToListingOrders(dateCreated));
			if(dateFinished == null) {
				this.dateFinishedForView = "";
			}else {
			this.setDateFinishedForView(DateFormatConverter.convertFromDBToListingOrders(dateFinished));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * Constructor for listing all finished orders
	 * 
	 * @param id
	 * @param dateCreated
	 * @param dateFinished
	 * @param cost
	 * @param firstName
	 * @param lastName
	 * @param discounts
	 * @param creator
	 */
	public Order(int id, Date dateCreated, Date dateFinished, double cost, String firstName, String lastName , Set<Discount> discounts,User creator) {
		this(id,dateCreated, dateFinished, cost, creator);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setDiscounts(discounts);
		this.setCreator(creator);
		try {
			this.setDateCreatedForView(DateFormatConverter.convertFromDBToListingOrders(dateCreated));
			if(dateFinished == null) {
				this.dateFinishedForView = "";
			}else {
			this.setDateFinishedForView(DateFormatConverter.convertFromDBToListingOrders(dateFinished));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public Order(int id, Date dateCreated, Date dateFinished, double cost, User creator) {
		this(id);
		this.setDateCreated(dateCreated);
		this.setDateFinished(dateFinished);
		this.setCost(cost);
		this.setCreator(creator);
	}
	
	public Order(int id, Date dateCreated, Date dateFinished, double cost, Set<Discount> discounts, User creator) {
		this(id, dateCreated, dateFinished, cost, creator);
		this.setDiscounts(discounts);
		this.setTotalDiscount();
	}
		
	
	
	private void setTotalDiscount(){
		for (Discount discount : discounts) {
			this.totalDiscount+=discount.getValue();
		}
	}
	
	
	public void setCreator(User creator) {
		if(creator == null){
			throw new IllegalArgumentException("Order.creator is null!");
		}
		this.creator = creator;
	}
	
	public void setDiscounts(Set<Discount> discounts) {
		if(discounts == null){
			this.discounts = new HashSet<>();
		}else {
		this.discounts = discounts;
		}
	}
	
	public void setCost(double cost) {
		if(cost<0){
			throw new IllegalArgumentException("Invalid Order.cost entered!");
		}
		this.cost = cost;
	}
	
	public void setDateFinished(Date dateFinished) {
		this.dateFinished = dateFinished;
	}
	
	public void setDateCreated(Date dateCreated) {
		if(dateCreated == null){
			throw new IllegalArgumentException("Order.dateCreated is null!");
		}
		this.dateCreated = dateCreated;
	}
	
	public void setId(int id) {
		if(id<1){
			throw new IllegalArgumentException("Invalid Order.id entered!");
		}
		this.id = id;
	}
	
	public double getCost() {
		return cost;
	}
	
	public int getId() {
		return id;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if(email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Invalid Order.email entered!");
		}
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		if(city== null || city.isEmpty()) {
			throw new IllegalArgumentException("Invalid Order.city entered!");
		}
		this.city = city;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		if(streetAddress == null) {
			this.streetAddress = "";
		}else {
		this.streetAddress = streetAddress;
		}
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		if(telNumber == null) {
			this.telNumber = "";
		}else {
		this.telNumber = telNumber;
		}
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		if(note == null) {
			this.note = "";
		}else {
			this.note = note;
		}
	}

	public boolean getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(boolean isExpress) {
		this.isExpress = isExpress;
	}

	public double getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public Date getDateFinished() {
		return dateFinished;
	}

	public Set<Discount> getDiscounts() {
		return discounts;
	}

	public User getCreator() {
		return creator;
	}

	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		if(firstName== null || firstName.isEmpty()) {
			throw new IllegalArgumentException("Invalid Order.firstName entered!");
		}
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		if(lastName== null || lastName.isEmpty()) {
			throw new IllegalArgumentException("Invalid Order.lastName entered!");
		}
		this.lastName = lastName;
	}


	public boolean isAccepted() {
		return isAccepted;
	}


	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}


	public String getDateCreatedForView() {
		return dateCreatedForView;
	}


	public void setDateCreatedForView(String dateCreatedForView) {
		if(dateCreatedForView == null) {
			dateCreatedForView = "";
		}
		this.dateCreatedForView = dateCreatedForView;
	}


	public String getDateFinishedForView() {
		return dateFinishedForView;
	}


	public void setDateFinishedForView(String dateFinishedForView) {
		if(dateFinishedForView == null) {
			dateFinishedForView = "";
		}
		this.dateFinishedForView = dateFinishedForView;
	}

	public double getDiscount() {
		return discount;
	}


	public void setDiscount(double discount) {
		this.discount = discount;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
