package com.example.model.pojo;

import java.util.Date;

public class Discount {

	private int id;
	private double value;
	private Date dateStarted;
	private Date dateFinished;
	public Discount(int id, double value, Date dateStarted, Date dateFinished) {	
			this.setId(id);
			this.setValue(value);
			this.setDateStarted(dateStarted);
			this.setDateFinished(dateFinished);
	}
	
	public void setDateFinished(Date dateFinished) {
		if(dateFinished == null){
			throw new IllegalArgumentException("Discount.dateFinished is null!");
		}
		this.dateFinished = dateFinished;
	}
	
	public void setDateStarted(Date dateStarted) {
		if(dateStarted == null){
			throw new IllegalArgumentException("Discount.dateStarted is null!");
		}
		this.dateStarted = dateStarted;
	}
	
	public void setValue(double value) {
		if(value <= 0){
			throw new IllegalArgumentException("Invalid Discount.value entered!");
		}
		this.value = value;
	}
	
	public void setId(int id) {
		if(id < 1){
			throw new IllegalArgumentException("Invalid Discount.id entered!");
		}
		this.id = id;
	}
	
	
	public double getValue() {
		return value;
	}
	
	
}
