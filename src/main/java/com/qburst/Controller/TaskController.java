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
import com.qburst.Model.TaskData;
import com.qburst.Service.Scrum;

/**
 * Servlet implementation class TaskController
 */
@WebServlet("/TaskController")
public class TaskController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TaskController() {
		super();
		// TODO Auto-generated constructor stub
	}

	private void setAccessControlHeaders(HttpServletResponse resp) {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "PUT,GET,POST,DELETE");
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		setAccessControlHeaders(resp);
		resp.setStatus(HttpServletResponse.SC_OK);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setAccessControlHeaders(response);
		PrintWriter out = response.getWriter();
		TaskData incomingData = new TaskData();

		Scrum scrumService = new Scrum();
		ObjectMapper mapper = new ObjectMapper();
		ServletInputStream inputjson = null;

		inputjson = request.getInputStream();

		incomingData = mapper.readValue(inputjson, TaskData.class);
		boolean result = false;

		try {
			result = scrumService.editTask(incomingData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == true) {
			out.println("{\"message\":\"Task Successfully Edited\"}");
		} else {
			out.println("{\"message\":\"Failed To Edit\"}");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setAccessControlHeaders(response);
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		Scrum scrum = new Scrum();
		TaskData incomingdata = new TaskData();
		ObjectMapper mapper = new ObjectMapper();
		ServletInputStream inputjson = null;
		inputjson = request.getInputStream();
		incomingdata = mapper.readValue(inputjson, TaskData.class);
		System.out.println(incomingdata.getLastEdit());
		boolean n = false;
		try {
			n = scrum.addTask(incomingdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (n == true) {
			out.println("{\"message\":\"Task Added Successfully\"}");
		} else {
			out.println("{\"message\":\"Failed to add Task\"}");
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		setAccessControlHeaders(response);
		PrintWriter out = response.getWriter();
		Scrum scrum = new Scrum();
		TaskData incomingdata = new TaskData();
		ObjectMapper mapper = new ObjectMapper();
		ServletInputStream inputjson = null;
		inputjson = request.getInputStream();
		incomingdata = mapper.readValue(inputjson, TaskData.class);
		boolean n = false;
		try {
			n = scrum.deleteTask(incomingdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (n == true) {
			out.println("{\"message\":\"Task Deleted Successfully\"}");
		} else {
			out.println("{\"message\":\"Failed to delete Task\"}");
		}
	}

	// TO LIST TASKS
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setAccessControlHeaders(response);
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		Scrum scrum = new Scrum();
		String viewTaskDate = request.getParameter("taskDate");
		String viewTaskMemberEmail = request.getParameter("memberEmail");
		String viewTaskProjectId = request.getParameter("projectId");
		ObjectMapper mapper = new ObjectMapper();
		List<TaskData> list = new ArrayList<TaskData>();
		String receivedData = null;
		try {
			list = scrum.readService(viewTaskDate, viewTaskMemberEmail, viewTaskProjectId);
			receivedData = mapper.writeValueAsString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println(receivedData);
	}
}