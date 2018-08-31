package com.qburst.Model;

public class ProjectData {

	private String projectName, projectDesc;

	private String projectId;
	private String memberId[] = new String[20];

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

	public String[] getMemberId() {
		return memberId;
	}

	public void setMemberId(String[] memberId) {
		this.memberId = memberId;
	}
}
