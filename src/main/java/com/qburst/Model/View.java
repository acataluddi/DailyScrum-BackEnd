package com.qburst.Model;

import java.util.List;

public class View {
	private List<UsersData> employee_data;
	private List<ProjectData> project_names;
	private List<ProjectData> project_member_data;
	private List<TaskData> yesterday_task;
	private List<TaskData> today_task; 
	
	private int pagenum;
	private int numOfRec;
	private int pageid;
	
	public int getPageid() {
		return pageid;
	}

	public void setPageid(int pageid) {
		this.pageid = pageid;
	}

	public int getPagenum() {
		return pagenum;
	}

	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}

	public int getNum_of_rec() {
		return numOfRec;
	}

	public void setNumOfRec(int num_of_rec) {
		this.numOfRec = num_of_rec;
	}

	
	
	public List<ProjectData> getProjectNames() {
		return project_names;
	}
	
	public List<TaskData> getYesterdayTask() {
		return yesterday_task;
	}
	
	public List<TaskData> getTodayTask() {
		return today_task;
	}
	
	public List<ProjectData> getProjectMemberData() {
		return project_member_data;
	}
	
	public List<UsersData> getEmployeeData() {
		return employee_data;
	}
	
	public void setProjectNames(List<ProjectData> project_names) {
		this.project_names = project_names;
	}

	public void setYesterdayTask(List<TaskData> yesterday_task) {
		this.yesterday_task = yesterday_task;
	}

	public void setTodayTask(List<TaskData> today_task) {
		this.today_task = today_task;
	}
	
	public void setProjectMemberData(List<ProjectData> project_member_data) {
		this.project_member_data = project_member_data;
	}

	public void setEmployeeData(List<UsersData> employee_data) {
		this.employee_data = employee_data;
	}
	
	public List<UsersData> getEmployeeData(int pageno, int numOfRec) {
		return employee_data;
	}
	
}
