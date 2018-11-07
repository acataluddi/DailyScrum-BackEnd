package com.qburst.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qburst.Model.ProjectData;
import com.qburst.Model.ProjectMember;
import com.qburst.Model.TaskData;
import com.qburst.Service.Scrum;

@WebServlet("/ProjectController")
public class ProjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProjectController() {
		super();
	}

	// for Preflight
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		setAccessControlHeaders(resp);
		resp.setStatus(HttpServletResponse.SC_OK);
	}

	private void setAccessControlHeaders(HttpServletResponse resp) {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "PUT,GET,POST,DELETE");
		resp.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, Authorization, token");
		resp.setContentType("text/html; charset=UTF-8");
	}

	// To add a project
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setAccessControlHeaders(response);
		PrintWriter out = response.getWriter();
		String token = "";
		Scrum scrum = new Scrum();
		ProjectData incomingdata = new ProjectData();
		ProjectMember[] members = null;
		ObjectMapper mapper = new ObjectMapper();
		ServletInputStream inputjson = null;

		inputjson = request.getInputStream();
		token = request.getHeader("token");
		incomingdata = mapper.readValue(inputjson, ProjectData.class);

		ProjectData pdata = new ProjectData();
		try {
			pdata = scrum.addProject(incomingdata, token);
			String receivedProjectDetails = mapper.writeValueAsString(pdata);
			out.println(receivedProjectDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// To edit a project
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.addHeader("Access-Control-Allow-Origin", "*");

		setAccessControlHeaders(response);
		PrintWriter out = response.getWriter();
		Scrum scrum = new Scrum();
		String token = "";
		ProjectData incomingdata = new ProjectData();
		ObjectMapper mapper = new ObjectMapper();
		ServletInputStream inputjson = null;
		token = request.getHeader("token");
		inputjson = request.getInputStream();
		incomingdata = mapper.readValue(inputjson, ProjectData.class);
		boolean n = false;
		try {
			n = scrum.editProject(incomingdata, token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (n == true) {
			out.println("{\"message\":\"Project Edited Successfully\"}");
		} else {
			out.println("{\"message\":\"Failed to Edit Project\"}");
		}
	}

	// To delete a project
	@SuppressWarnings("unused")
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setAccessControlHeaders(response);
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String projectId = request.getParameter("projectId");
		System.out.println("Hello");
		System.out.println(projectId);
		String token = "";
		Scrum scrum = new Scrum();
		ProjectData incomingdata = new ProjectData();
		ObjectMapper mapper = new ObjectMapper();
		ServletInputStream inputjson = null;
		token = request.getHeader("token");
		boolean n = false;
		try {
			System.out.println("Iam going");
			n = scrum.deleteProject(projectId, token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (n == true) {
			out.println("Project Deleted Successfully");
		} else {
			out.println("Failed to delete Project");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		setAccessControlHeaders(response);
		PrintWriter out = response.getWriter();
		String token = "";

		token = request.getHeader("token");
		Scrum scrum = new Scrum();
		ObjectMapper mapper = new ObjectMapper();
		List<ProjectData> projectlist = new ArrayList<ProjectData>();
		String projects = null;
		try {

			projectlist = scrum.readProjectService(token);

			projects = mapper.writeValueAsString(projectlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Projects \n" + projects);
		out.println(projects);
	}
}
