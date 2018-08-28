package com.qburst.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qburst.Model.UsersData;
import com.qburst.Model.View;
import com.qburst.Service.Scrum;

@WebServlet("/CRUDControllerUser")
public class CRUDControllerUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CRUDControllerUser() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		UsersData incomingdata = new UsersData();
		Scrum scrum = new Scrum();
		ObjectMapper mapper = new ObjectMapper();
		ServletInputStream inputjson = null;

		inputjson = request.getInputStream();

		incomingdata = mapper.readValue(inputjson, UsersData.class);

		boolean result = false;

		try {
			result = scrum.insertUser(incomingdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == true) {
			out.println("{\"message\":\"User Registered\"}");
		} else {
			out.println("{\"message\":\"User Already Exist\"}");
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		Scrum scrum = new Scrum();
		UsersData incomingdata = new UsersData();
		ObjectMapper mapper = new ObjectMapper();

		ServletInputStream inputjson = null;

		inputjson = request.getInputStream();

		incomingdata = mapper.readValue(inputjson, UsersData.class);

		try {
			scrum.update(incomingdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * Retrieve Users List
		 */
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();

		View myView = new View();
		Scrum scrumService = new Scrum();

		String spagenum = request.getParameter("page");
		int pagenum = Integer.parseInt(spagenum);

		// pageid received from client
		int pageid = 3;
		myView.setPageid(pageid);

		// number of records to be displayed in a page: from client
		int numOfRec = 3;

		myView.setPagenum(pagenum);
		myView.setNumOfRec(numOfRec);

		try {

			myView = scrumService.read(myView);

		} catch (Exception e) {

		}

		List<UsersData> userlist = myView.getEmployeeData(pagenum, numOfRec);

		String outputRecords = mapper.writeValueAsString(userlist);
		out.println(outputRecords);

		out.close();
	}
}
