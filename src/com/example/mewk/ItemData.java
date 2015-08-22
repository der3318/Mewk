package com.example.mewk;

public class ItemData {
	
	public String itemname;
	public String description;
	public String deadline;
	public String username;
	public String phone;
	public String email;
	public String location;
	
	public ItemData (String itemname, String description, String deadline, String username, String phone, String email, String location) {
		this.itemname = itemname;
		this.description = description;
		this.deadline = deadline;
		this.username = username;
		this.phone = phone;
		this.email = email;
		this.location = location;
	}	
}