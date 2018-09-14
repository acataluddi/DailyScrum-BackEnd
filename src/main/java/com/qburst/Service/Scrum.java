package com.qburst.Service;

import java.util.ArrayList;
import java.util.List;

import com.qburst.Controller.IdTokenVerification;
import com.qburst.DAO.ProjectDao;
import com.qburst.DAO.ScrumDao;
import com.qburst.Model.ProjectData;
import com.qburst.Model.TaskData;
import com.qburst.Model.UsersData;

public class Scrum extends ScrumDao {
	private ProjectDao pdao = new ProjectDao();

	public UsersData insertUser(String token) throws Exception {
		UsersData incomingdata = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		incomingdata = id_verifier.processToken(token);
		UsersData user = new UsersData();
		System.out.println("Th euser id" + incomingdata.getEmployeeID());
		if (incomingdata.getEmployeeID() != null) {
			try {

				user = insertIntoTable(incomingdata);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return user;
	}

	public UsersData update(UsersData usersData, String token) throws Exception {

		UsersData UserUpdate = new UsersData();
		UsersData user = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {

				user = insertIntoTable(user);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (user.getUserType().equals("Admin") || user.getUserType().equals("Manager")) {
			try {
				UserUpdate = userTypeUpdate(usersData);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return UserUpdate;
	}

	// To add Project
	public ProjectData addProject(ProjectData incomingdata, String token) throws Exception {
		ProjectData result = new ProjectData();
		UsersData user = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {

				user = insertIntoTable(user);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (user.getUserType().equals("Admin") || user.getUserType().equals("Manager")) {
			try {
				result = this.pdao.insertProject(incomingdata);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return result;
	}

	// To delete Project
	public boolean deleteProject(String incomingdata, String token) throws Exception {
		boolean result = false;
		UsersData user = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {

				user = insertIntoTable(user);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (user.getUserType().equals("Admin") || user.getUserType().equals("Manager")) {
			try {

				// result = subtractProject(incomingdata);

				result = this.pdao.deleteProject(incomingdata);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return result;
	}

	// To edit Project
	public boolean editProject(ProjectData incomingdata, String token) throws Exception {
		boolean result = false;
		UsersData user = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {

				user = insertIntoTable(user);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (user.getUserType().equals("Admin") || user.getUserType().equals("Manager")) {
			try {
				result = this.pdao.updateProject(incomingdata);

			} catch (Exception e) {
				System.out.println(e);
			}
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
		if (user.getEmployeeID() != null) {
			try {

				user = insertIntoTable(user);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		System.out.println("In get projects");
		System.out.println("User type " + user.getUserType());
		if (user.getUserType().equals("Admin") || user.getUserType().equals("Manager")) {
			project_param = "getall";
			System.out.println("if Project param" + project_param);
		} else {
			if (user.getEmail() != "") {
				project_param = user.getEmail();
			} else {
				project_param = "";
			}
			System.out.println("Else Project param" + project_param);
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

	public List<UsersData> readUserService(int pagenum, int numOfRec, String token) {
		List<UsersData> usersList = new ArrayList<UsersData>();
		UsersData user = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {

				user = insertIntoTable(user);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (user.getUserType().equals("Admin") || user.getUserType().equals("Manager")
				|| user.getUserType().equals("User")) {
			try {
				usersList = readUserList(pagenum, numOfRec);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return usersList;
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

	public boolean addTask(TaskData incomingdata, String token) throws Exception {
		boolean result = false;
		UsersData user = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {

				user = insertIntoTable(user);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (user.getUserType().equals("Admin") || user.getUserType().equals("Manager")
				|| user.getUserType().equals("User")) {
			try {
				result = insertTask(incomingdata);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return result;
	}

	public boolean editTask(TaskData incomingdata, String token) throws Exception {
		boolean result = false;
		UsersData user = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {

				user = insertIntoTable(user);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (user.getUserType().equals("Admin") || user.getUserType().equals("Manager")
				|| user.getUserType().equals("User")) {

			try {
				result = updateTask(incomingdata);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return result;
	}

	public boolean deleteTask(TaskData incomingdata, String token) throws Exception {
		boolean result = false;
		UsersData user = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {

				user = insertIntoTable(user);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (user.getUserType().equals("Admin") || user.getUserType().equals("Manager")
				|| user.getUserType().equals("User")) {
			try {
				result = subtractTask(incomingdata);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return result;
	}

	public List<TaskData> readService(String viewTaskDate, String viewTaskMemberEmail, String viewTaskProjectId,
			String token) {
		List<TaskData> list = new ArrayList<TaskData>();
		UsersData user = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {

				user = insertIntoTable(user);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (user.getUserType().equals("Admin") || user.getUserType().equals("Manager")
				|| user.getUserType().equals("User")) {
			try {
				list = readTaskList(viewTaskDate, viewTaskMemberEmail, viewTaskProjectId);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return list;
	}

}
