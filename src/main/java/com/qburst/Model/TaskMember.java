package com.qburst.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskMember {
	@JsonProperty("name")
	private String name;
	@JsonProperty("email")
	private String email;
	@JsonProperty("image")
	private String image;
	@JsonProperty("role")
	private String role;
	@JsonProperty("tasks")
	TaskData[] tasks;
	@JsonProperty("hour")
	private int hour;
	@JsonProperty("minute")
	private int minute;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public TaskData[] getTasks() {
		return tasks;
	}
	public void setTasks(TaskData[] tasks) {
		this.tasks = tasks;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	
}
