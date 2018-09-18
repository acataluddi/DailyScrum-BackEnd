package com.qburst.Model;

import org.mongojack.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectData {
	private String id;

	private String projectId;

	private String projectName, projectDesc;

	@JsonProperty("members")
	ProjectMemberModel[] members;

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
