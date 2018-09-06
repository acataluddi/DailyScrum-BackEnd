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

	/**
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
			out.println("Task Successfully Edited");
		} else {
			out.println("Failed To Edit");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		Scrum scrum = new Scrum();
		TaskData incomingdata = new TaskData();
		ObjectMapper mapper = new ObjectMapper();
		ServletInputStream inputjson = null;
		inputjson = request.getInputStream();
		incomingdata = mapper.readValue(inputjson, TaskData.class);
		boolean n = false;
		try {
			n = scrum.addTask(incomingdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (n == true) {
			out.println("Task Added Successfully");
		} else {
			out.println("Failed to add Task");
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
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
			out.println("Project Deleted Successfully");
		} else {
			out.println("Failed to delete Project");
		}
	}

	// TO LIST TASKS
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		Scrum scrum = new Scrum();
		String viewTaskDate = request.getParameter("taskDate");
		String viewTaskEmpId = request.getParameter("employeeId");
		out.println(viewTaskDate);
		ObjectMapper mapper = new ObjectMapper();
		List<TaskData> list = new ArrayList<TaskData>();
		String example = null;
		try {
			list = scrum.readService(viewTaskDate, viewTaskEmpId);
			example = mapper.writeValueAsString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println(example);
	}
}