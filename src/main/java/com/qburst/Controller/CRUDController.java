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
import com.qburst.Model.View;
import com.qburst.Service.Service;

@WebServlet("/CRUDController")
public class CRUDController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CRUDController() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();		
		Data data = new Data();
		Service service = new Service();
		ObjectMapper mapper = new ObjectMapper();
		ServletInputStream inputjson = null;

		inputjson = request.getInputStream();

		data = mapper.readValue(inputjson, Data.class);
		
		boolean result = false;
		
		try {
			result = service.insertingService(data);
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
		Service service = new Service();
		Data data = new Data();
		ObjectMapper mapper = new ObjectMapper();
		
		ServletInputStream inputjson = null;

		inputjson = request.getInputStream();

		data = mapper.readValue(inputjson, Data.class);
		
		try {
			service.updatingService(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Service service = new Service();
		View mv = new View();
		Data data = new Data();
		ObjectMapper mapper = new ObjectMapper();
		
		ServletInputStream inputjson = null;

		//inputjson = request.getInputStream();

		//data = mapper.readValue(inputjson, Data.class);
		try {
			mv = service.readService(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String data1 = mapper.writeValueAsString(mv.getList1());
		String data2 = mapper.writeValueAsString(mv.getList2());
		String data3 = mapper.writeValueAsString(mv.getList3());
		String data4 = mapper.writeValueAsString(mv.getList4());
		String data5 = mapper.writeValueAsString(mv.getList5());
	}

}
