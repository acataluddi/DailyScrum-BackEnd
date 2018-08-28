package com.qburst.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsersData {

	private String EmployeeID;

	private String name;

	private String email;

	@JsonProperty("idToken")
	private String idToken;

	private String userType;

	public String getEmployeeID() {
		return EmployeeID;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	public void setToken(String idToken) {
		this.idToken = idToken;
	}
}
