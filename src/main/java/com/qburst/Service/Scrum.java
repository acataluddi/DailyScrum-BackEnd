package com.qburst.Service;

import com.qburst.Model.ProjectData;

import com.qburst.Model.TaskData;

import com.qburst.Model.UsersData;
import com.qburst.Model.View;
import com.qburst.DAO.ScrumDao;

public class Scrum extends ScrumDao {
	public boolean insertUser(UsersData incomingdata) throws Exception {
		boolean result = false;
		try {
			result = insertIntoTable(incomingdata);
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	// To add Project
	public boolean addProject(ProjectData incomingdata) throws Exception {
		boolean result = false;
		try {
			result = insertProject(incomingdata);
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	// To delete Project
	public boolean deleteProject(ProjectData incomingdata) throws Exception {
		boolean result = false;
		try {
			result = subtractProject(incomingdata);
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	// To edit Project
	public boolean editProject(ProjectData incomingdata) throws Exception {
		boolean result = false;
		try {
			result = updateProject(incomingdata);
		} catch (Exception e) {
			System.out.println(e);
		}

		return result;
	}

	public void update(UsersData incomingdata) throws Exception {
		// Function to update user
	}

	public View read(View viewInfo) throws Exception {
		View mv = new View();
		try {
			int page_id = viewInfo.getPageid();
			switch (page_id) {
			case 1:
				mv.setProjectNames(readProjectNames());
				mv.setEmployeeData(readEmployeeData());
				break;

			case 2:
				mv.setProjectNames(readProjectNames());
				mv.setYesterdayTask(readYesterdayTask());
				mv.setTodayTask(readTodayTask());
				break;
			case 3:
				// View UserList
				int pagenum = viewInfo.getPagenum();
				int num_of_rec = viewInfo.getNum_of_rec();
				mv.setEmployeeData(readUserList(pagenum, num_of_rec));
				break;
			default:
				mv.setProjectMemberData(readProjectMemberData());
				mv.setProjectNames(readProjectNames());
				mv.setYesterdayTask(readYesterdayTask());
				mv.setTodayTask(readTodayTask());
			}
		} catch (Exception e) {
			throw new Exception();
		}
		return mv;
	}

	public boolean loggingin(UsersData incomingdata) throws Exception {
		boolean op = false;
		try {
			op = login(incomingdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return op;
	}

	public boolean addTask(TaskData incomingdata) throws Exception {
		boolean result = false;

		try {
			result = insertTask(incomingdata);
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
}
