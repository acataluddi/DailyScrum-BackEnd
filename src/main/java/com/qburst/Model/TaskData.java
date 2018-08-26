package com.qburst.Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TaskData {
	
	private String taskDesc, impediment;
	private int memberId;
	private int projectId;
	private int taskId;
	LocalTime timeStamp;
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	public String getImpediment() {
		return impediment;
	}
	public void setImpediment(String impediment) {
		this.impediment = impediment;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public LocalTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	public LocalDate getTaskDate() {
		return taskDate;
	}
	public void setTaskDate(LocalDate taskDate) {
		this.taskDate = taskDate;
	}
	public LocalTime getTimeSpent() {
		return timeSpent;
	}
	public void setTimeSpent(LocalTime timeSpent) {
		this.timeSpent = timeSpent;
	}
	LocalDate taskDate;
	LocalTime timeSpent;
		
}