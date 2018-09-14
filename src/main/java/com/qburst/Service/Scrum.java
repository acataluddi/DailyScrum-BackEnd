package com.qburst.Service;

import com.qburst.Model.ProjectData;
import com.qburst.Model.TaskData;
import com.qburst.Model.UsersData;
import com.qburst.Model.View;
import java.util.ArrayList;
import java.util.List;

import com.qburst.Controller.IdTokenVerification;
import com.qburst.DAO.ProjectDao;
import com.qburst.DAO.ScrumDao;

public class Scrum extends ScrumDao {
	private ProjectDao pdao = new ProjectDao();

	public UsersData insertUser(String token) throws Exception {
		UsersData incomingdata = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		incomingdata = id_verifier.processToken(token);
		UsersData user = new UsersData();
		try {

			user = insertIntoTable(incomingdata);
		} catch (Exception e) {
			System.out.println(e);
		}
		return user;
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

			// result = subtractProject(incomingdata);

			result = this.pdao.deleteProject(incomingdata);
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	// To edit Project
	public boolean editProject(ProjectData incomingdata) throws Exception {
		boolean result = false;
		try {
			result = this.pdao.updateProject(incomingdata);

		} catch (Exception e) {
			System.out.println(e);
		}

		return result;
	}

	// To read all projects
	public List<ProjectData> readProjectService(String token) {
		List<ProjectData> projectlist = new ArrayList<ProjectData>();
		UsersData user = new UsersData();
		String project_param = "";
		IdTokenVerification id_verifier = new IdTokenVerification();
		user = id_verifier.processToken(token);
		if(user!=null) {
			try {

				user = insertIntoTable(user);
			} catch (Exception e) {
				System.out.println(e);
			}			
		}
		System.out.println("In get projects");
		System.out.println("User type "+user.getUserType());
		if (user.getUserType().equals("Admin") || user.getUserType().equals("Manager")) {
			project_param = "getall";
			System.out.println("if Project param"+project_param);
		} else {
			if (user.getEmail() != "") {
				project_param = user.getEmail();
			} else {
				project_param = "";
			}
			System.out.println("Else Project param"+project_param);
		}
		try {
			projectlist = this.pdao.getProjects(project_param);
		} catch (Exception e) {
			System.out.println(e);
		}
		return projectlist;
	}
	// public List<ProjectData> readProjectService(String memberEmail) {
	// List<ProjectData> projectlist = new ArrayList<ProjectData>();
	// try {
	// projectlist = this.pdao.getProjects(memberEmail);
	// } catch (Exception e) {
	// System.out.println(e);
	// }
	// return projectlist;
	// }

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

	public List<TaskData> readService(String viewTaskDate, String viewTaskMemberEmail, String viewTaskProjectId) {
		List<TaskData> list = new ArrayList<TaskData>();
		try {
			list = readTaskList(viewTaskDate, viewTaskMemberEmail, viewTaskProjectId);
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}

}
