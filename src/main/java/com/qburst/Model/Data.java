package com.qburst.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

	private String name;
	
	private String email;
	
	private String password;
	
	private String role;
	
	@JsonProperty("member_id")
	private int member_id;
	
	@JsonProperty("page_id")
	private int page_id;
	
	public Integer getPageID() {
		return page_id;
	}
	
	public Integer getMemberID() {
		return member_id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}
	
	
	public void setPageID(Integer page_id) {
		this.page_id = page_id;
	}
	
	public void setMemberID(Integer member_id) {
		this.member_id = member_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
