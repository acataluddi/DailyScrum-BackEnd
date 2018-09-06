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
import com.qburst.Service.Scrum;


@WebServlet("/AuthenticationController")
public class AuthenticationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public AuthenticationController() {
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
		System.out.println(incomingdata.getImageurl());
		UsersData user = new UsersData();
		try {
			user = scrum.insertUser(incomingdata);
			String receivedUserDetails = mapper.writeValueAsString(user);
			System.out.println(receivedUserDetails);
			out.println(receivedUserDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
