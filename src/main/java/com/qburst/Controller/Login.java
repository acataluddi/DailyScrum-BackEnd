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
import com.qburst.Model.Data;
import com.qburst.Service.Service;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Login() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Service service = new Service();
		Data data = new Data();
		ObjectMapper mapper = new ObjectMapper();
		
		ServletInputStream inputjson = null;

		inputjson = request.getInputStream();

		data = mapper.readValue(inputjson, Data.class);
		
		try {
			boolean n = service.loggingService(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
