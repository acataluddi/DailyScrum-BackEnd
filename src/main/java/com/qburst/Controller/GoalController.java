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
import com.qburst.Model.Comment;
import com.qburst.Model.Goal;
import com.qburst.Model.GoalMember;
import com.qburst.Model.NavBarMember;
import com.qburst.Service.GoalService;

/**
 * Servlet implementation class GoalsController
 */
@WebServlet("/GoalController")
public class GoalController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoalController() {
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
		resp.setContentType("text/html; charset=UTF-8");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setAccessControlHeaders(response);
		PrintWriter out = response.getWriter();
		String token = "";
		String goalStatusString = "";
		String goalMemberString = "";
		String readParameter = "";
		String userEmail = "";
		ObjectMapper mapper = new ObjectMapper();
		GoalService goalService = new GoalService();
		GoalMember goalMember = new GoalMember();
		List<NavBarMember> membersStatusList = new ArrayList<NavBarMember>();
		token = request.getHeader("token");
		readParameter = request.getParameter("goalParam");
		userEmail = request.getParameter("userEmail");
		if (readParameter.equals("getStatusList")) {
			try {
				membersStatusList = goalService.readGoalStatus(token);
				goalStatusString = mapper.writeValueAsString(membersStatusList);
			} catch (Exception e) {
			}
			out.print(goalStatusString);
			out.close();
		}else if (readParameter.equals("getGoalMember")) {
			try {
				goalMember = goalService.getGoalMember(token, userEmail);
				goalMemberString = mapper.writeValueAsString(goalMember);
			} catch (Exception e) {
			}
			out.print(goalMemberString);
			out.close();			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setAccessControlHeaders(response);
		PrintWriter out = response.getWriter();
		String token = "";
		GoalService goalService = new GoalService();
		Goal goal = new Goal();
		Goal goalResponse = new Goal();
		ObjectMapper mapper = new ObjectMapper();
		ServletInputStream inputjson = null;

		inputjson = request.getInputStream();
		token = request.getHeader("token");
		goal = mapper.readValue(inputjson, Goal.class);

		try {
			goalResponse = goalService.addGoal(goal, token);
			String addedGoalDetails = mapper.writeValueAsString(goalResponse);
			out.println(addedGoalDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setAccessControlHeaders(response);
		PrintWriter out = response.getWriter();
		String token = "";
		GoalService goalService = new GoalService();
		Comment comment = new Comment();
		Comment responseComment = new Comment();
		ObjectMapper mapper = new ObjectMapper();
		ServletInputStream inputjson = null;

		inputjson = request.getInputStream();
		token = request.getHeader("token");
		comment = mapper.readValue(inputjson, Comment.class);

		try {
			responseComment = goalService.addComment(comment, token);
			String addedCommentDetails = mapper.writeValueAsString(responseComment);
			out.println(addedCommentDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
