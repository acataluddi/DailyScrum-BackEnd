package com.qburst.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Goal {
	
	private String goalId;
	
	private String goalTime;
	
	private String goalTitle;
	
	private String goalDescription;
	
	private String managerEmail;
	
	private String managerName;
	
	private String managerImage;
	
	private String userEmail;
	
	private boolean hasNewUpdates;
	
	@JsonProperty("comments")
	private Comment[] comments;

	public String getGoalId() {
		return goalId;
	}

	public void setGoalId(String goalId) {
		this.goalId = goalId;
	}

	public String getGoalTime() {
		return goalTime;
	}

	public void setGoalTime(String goalTime) {
		this.goalTime = goalTime;
	}

	public String getGoalTitle() {
		return goalTitle;
	}

	public void setGoalTitle(String goalTitle) {
		this.goalTitle = goalTitle;
	}

	public String getGoalDescription() {
		return goalDescription;
	}

	public void setGoalDescription(String goalDescription) {
		this.goalDescription = goalDescription;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerImage() {
		return managerImage;
	}

	public void setManagerImage(String managerImage) {
		this.managerImage = managerImage;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public boolean getHasNewUpdates() {
		return hasNewUpdates;
	}

	public void setHasNewUpdates(boolean hasNewUpdates) {
		this.hasNewUpdates = hasNewUpdates;
	}

	public Comment[] getComments() {
		return comments;
	}

	public void setComments(Comment[] comments) {
		this.comments = comments;
	}

}
