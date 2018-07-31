package com.qburst.Service;

import com.qburst.Model.Data;
import com.qburst.Model.View;

import java.sql.SQLException;
import java.util.List;

import com.qburst.DAO.thisDao;

public class Service extends thisDao{

	
	
	public Data insertingService(Data sdata)throws Exception{
		Data data = new Data();
		//System.out.println("hii ");
		try {
			data = insertDataBase(sdata);
		}catch(Exception e){
			System.out.println(e);
		}
		return data;
	}
	
	public void updatingService(Data sdata) throws Exception {
		Data data = null;
		try {
			int page_id = data.getPageID();
			switch(page_id)
			{
				case 1 : 
					MemberProjectUpdate(sdata);
				break;
				
				case 2 : MemberTaskUpdate(sdata);
				break;
				
			}
		} catch (Exception e) {
			throw new Exception();
		}
	}
	public View readService(Data data) throws Exception {

		View mv = new View();
		try {
			int page_id = data.getPageID();
			switch(page_id)
			{
				case 1 : 
					mv.setList1(readProjectNames());
				mv.setList5(readEmployeeData());
				break;
				
				case 2 : 
				mv.setList1(readProjectNames());
				mv.setList2(readYesterdayTask());
				mv.setList3(readTodayTask());
				break;
				
				default : mv.setList4(readProjectMemberData());
				mv.setList1(readProjectNames());
				mv.setList2(readYesterdayTask());
				mv.setList3(readTodayTask());
			}
		} catch (Exception e) {
			throw new Exception();
		}
		return mv;
	}

	public boolean loggingService(Data sdata) throws Exception {

		boolean op = false;

		try {
			op = login(sdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return op;
	}
}
