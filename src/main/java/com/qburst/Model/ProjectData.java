package com.qburst.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectData {

	private String projectName, projectDesc;

	private String projectId;
//	private String members[];
	
	@JsonProperty("members")
	ProjectMemberModel[] members;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDesc() {
		return projectDesc;
	}

	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}

	public ProjectMemberModel[] getMembers() {
		return members;
	}
	
	@JsonProperty("members")
	public void setMembers(ProjectMemberModel[] members) {
		this.members = members;
	}
}
