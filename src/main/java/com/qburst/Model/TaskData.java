package com.qburst.Model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qburst.serialization.LocalDateDeserializer;
import com.qburst.serialization.LocalDateSerializer;
import com.qburst.serialization.LocalTimeDeserializer;
import com.qburst.serialization.LocalTimeSerializer;

public class TaskData {

	private int taskId;
	private String taskDesc, impediment;
	private int memberId;
	private int projectId;

	@JsonDeserialize(using = LocalTimeDeserializer.class)
	@JsonSerialize(using = LocalTimeSerializer.class)
	LocalTime timeStamp;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	LocalDate taskDate;

	@JsonDeserialize(using = LocalTimeDeserializer.class)
	@JsonSerialize(using = LocalTimeSerializer.class)
	LocalTime timeSpent;

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
}