package com.qburst.Controller;

import java.io.IOException;
import java.io.PrintWriter;

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

@WebServlet("/CRUDController")
public class CRUDController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CRUDController() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		if(result == true) {
			out.println("Registered");
		}
		else {
			out.println("Could not register");
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
		PrintWriter out = response.getWriter();
		Scrum scrum = new Scrum();
		View mv = new View();
		UsersData incomingdata = new UsersData();
		ObjectMapper mapper = new ObjectMapper();
		
		ServletInputStream inputjson = null;

		//inputjson = request.getInputStream();

		//data = mapper.readValue(inputjson, UsersData.class);
		try {
			mv = scrum.read(incomingdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String data1 = mapper.writeValueAsString(mv.getProjectNames());
		String data2 = mapper.writeValueAsString(mv.getYesterdayTask());
		String data3 = mapper.writeValueAsString(mv.getTodayTask());
		String data4 = mapper.writeValueAsString(mv.getProjectMemberData());
		String data5 = mapper.writeValueAsString(mv.getEmployeeData());
	}

}
