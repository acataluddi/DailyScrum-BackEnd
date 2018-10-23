package com.qburst.Model;

import org.mongojack.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoalMember {
	
	private String id;
	
	private String userId;
	
	private String userName;
	
	private String userEmail;
	
	private String userImage;
	
	private String lastUpdate;
	
	private boolean hasNewUpdates;
	
	@JsonProperty("goals")
	private Goal[] goals;

	@ObjectId
	@JsonProperty("_id")
	public String getId() {
		return id;
	}

	@ObjectId
	@JsonProperty("_id")
	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public boolean getHasNewUpdates() {
		return hasNewUpdates;
	}

	public void setHasNewUpdates(boolean hasNewUpdates) {
		this.hasNewUpdates = hasNewUpdates;
	}

	public Goal[] getGoals() {
		return goals;
	}

	public void setGoals(Goal[] goals) {
		this.goals = goals;
	}

}
