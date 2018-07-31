package com.qburst.Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TaskData {
	
	private String task_disc, impediment;
	private int member_id, page_id;
	LocalTime time_stamp;
	LocalDate task_date;
	LocalTime time_spent;
		
	public Integer getPageID() {
		return page_id;
	}
	
	public Integer getMemberID() {
		return member_id;
	}
	
	public String getTaskDescription() {
		return task_disc;
	}
	
	public LocalTime getTimeSpent() {
		return time_spent;
	}
	
	public LocalDate getDate() {
		task_date = LocalDate.now();
		return task_date;
	}
	
	public String getImpediment() {
		return impediment;
	}
	
	public LocalTime getTimeStamp() {
		time_stamp = LocalTime.now();
		return time_stamp;
	}
	
	public void setPageID(Integer page_id) {
		this.page_id = page_id;
	}
	
	public void setMemberID(Integer member_id) {
		this.member_id = member_id;
	}
	
	public void setTaskDescription(String task_disc) {
		this.task_disc = task_disc;
	}

	public void setTimeSpent(int hour, int minute) {
		time_spent  = LocalTime.of(hour, minute);
		this.time_spent = time_spent;
	}

	public void setDate(LocalDate task_date) {
		this.task_date = task_date;
	}

	public void setImpediment(String impediment) {
		this.impediment = impediment;
	}

	public void setTimeStamp(LocalTime time_stamp) {
		this.time_stamp = time_stamp;
	}
}
