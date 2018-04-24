package com.example.model.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order {

	private int id;
	private Date dateCreated;
	private Date dateFinished;
	private double cost;
	private double totalDiscount;
	private Set<Discount> discounts;
	private User creator;
	
	public Order(int id) {
		this.setId(id);
		this.dateCreated = new Date();
		this.dateFinished = new Date();
		this.cost = 0;
		this.totalDiscount = 0;
		this.discounts = new HashSet<>();
		this.creator = new User();
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
			throw new IllegalArgumentException("Order.discounts is null!");
		}
		this.discounts = discounts;
	}
	
	public void setCost(double cost) {
		if(cost<0){
			throw new IllegalArgumentException("Invalid Order.cost entered!");
		}
		this.cost = cost;
	}
	
	public void setDateFinished(Date dateFinished) {
		if(dateFinished == null){
			throw new IllegalArgumentException("Order.dateFinished is null!");
		}
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
