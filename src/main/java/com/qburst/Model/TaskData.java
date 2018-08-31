package com.qburst.Model;

//import java.time.LocalDate;
//import java.time.LocalTime;
//
//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.qburst.serialization.LocalDateDeserializer;
//import com.qburst.serialization.LocalDateSerializer;
//import com.qburst.serialization.LocalTimeDeserializer;
//import com.qburst.serialization.LocalTimeSerializer;

public class TaskData {
	private String employeeId;

	private String taskId;
	private String taskDesc, impediment, timeStamp, taskDate;

	private int projectId;
	private int timeSpent;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

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

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(String taskDate) {
		this.taskDate = taskDate;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(int timeSpent) {
		this.timeSpent = timeSpent;
	}

	// @JsonDeserialize(using = LocalTimeDeserializer.class)
	// @JsonSerialize(using = LocalTimeSerializer.class)
	// LocalTime timeStamp;
	//
	// @JsonDeserialize(using = LocalDateDeserializer.class)
	// @JsonSerialize(using = LocalDateSerializer.class)
	// LocalDate taskDate;
	//
	// @JsonDeserialize(using = LocalTimeDeserializer.class)
	// @JsonSerialize(using = LocalTimeSerializer.class)
	// LocalTime timeSpent;

}