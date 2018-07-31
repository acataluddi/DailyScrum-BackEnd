package com.qburst.Service;

import java.util.List;

import com.qburst.Model.Data;
import com.qburst.Model.View;
import com.qburst.DAO.thisDao;

public class Service {

	thisDao dao = null;
	
	public thisDao getDao() {
		return dao;
	}
	public void setDao(thisDao dao) {
		this.dao = dao;
	}
	
	public Data insertingService(Data sdata) throws Exception {
		Data data = null;
		try {
			data = dao.insertDataBase(sdata);
		} catch (Exception e) {
			throw new Exception();
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
					dao.MemberProjectUpdate(sdata);
				break;
				
				case 2 : dao.MemberTaskUpdate(sdata);
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
					mv.setList1(dao.readProjectNames());
				mv.setList5(dao.readEmployeeData());
				break;
				
				case 2 : 
				mv.setList1(dao.readProjectNames());
				mv.setList2(dao.readYesterdayTask());
				mv.setList3(dao.readTodayTask());
				break;
				
				default : mv.setList4(dao.readProjectMemberData());
				mv.setList1(dao.readProjectNames());
				mv.setList2(dao.readYesterdayTask());
				mv.setList3(dao.readTodayTask());
			}
		} catch (Exception e) {
			throw new Exception();
		}
		return mv;
	}

	public boolean loggingService(Data sdata) throws Exception {

		boolean op = false;

		try {
			op = dao.login(sdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return op;
	}
}
