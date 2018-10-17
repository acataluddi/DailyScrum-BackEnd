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
import com.qburst.Model.Feedback;
import com.qburst.Model.FeedbackMember;
import com.qburst.Model.NavBarMember;
import com.qburst.Service.FeedbackService;

/**
 * Servlet implementation class FeedbackController
 */
@WebServlet("/FeedbackController")
public class FeedbackController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FeedbackController() {
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setAccessControlHeaders(response);
		PrintWriter out = response.getWriter();
		String token = "";
		String readParameter = "";
		String feedbackStatusString = "";
		String feedbackMemberString = "";
		FeedbackService fbService = new FeedbackService();
		FeedbackMember feedbackmember = new FeedbackMember();
		List<NavBarMember> membersStatusList = new ArrayList<NavBarMember>();
		ObjectMapper mapper = new ObjectMapper();
		token = request.getHeader("token");
		readParameter = request.getParameter("feedbackParam");
		if (readParameter.equals("getStatusList")) {
			try {
				membersStatusList = fbService.getFeedbackStatus(token);
				feedbackStatusString = mapper.writeValueAsString(membersStatusList);
			} catch (Exception e) {
			}
			out.print(feedbackStatusString);
			out.close();
		} else {
			try {
				feedbackmember = fbService.getFeedbackMember(token, readParameter);
				feedbackMemberString = mapper.writeValueAsString(feedbackmember);
			} catch (Exception e) {
			}
			out.print(feedbackMemberString);
			out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setAccessControlHeaders(response);
		PrintWriter out = response.getWriter();
		String token = "";
		FeedbackService fbService = new FeedbackService();
		Feedback feedback = new Feedback();
		ObjectMapper mapper = new ObjectMapper();
		ServletInputStream inputjson = null;

		inputjson = request.getInputStream();
		token = request.getHeader("token");
		feedback = mapper.readValue(inputjson, Feedback.class);

		Feedback fb = new Feedback();
		try {
			fb = fbService.addFeedback(feedback, token);
			String addedFeedbackDetails = mapper.writeValueAsString(fb);
			out.println(addedFeedbackDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setAccessControlHeaders(response);
		PrintWriter out = response.getWriter();
		String token = "";
		String updateEmail = "";
		FeedbackService fbService = new FeedbackService();
		token = request.getHeader("token");
		updateEmail = request.getParameter("updateEmail");
		try {
			if (fbService.updateFeedbackStatus(token, updateEmail)) {
				out.println("{\"FeedbackStatusUpdate\":true}");
			} else {
				out.println("{\"FeedbackStatusUpdate\":false}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
