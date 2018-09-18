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
		resp.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, Authorization, token");
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
		String token = "";
		token = request.getHeader("token");

		inputjson = request.getInputStream();

		incomingData = mapper.readValue(inputjson, TaskData.class);
		boolean result = false;

		try {
			result = scrumService.editTask(incomingData, token);
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
		String token = "";
		TaskData incomingdata = new TaskData();
		ObjectMapper mapper = new ObjectMapper();
		ServletInputStream inputjson = null;
		inputjson = request.getInputStream();
		incomingdata = mapper.readValue(inputjson, TaskData.class);
		System.out.println(incomingdata.getLastEdit());
		token = request.getHeader("token");
		boolean n = false;
		try {
			n = scrum.addTask(incomingdata, token);
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
		String token = "";
		inputjson = request.getInputStream();
		incomingdata = mapper.readValue(inputjson, TaskData.class);
		token = request.getHeader("token");
		boolean n = false;
		try {
			n = scrum.deleteTask(incomingdata, token);
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
		String token = "";
		token = request.getParameter("token");
		try {
			list = scrum.readService(viewTaskDate, viewTaskMemberEmail, viewTaskProjectId, token);
			receivedData = mapper.writeValueAsString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println(receivedData);
	}
}