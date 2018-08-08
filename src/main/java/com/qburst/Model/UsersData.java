package com.qburst.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsersData {

	private String name;
	
	private String email;
		
	private String userType;
	
	private int memberID;
	
	@JsonProperty("pageID")
	private int pageID;
	
	public Integer getPageID() {
		return pageID;
	}
	
	public Integer getMemberID() {
		return memberID;
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
	
	
	public void setPageID(Integer pageID) {
		this.pageID = pageID;
	}
	
	public void setMemberID(Integer memberID) {
		this.memberID = memberID;
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
}
