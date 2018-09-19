package com.qburst.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qburst.Model.TaskMember;
import com.qburst.Service.Scrum;

/**
 * Servlet implementation class TaskPageController
 */
@WebServlet("/TaskPageController")
public class TaskPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TaskPageController() {
		super();
		// TODO Auto-generated constructor stub
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
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		setAccessControlHeaders(response);
		PrintWriter out = response.getWriter();
		Scrum scrum = new Scrum();
		String token = "";
		String viewTaskDate = "";
		String viewTaskProjectId = "";
		String membersData = "";
		List<TaskMember> taskList = new ArrayList<TaskMember>();
		ObjectMapper mapper = new ObjectMapper();
		viewTaskDate = request.getParameter("taskDate");
		viewTaskProjectId = request.getParameter("projectId");
		token = request.getParameter("token");
		try {
			taskList = scrum.taskPageService(viewTaskDate, viewTaskProjectId, token);
			membersData = mapper.writeValueAsString(taskList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println(membersData);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
