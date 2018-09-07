package com.qburst.Service;

import com.qburst.Model.ProjectData;
import com.qburst.Model.TaskData;
import com.qburst.Model.UsersData;
import com.qburst.Model.View;
import java.util.ArrayList;
import java.util.List;

import com.qburst.DAO.ProjectDao;
import com.qburst.DAO.ScrumDao;

public class Scrum extends ScrumDao {
	private ProjectDao pdao = new ProjectDao();
	public boolean insertUser(UsersData incomingdata) throws Exception {
		boolean result = false;
		try {
			result = insertIntoTable(incomingdata);
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	public UsersData update(UsersData usersData) throws Exception {

		UsersData UserUpdate = new UsersData();

		try {
			UserUpdate = userTypeUpdate(usersData);
		} catch (Exception e) {
			System.out.println(e);
		}
		return UserUpdate;
	}

	// To add Project
	public ProjectData addProject(ProjectData incomingdata) throws Exception {
		ProjectData result = null;
		try {
//			result = insertProject(incomingdata);
			result = this.pdao.insertProject(incomingdata);
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	// To delete Project
	public boolean deleteProject(String incomingdata) throws Exception {
		boolean result = false;
		try {
			System.out.println("Scrum");
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
	
	//To read all projects
	public List<ProjectData> readProjectService() {
		List<ProjectData> projectlist = new ArrayList<ProjectData>();
		try {
			projectlist = this.pdao.getProjects();
		} catch (Exception e) {
			System.out.println(e);
		}
		return projectlist;
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

	public boolean editTask(TaskData incomingdata) throws Exception {
		boolean result = false;

		try {
			result = updateTask(incomingdata);
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	public boolean deleteTask(TaskData incomingdata) throws Exception {
		boolean result = false;
		try {
			result = subtractTask(incomingdata);
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	public List<TaskData> readService(String viewTaskDate, String viewTaskEmpId) {
		List<TaskData> list = new ArrayList<TaskData>();
		try {
			list = readTaskList(viewTaskDate, viewTaskEmpId);
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}
	
	public float getTotalCount() {
		float NoOfRecords = 0;
		try {
			
		NoOfRecords = getCount();
		System.out.println("jh");
		
	}catch (Exception e){
		System.out.println(e);
	}
		return NoOfRecords;
	}
}
