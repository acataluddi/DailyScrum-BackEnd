package com.qburst.Model;

public class TaskData {

	private String memberEmail;
	private String taskId;
	private String description;
	private String impediments;
	private String projectId;
	private String taskDate;
	private String lastEdit;

	private int hourSpent;
	private int minuteSpent;

	private boolean taskCompleted;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImpediments() {
		return impediments;
	}

	public void setImpediments(String impediment) {
		this.impediments = impediment;
	}

	public String getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(String taskDate) {
		this.taskDate = taskDate;
	}

	public int getHourSpent() {
		return hourSpent;
	}

	public void setHourSpent(int hourSpent) {
		this.hourSpent = hourSpent;
	}

	public int getMinuteSpent() {
		return minuteSpent;
	}

	public void setMinuteSpent(int minuteSpent) {
		this.minuteSpent = minuteSpent;
	}

	public Boolean getTaskCompleted() {
		return taskCompleted;
	}

	public void setTaskCompleted(Boolean taskCompleted) {
		this.taskCompleted = taskCompleted;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getLastEdit() {
		return lastEdit;
	}

	public void setLastEdit(String lastEdit) {
		this.lastEdit = lastEdit;
	}

}