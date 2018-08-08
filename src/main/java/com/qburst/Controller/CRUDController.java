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
import com.qburst.Model.GoogleUser;
import com.qburst.Model.UsersData;
import com.qburst.Model.View;
import com.qburst.Service.Scrum;
import com.qburst.Validator.TokenValidator;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

@WebServlet("/CRUDController")
public class CRUDController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CRUDController() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UsersData user = new UsersData();
		GoogleUser gmember = new GoogleUser();
		ObjectMapper mapper = new ObjectMapper();
		TokenValidator tvalidator = new TokenValidator();
		Scrum scrum = new Scrum();
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		ServletInputStream inputjson = null;
		
		inputjson = request.getInputStream();
		gmember = mapper.readValue(inputjson, GoogleUser.class);
		user = tvalidator.verifyToken(gmember.getIdToken());

		if (user == null) {
			out.println("{\"message\":\"Invalid token\"}");
		} else {
			boolean result = false;
			// just setting userType as user for the first time
			user.setUserType("user");
			try {
				result = scrum.insertUser(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (result == true) {
				out.println("{\"message\":\"registered\"}");
			} else {
				out.println("{\"message\":\"User exists\"}");
			}
		}
		// UsersData incomingdata = new UsersData();
		// ObjectMapper mapper = new ObjectMapper();
		// ServletInputStream inputjson = null;
		//
		// inputjson = request.getInputStream();
		//
		// incomingdata = mapper.readValue(inputjson, UsersData.class);
		//
		// boolean result = false;
		//
		// try {
		// result = scrum.insertUser(incomingdata);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// if (result == true) {
		//// out.println("Registered");
		// out.println("{\"message\":\"registered\"}");
		// } else {
		//// out.println("Could not register");
		// out.println("{\"message\":\"User exists\"}");
		// }
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
		UsersData user = new UsersData();
		TokenValidator tvalidator = new TokenValidator();
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String idToken = request.getParameter("id_token");
		user = tvalidator.verifyToken(idToken);
		if (user == null) {
			out.println("{\"message\":\"Invalid token\"}");
		} else {
			out.println("{\"message\":\"" + user.getEmail() + "\"}");
		}

		// PrintWriter out = response.getWriter();
		// ObjectMapper mapper = new ObjectMapper();
		//
		// View myView = new View();
		// Scrum scrumService = new Scrum();
		//
		// String spagenum = request.getParameter("page");
		// int pagenum = Integer.parseInt(spagenum);
		//
		// // pageid received from client
		// int pageid = 3;
		// myView.setPageid(pageid);
		//
		// // number of records to be displayed in a page: from client
		// int numOfRec = 3;
		//
		// myView.setPagenum(pagenum);
		// myView.setNumOfRec(numOfRec);
		//
		// try {
		//
		// myView = scrumService.read(myView);
		//
		// } catch (Exception e) {
		//
		// }
		//
		// List<UsersData> userlist = myView.getEmployeeData(pagenum, numOfRec);
		//
		// String outputRecords = mapper.writeValueAsString(userlist);
		// out.println(outputRecords);
		//
		// out.close();
		//
		// /*
		// * String data1 = mapper.writeValueAsString(mv.getProjectNames());
		// * String data2 = mapper.writeValueAsString(mv.getYesterdayTask());
		// * String data3 = mapper.writeValueAsString(mv.getTodayTask());
		// * String data4 = mapper.writeValueAsString(mv.getProjectMemberData());
		// * String data5 = mapper.writeValueAsString(mv.getEmployeeData());
		// */
	}
}
