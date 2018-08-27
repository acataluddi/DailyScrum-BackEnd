package com.qburst.Model;

public class UsersData {
	
	private String memberID;

	private String name;
	
	private String email;
		
	private String userType;

	public String getMemberID() {
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

	public void setMemberID(String memberID) {
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
