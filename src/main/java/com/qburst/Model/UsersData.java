package com.qburst.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsersData {

	private String EmployeeID;
	

	private String Name;

	private String Email;

	@JsonProperty("idToken")
	private String idToken;

	private String userType;

	
	
	private String imageurl;
	 public String getImageurl() {
	return imageurl;

	}
	 public void setImageurl(String imageurl) {
	this.imageurl = imageurl;
	}
	
	
	
	public String getEmployeeID() {
		return EmployeeID;

	}

	public String getName() {
		return Name;
	}

	public String getEmail() {
		return Email;
	}

	public String getUserType() {
		return userType;
	}
	
	public String getToken() {
		return (idToken);
	}

	public void setEmployeeID(String EmployeeID) {
		this.EmployeeID = EmployeeID;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public void setEmail(String Email) {
		this.Email = Email;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void setToken(String idToken) {
		this.idToken = idToken;
	}
	


}
