package com.qburst.Model;

public class ProjectData {

	private String projectName, projectDesc;

	private int projectId;
	private int memberId[] = new int[20];

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
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

	public int[] getMemberId() {
		return memberId;
	}

	public void setMemberId(int[] memberId) {
		this.memberId = memberId;
	}
}
