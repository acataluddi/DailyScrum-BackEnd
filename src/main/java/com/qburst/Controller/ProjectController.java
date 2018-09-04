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
import com.qburst.Model.TaskData;
import com.qburst.Model.View;
import com.qburst.Service.Scrum;

@WebServlet("/ProjectController")
public class ProjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProjectController() {
		super();
	}

	// To add a project
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Scrum scrum = new Scrum();
		ProjectData incomingdata = new ProjectData();
		ObjectMapper mapper = new ObjectMapper();
		ServletInputStream inputjson = null;
		inputjson = request.getInputStream();
		incomingdata = mapper.readValue(inputjson, ProjectData.class);
		boolean n = false;
		try {
			n = scrum.addProject(incomingdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (n == true) {
			out.println("Project Added Successfully");
		} else {
			out.println("Failed to add Project");
		}
	}

	// To edit a project
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		Scrum scrum = new Scrum();
		ProjectData incomingdata = new ProjectData();
		ObjectMapper mapper = new ObjectMapper();
		ServletInputStream inputjson = null;
		inputjson = request.getInputStream();
		incomingdata = mapper.readValue(inputjson, ProjectData.class);
		boolean n = false;
		try {
			n = scrum.editProject(incomingdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (n == true) {
			out.println("Project Edited Successfully");
		} else {
			out.println("Failed to Edit Project");
		}
	}

	// To delete a project
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		Scrum scrum = new Scrum();
		ProjectData incomingdata = new ProjectData();
		ObjectMapper mapper = new ObjectMapper();
		ServletInputStream inputjson = null;
		inputjson = request.getInputStream();
		incomingdata = mapper.readValue(inputjson, ProjectData.class);
		boolean n = false;
		try {
			n = scrum.deleteProject(incomingdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (n == true) {
			out.println("Project Deleted Successfully");
		} else {
			out.println("Failed to delete Project");
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();

		ProjectData projectData = new ProjectData();
		Scrum scrumService = new Scrum();
//		System.out.println("Hello");
		String empty = null;
		List<ProjectData> newlist = new ArrayList<ProjectData>();
		System.out.println("Hello");
		try {
			newlist = scrumService.readProjectService();
			System.out.println(newlist);
			empty = mapper.writeValueAsString(newlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println(empty);
	}
}
