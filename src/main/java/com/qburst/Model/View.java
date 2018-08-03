package com.qburst.Model;

import java.util.List;

public class View {
	private List<UsersData> project_names, yesterday_task, today_task, project_member_data, employee_data;
	
	public List<UsersData> getProjectNames() {
		return project_names;
	}
	
	public List<UsersData> getYesterdayTask() {
		return yesterday_task;
	}
	
	public List<UsersData> getTodayTask() {
		return today_task;
	}
	
	public List<UsersData> getProjectMemberData() {
		return project_member_data;
	}
	
	public List<UsersData> getEmployeeData() {
		return employee_data;
	}
	
	public void setProjectNames(List<UsersData> project_names) {
		this.project_names = project_names;
	}

	public void setYesterdayTask(List<UsersData> yesterday_task) {
		this.yesterday_task = yesterday_task;
	}

	public void setTodayTask(List<UsersData> today_task) {
		this.today_task = today_task;
	}
	
	public void setProjectMemberData(List<UsersData> project_member_data) {
		this.project_member_data = project_member_data;
	}

	public void setEmployeeData(List<UsersData> employee_data) {
		this.employee_data = employee_data;
	}
}
