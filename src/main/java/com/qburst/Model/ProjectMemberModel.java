package com.qburst.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectMemberModel {
	@JsonProperty("email")
	private String email;
	@JsonProperty("role")
	private String role;
	@JsonProperty("name")
	private String name;
	@JsonProperty("image")
	private String image;

	public String getemail() {
		return email;
	}

	public void setemail(String email) {
		this.email = email;
	}

	public String getrole() {
		return role;
	}

	public void setrole(String role) {
		this.role = role;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public String getimage() {
		return image;
	}

	public void setimage(String image) {
		this.image = image;
	}
}
