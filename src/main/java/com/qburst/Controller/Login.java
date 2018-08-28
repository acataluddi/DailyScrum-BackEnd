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

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Login() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Scrum scrum = new Scrum();
		UsersData incomingdata = new UsersData();
		ObjectMapper mapper = new ObjectMapper();
		
		ServletInputStream inputjson = null;

		inputjson = request.getInputStream();

		incomingdata = mapper.readValue(inputjson, UsersData.class);
		
		try {
			boolean n = scrum.loggingin(incomingdata);
			if(n) {
				IdTokenVerification tokenverify = new IdTokenVerification();
				String token = incomingdata.getToken();
				String message = tokenverify.processToken(token);
				out.println(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
