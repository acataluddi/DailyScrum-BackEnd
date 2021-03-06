package com.qburst.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.qburst.Controller.IdTokenVerification;
import com.qburst.DAO.ProjectDao;
import com.qburst.DAO.ScrumDao;
import com.qburst.Model.ProjectData;
import com.qburst.Model.ProjectMember;
import com.qburst.Model.TaskData;
import com.qburst.Model.TaskMember;
import com.qburst.Model.UsersData;

public class Scrum extends ScrumDao {
	private ProjectDao pdao = new ProjectDao();

	public UsersData insertUser(String token) throws Exception {
		UsersData incomingdata = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		incomingdata = id_verifier.processToken(token);
		UsersData user = new UsersData();
		List<ProjectData> projectlist = new ArrayList<ProjectData>();
		if (incomingdata.getEmployeeID() != null) {
			try {

				user = insertIntoTable(incomingdata);
				projectlist = this.pdao.getProjects(user.getEmail());
				Iterator<ProjectData> itr = projectlist.iterator();
				ProjectMember[] current_members;
				while (itr.hasNext()) {
					ProjectData project = (ProjectData) itr.next();
					current_members = project.getMembers();
					for (int i = 0; i < current_members.length; i++) {
						if (current_members[i].getemail().equals(user.getEmail())) {
							current_members[i].setname(user.getName());
							current_members[i].setimage(user.getImageurl());
							if (current_members[i].getrole().equals("Project Manager")
									&& user.getUserType().equals("User")) {
								user.setUserType("Manager");
								user = userTypeUpdate(user);
							}
							break;
						}
					}
					project.setMembers(current_members);
					this.pdao.updateProject(project);
				}
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
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = formatter.format(date);
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
				ProjectMember[] membersWithoutManager = incomingdata.getMembers();
				ArrayList<ProjectMember> membersList = new ArrayList<ProjectMember>(
						Arrays.asList(membersWithoutManager));
				ProjectMember manager = new ProjectMember();
				manager.setemail(user.getEmail());
				manager.setrole("Project Manager");
				membersList.add(manager);
				ProjectMember[] members = new ProjectMember[membersWithoutManager.length + 1];
				members = membersList.toArray(members);
				SendEmailService mailService = new SendEmailService();
				for (int i = 0; i < members.length; i++) {
					UsersData current_user = new UsersData();
					current_user = getIndividualUser(members[i].getemail());
					if (current_user.getName() == null) {
						members[i].setname("");
						members[i].setimage("https://image.flaticon.com/icons/svg/146/146007.svg");
					} else if (current_user.getName() != "") {
						members[i].setname(current_user.getName());
						members[i].setimage(current_user.getImageurl());
						if (members[i].getrole().equals("Project Manager")
								&& current_user.getUserType().equals("User")) {
							current_user.setUserType("Manager");
							current_user = userTypeUpdate(current_user);
						}
					}
					members[i].setAddedDate(strDate);
					members[i].setDeletedDate("");
					members[i].setIsActive(true);

					MailThread mailthread = new MailThread(members[i], incomingdata.getProjectName(), user);
					mailthread.start();
				}
				incomingdata.setMembers(members);
				incomingdata.setStartDate(strDate);
				incomingdata.setEndDate("");
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
		SendEmailService mailService = new SendEmailService();
		IdTokenVerification id_verifier = new IdTokenVerification();
		user = id_verifier.processToken(token);
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = formatter.format(date);
		if (user.getEmployeeID() != null) {
			try {
				user = insertIntoTable(user);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		String pid = incomingdata.getProjectId();
		ProjectData current_project = this.pdao.getIndividualProject(pid);
		ProjectMember[] current_members = current_project.getMembers();
		ProjectMember[] members = incomingdata.getMembers();
		ArrayList<ProjectMember> membersList = new ArrayList<ProjectMember>(Arrays.asList(members));
		if (user.getUserType().equals("Admin") || user.getUserType().equals("Manager")) {
			try {
				for (ProjectMember oldMember : current_members) {
					boolean memberPresent = false;
					boolean memberIsActive = false;
					for (ProjectMember newMember : members) {
						if (oldMember.getemail().equals(newMember.getemail())) {
							memberPresent = true;
							break;
						}
					}
					if (!memberPresent) {
						oldMember.setIsActive(false);
						oldMember.setDeletedDate(strDate);
						membersList.add(oldMember);
					}
				}
				for (ProjectMember newMember : members) {
					boolean memberPresent = false;
					for (ProjectMember oldMember : current_members) {
						if (newMember.getemail().equals(oldMember.getemail())) {
							if (oldMember.getIsActive() == true) {
								newMember.setDeletedDate("");
								newMember.setIsActive(true);
								newMember.setAddedDate(oldMember.getAddedDate());
								newMember.setimage(oldMember.getimage());
								newMember.setname(oldMember.getname());
								Iterator<ProjectMember> itr = membersList.iterator();
								while (itr.hasNext()) {
									ProjectMember m = (ProjectMember) itr.next();
									if (m.getemail().equals(newMember.getemail())) {
										itr.remove();
									}
								}
								membersList.add(newMember);
								memberPresent = true;
								break;
							} else if (oldMember.getIsActive() == false) {
								newMember.setDeletedDate("");
								newMember.setIsActive(true);
								newMember.setAddedDate(oldMember.getAddedDate());
								newMember.setimage(oldMember.getimage());
								newMember.setname(oldMember.getname());
								Iterator<ProjectMember> itr = membersList.iterator();
								while (itr.hasNext()) {
									ProjectMember m = (ProjectMember) itr.next();
									if (m.getemail().equals(newMember.getemail())) {
										itr.remove();
									}
								}
								membersList.add(newMember);
								memberPresent = true;
								break;
							}
						}
					}
					if (!memberPresent) {
						newMember.setAddedDate(strDate);
						newMember.setDeletedDate("");
						newMember.setIsActive(true);
						Iterator<ProjectMember> itr = membersList.iterator();
						while (itr.hasNext()) {
							ProjectMember m = (ProjectMember) itr.next();
							if (m.getemail().equals(newMember.getemail())) {
								itr.remove();
							}
						}
						UsersData current_user = new UsersData();
						current_user = getIndividualUser(newMember.getemail());
						if (current_user.getName() == null) {
							newMember.setname("");
							newMember.setimage("https://image.flaticon.com/icons/svg/146/146007.svg");
						} else {
							newMember.setname(current_user.getName());
							newMember.setimage(current_user.getImageurl());
						}
						membersList.add(newMember);
						MailThread mailthread = new MailThread(newMember, incomingdata.getProjectName(), user);
						mailthread.start();
					}
				}
				members = membersList.toArray(members);
				// To change the role of Users (who are Project Managers) to Manager
				for (ProjectMember memb : members) {
					if (memb.getrole().equals("Project Manager") && memb.getname() != "") {
						UsersData current_user = new UsersData();
						current_user = getIndividualUser(memb.getemail());
						if (current_user.getUserType().equals("User")) {
							current_user.setUserType("Manager");
							current_user = userTypeUpdate(current_user);
						}
					}
				}

				incomingdata.setMembers(members);
				incomingdata.setStartDate(current_project.getStartDate());
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
		if (user.getUserType().equals("Admin")) {
			project_param = "getall";
		} else {
			if (user.getEmail() != "" && (user.getUserType().equals("Manager") || user.getUserType().equals("User"))) {
				project_param = user.getEmail();
			} else {
				project_param = "";
			}
		}
		try {
			projectlist = this.pdao.getProjects(project_param);
			if (user.getEmail() != "" && (user.getUserType().equals("Manager") || user.getUserType().equals("User"))) {
				Iterator<ProjectData> itr = projectlist.iterator();
				ProjectMember[] current_members;
				while (itr.hasNext()) {
					ProjectData project = (ProjectData) itr.next();
					current_members = project.getMembers();
					ArrayList<ProjectMember> membersList = new ArrayList<ProjectMember>(
							Arrays.asList(current_members));
					Iterator<ProjectMember> itr1 = membersList.iterator();
					while (itr1.hasNext()) {
						ProjectMember mem = (ProjectMember) itr1.next();
						if (mem.getIsActive() == false) {
							itr1.remove();
						}
					}
					ProjectMember[] updated_members = new ProjectMember[membersList.size()];
					updated_members = membersList.toArray(updated_members);
					int index = projectlist.indexOf(project);
					boolean isProjectDeleted = false;
					project.setMembers(updated_members);
					for (int i = 0; i < current_members.length; i++) {
						if (current_members[i].getemail().equals(user.getEmail())) {
							if (current_members[i].getIsActive() == false) {
								itr.remove();
								isProjectDeleted = true;
								break;
							}
						}
					}
					if (!isProjectDeleted) {
						projectlist.set(index, project);
					}
				}
			} else if (user.getEmail() != "" && (user.getUserType().equals("Admin"))) {
				Iterator<ProjectData> itr = projectlist.iterator();
				ProjectMember[] current_members;
				while (itr.hasNext()) {
					ProjectData project = (ProjectData) itr.next();
					current_members = project.getMembers();
					ArrayList<ProjectMember> membersList = new ArrayList<ProjectMember>(
							Arrays.asList(current_members));
					Iterator<ProjectMember> itr1 = membersList.iterator();
					while (itr1.hasNext()) {
						ProjectMember mem = (ProjectMember) itr1.next();
						if (mem.getIsActive() == false) {
							itr1.remove();
						}
					}
					ProjectMember[] updated_members = new ProjectMember[membersList.size()];
					updated_members = membersList.toArray(updated_members);
					int index = projectlist.indexOf(project);
					project.setMembers(updated_members);
					projectlist.set(index, project);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return projectlist;
	}

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

	public List<TaskMember> taskPageService(String viewTaskDate, String viewTaskProjectId, String token) {
		UsersData user = new UsersData();
		ProjectData pdata = new ProjectData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		List<TaskMember> taskList = new ArrayList<TaskMember>();
		List<TaskMember> taskListNonEmpty = new ArrayList<TaskMember>();
		List<TaskMember> taskListEmpty = new ArrayList<TaskMember>();
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
				pdata = this.pdao.getIndividualProject(viewTaskProjectId);
				ProjectMember[] members;
				members = pdata.getMembers();
				for (int i = 0; i < members.length; i++) {
					TaskMember tmember = new TaskMember();
					List<TaskData> tasklist = new ArrayList<TaskData>();
					int totalHour = 0;
					int totalMinute = 0;
					tmember.setName(members[i].getname());
					tmember.setEmail(members[i].getemail());
					tmember.setImage(members[i].getimage());
					tmember.setRole(members[i].getrole());
					tmember.setAddedDate(members[i].getAddedDate());
					tmember.setDeletedDate(members[i].getDeletedDate());
					tmember.setIsActive(members[i].getIsActive());
					Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(members[i].getAddedDate());
					Date date2;
					if (!members[i].getDeletedDate().equals("")) {
						date2 = new SimpleDateFormat("dd-MM-yyyy").parse(members[i].getDeletedDate());
					} else {
						date2 = new Date();
					}
					Date date3 = new SimpleDateFormat("dd-MM-yyyy").parse(viewTaskDate);
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
					String addedDate = formatter.format(date1);
					String deletedDate = formatter.format(date2);
					String taskDate = formatter.format(date3);
					if ((date3.after(date1) || taskDate.equals(addedDate))
							&& (date3.before(date2) || taskDate.equals(deletedDate))) {
						if ((date3.after(date1) || taskDate.equals(addedDate))
								&& (date3.before(date2) || taskDate.equals(deletedDate))) {
							tasklist = readTaskList(viewTaskDate, members[i].getemail(), viewTaskProjectId);
						}
						TaskData[] tasks = new TaskData[tasklist.size()];
						tasks = tasklist.toArray(tasks);
						tmember.setTasks(tasks);
						for (int j = 0; j < tasks.length; j++) {
							totalHour += tasks[j].getHourSpent();
							totalMinute += tasks[j].getMinuteSpent();
						}
						double extrahour = 0;
						if (totalMinute >= 60) {
							extrahour = Math.floor(totalMinute / 60);
							totalMinute = totalMinute % 60;
						}
						totalHour += extrahour;
						tmember.setHour(totalHour);
						tmember.setMinute(totalMinute);
						taskList.add(tmember);
					}
				}
				Iterator<TaskMember> taskIterator = taskList.iterator();
				while (taskIterator.hasNext()) {
					TaskMember tMember = (TaskMember) taskIterator.next();
					if (tMember.getTasks().length > 0) {
						taskListNonEmpty.add(tMember);
					} else if (tMember.getTasks().length == 0) {
						taskListEmpty.add(tMember);
					}
				}
				taskListNonEmpty.addAll(taskListEmpty);
				return taskListNonEmpty;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return taskList;
	}
}
