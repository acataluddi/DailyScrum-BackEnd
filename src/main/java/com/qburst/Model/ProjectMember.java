package com.qburst.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectMember {
	@JsonProperty("email")
	private String email;
	@JsonProperty("role")
	private String role;
	@JsonProperty("name")
	private String name;
	@JsonProperty("image")
	private String image;
	@JsonProperty("addedDate")
	private String addedDate;
	@JsonProperty("deletedDate")
	private String deletedDate;
	@JsonProperty("isActive")
	private boolean isActive;

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

	public String getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
	}

	public String getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(String deletedDate) {
		this.deletedDate = deletedDate;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
}
