package com.qburst.Model;

public class ProjectData {
	
	private String project_name, project_disc;
	private int member_id, page_id;
	
	public Integer getPageID() {
		return page_id;
	}
	
	public Integer getMemberID() {
		return member_id;
	}
	
	public String getProjectName() {
		return project_name;
	}
	
	public String getProjectDescription() {
		return project_disc;
	}
	
	public void setPageID(Integer page_id) {
		this.page_id = page_id;
	}
	
	public void setMemberID(Integer member_id) {
		this.member_id = member_id;
	}
	
	public void setProjectName(String project_name) {
		this.project_name = project_name;
	}

	public void setProjectDescription(String project_disc) {
		this.project_disc = project_disc;
	}

}
