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
import com.qburst.Model.UsersData;
import com.qburst.Service.Scrum;

@WebServlet("/CRUDControllerUser")

public class CRUDControllerUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CRUDControllerUser() {
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
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String token = "";
		Scrum scrum = new Scrum();
		ObjectMapper mapper = new ObjectMapper();
		token = request.getHeader("token");
		System.out.println("token is " + token);
		UsersData user = new UsersData();
		try {
			user = scrum.insertUser(token);
			String receivedUserDetails = mapper.writeValueAsString(user);
			System.out.println(receivedUserDetails);
			out.println(receivedUserDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		setAccessControlHeaders(response);
		PrintWriter out = response.getWriter();
		String token = "";
		Scrum scrum = new Scrum();
		System.out.println("inside put");
		UsersData result = new UsersData();

		token = request.getHeader("token");
		UsersData incomingdata = new UsersData();
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("inside put");
		ServletInputStream inputjson = null;
		inputjson = request.getInputStream();
		incomingdata = mapper.readValue(inputjson, UsersData.class);
		System.out.println(incomingdata);
		System.out.println(incomingdata.getEmail());
		out.println(incomingdata.getName());
		try {
			result = scrum.update(incomingdata, token);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String update = mapper.writeValueAsString(incomingdata);

		out.println(update);

		out.close();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * Retrieve Users List
		 */
		response.addHeader("Access-Control-Allow-Origin", "*");
		setAccessControlHeaders(response);
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		String token = "";
		token = request.getHeader("token");

		Scrum scrumService = new Scrum();

		List<UsersData> userlist = new ArrayList<UsersData>();
		String outputRecords = null;

		String spagenum = request.getParameter("page");
		int pagenum = Integer.parseInt(spagenum);

		int numOfRec = 0;

		if (pagenum != 0) {
			numOfRec = 10;
		}
		try {
			userlist = scrumService.readUserService(pagenum, numOfRec, token);
			outputRecords = mapper.writeValueAsString(userlist);
		} catch (Exception e) {

		}

		System.out.println(outputRecords);
		out.print(outputRecords);

		out.close();
	}
}
