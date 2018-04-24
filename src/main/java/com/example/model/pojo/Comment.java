package com.example.model.pojo;

import java.util.Date;

public class Comment {

	private int id;
	private String text;
	private int points;
	private Date dateCreated;
	private User creator;
	public Comment(int id, String text, int points, Date dateCreated, User creator) {	
			this.setId(id);
			this.setText(text);
			this.setDateCreated(dateCreated);
			this.setCreator(creator);
			this.points = points;
	}
	
	
	
	public void setId(int id) {
		if(id < 0){
			throw new IllegalArgumentException("Invalid Comment.id entered!");
		}
		this.id = id;
	}
	
	public void setDateCreated(Date dateCreated) {
		if(dateCreated == null){
			throw new IllegalArgumentException("Comment.dateCreated is null!");
		}
		this.dateCreated = dateCreated;
	}
	
	public void setText(String text) {
		if(text == null || text.isEmpty()){
			throw new IllegalArgumentException("Invalid Comment.text entered!");
		}
		this.text = text;
	}
	
	public void setCreator(User creator) {
		if(creator == null){
			throw new IllegalArgumentException("Comment.creator is null!");
		}
		this.creator = creator;
	}
	
	
		
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return this.id+" : "+this.text+" : "+this.dateCreated;
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
		Comment other = (Comment) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
