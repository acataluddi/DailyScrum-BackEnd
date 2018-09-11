package com.qburst.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.mongojack.JacksonDBCollection;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.qburst.Model.ProjectData;
import com.qburst.Model.TaskData;
import com.qburst.Model.UsersData;

public class ScrumDao extends connection {
	StringBuilder hash = new StringBuilder();

	@SuppressWarnings("deprecation")
	public UsersData insertIntoTable(UsersData usersData) throws Exception {
		/*
		 * Create database called Scrum and create a collection called Employee.
		 */
		DB db;
		DBCursor result = null;
		String UserType;
		UsersData user = new UsersData();
		try {

			MongoClient mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Employee");
			BasicDBObject document = new BasicDBObject();
			DBObject query = new BasicDBObject("Email", usersData.getEmail());
			DBCursor results = table.find(query);
			while (results.hasNext()) {
				List<DBObject> cursor = results.toArray();
				BasicDBObject userObj = (BasicDBObject) cursor.get(0);
				String MemberID = userObj.getString("EmployeeID");
				String Name = userObj.getString("Name");
				String Email = userObj.getString("Email");
				String imageURL = userObj.getString("imageurl");
				UserType = userObj.getString("userType");

				user.setEmployeeID(MemberID);
				user.setName(Name);
				user.setEmail(Email);
				user.setUserType(UserType);
				user.setImageurl(imageURL);

				return user;
			}
			document.put("EmployeeID", usersData.getEmployeeID()); // used to calculate the next value of the EmployeID
			document.put("Name", usersData.getName());
			document.put("Email", usersData.getEmail());
			document.put("userType", usersData.getUserType());
			document.put("imageurl", usersData.getImageurl());

			table.insert(document);
			DBObject querycheck = new BasicDBObject("Email", usersData.getEmail());
			result = table.find(querycheck);

			List<DBObject> cursor = result.toArray();
			BasicDBObject userObj = (BasicDBObject) cursor.get(0);
			String MemberID = userObj.getString("EmployeeID");
			String Name = userObj.getString("Name");
			String Email = userObj.getString("Email");
			String imageURL = userObj.getString("imageurl");
			UserType = userObj.getString("userType");

			user.setEmployeeID(MemberID);
			user.setName(Name);
			user.setEmail(Email);
			user.setUserType(UserType);
			user.setImageurl(imageURL);

		} catch (Exception e) {
		}
		while (result.hasNext()) {
			return user;
		}

		return user;

	}

	

	// Neeraj: Code for delete Project
//	@SuppressWarnings("deprecation")
//	public boolean subtractProject(String projectData) throws Exception {
//		DB db;
//		DBCursor result = null;
//		try {
//
//			MongoClient mongo = databaseConnection();
//			db = mongo.getDB("Scrum");
//			DBCollection collection = db.getCollection("Project");
//			String deletionProjectId = projectData.getProjectId();
//			BasicDBObject deleteQuery = new BasicDBObject();
//			System.out.println("ScrumDAO");
//			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
//			obj.add(new BasicDBObject("projectId", projectData));
//			deleteQuery.put("$and", obj);
//			collection.remove(deleteQuery);
//			System.out.println("Project Deleted");
//			result = collection.find(deleteQuery);
//			while (result.hasNext()) {
//				return false;
//			}
//		} catch (Exception e) {
//		}
//		return true;
//	}

	

	@SuppressWarnings("deprecation")
	public List<UsersData> readUserList(int pagenum, int num_of_rec) throws Exception {
		DB db;
		List<UsersData> userlist = new ArrayList<UsersData>();
		try {

			MongoClient mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection collection = db.getCollection("Employee");
			

			List<DBObject> cursor = collection.find().skip(num_of_rec * (pagenum - 1)).limit(num_of_rec).toArray();
			for (int i = 0; i < cursor.size(); i++) {
				BasicDBObject userObj = (BasicDBObject) cursor.get(i);


				String MemberID = userObj.getString("EmployeeID");// change
				String Name = userObj.getString("Name");
				String Email = userObj.getString("Email");
				String UserType = userObj.getString("userType");
				String imageURL = userObj.getString("imageurl");


				UsersData user = new UsersData();
				user.setImageurl(imageURL);
				user.setEmployeeID(MemberID);
				user.setName(Name);
				user.setEmail(Email);
				user.setUserType(UserType);
				user.setImageurl(imageURL);
				userlist.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userlist;
	}

	@SuppressWarnings("deprecation")
	public float getCount() {
		
		float NoOfRecords = 0;
		DB db;
		try {
			
		MongoClient mongo = databaseConnection();
		db = mongo.getDB("Scrum");
		DBCollection collection = db.getCollection("Employee");
		NoOfRecords = collection.count();
		System.out.println(NoOfRecords);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NoOfRecords;
	}
	
	@SuppressWarnings("deprecation")
	public List<TaskData> readTaskList(String viewTaskDate, String viewTaskEmpId) throws Exception {
		DB db;
		List<TaskData> tasklist = new ArrayList<TaskData>();
		try {

			MongoClient mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection collection = db.getCollection("Task");
			BasicDBObject andQuery = new BasicDBObject();
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(new BasicDBObject("taskDate", viewTaskDate));
			obj.add(new BasicDBObject("employeeId", viewTaskEmpId));
			andQuery.put("$and", obj);
			List<DBObject> cursor = collection.find(andQuery).toArray();
			for (int i = 0; i < cursor.size(); i++) {
				BasicDBObject userObj = (BasicDBObject) cursor.get(i);

				String TaskID = userObj.getString("taskId");
				String EmployeeID = userObj.getString("employeeId");
				String TaskDescription = userObj.getString("taskDesc");
				String Impediment = userObj.getString("impediment");

				int ProjectID = userObj.getInt("projectId");
				String TaskDate = userObj.getString("taskDate");
				int TimeSpent = userObj.getInt("timeSpent");
				String TimeStamp = userObj.getString("timeStamp");
				TaskData task = new TaskData();
				task.setTaskId(TaskID);
				task.setEmployeeId(EmployeeID);
				task.setTaskDesc(TaskDescription);
				task.setImpediment(Impediment);
				task.setProjectId(ProjectID);
				task.setTaskDate(TaskDate);
				task.setTimeSpent(TimeSpent);
				task.setTimeStamp(TimeStamp);
				tasklist.add(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tasklist;
	}

	public UsersData userTypeUpdate(UsersData usersData) throws SQLException {

		DB db;
		try {
			MongoClient mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection collection = db.getCollection("Employee");
			BasicDBObject updateDocument = new BasicDBObject();
			updateDocument.append("$set", new BasicDBObject().append("userType", usersData.getUserType()));
			BasicDBObject searchQuery = new BasicDBObject().append("Email", usersData.getEmail());
			collection.update(searchQuery, updateDocument);
			System.out.println(updateDocument);
			DBCursor cursor = collection.find();
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public UsersData MemberTaskUpdate(UsersData usersData) throws SQLException {
		return null;
	}

	public List<ProjectData> readProjectNames() throws SQLException {
		return null;
	}

	public List<UsersData> readEmployeeData() throws SQLException {
		return null;
	}

	public List<ProjectData> readProjectMemberData() throws SQLException {
		return null;
	}

	public List<TaskData> readYesterdayTask() throws SQLException {
		return null;
	}

	public List<TaskData> readTodayTask() throws SQLException {
		return null;
	}

	public boolean login(UsersData usersData) {
		return false;
	}

	public List<UsersData> readData(int n) throws SQLException {
		return null;
	}

	@SuppressWarnings("deprecation")
	public boolean insertTask(TaskData taskData) throws Exception {

		DB db;
		DBCursor result = null;
		try {
			MongoClient mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection collection = db.getCollection("Task");
			BasicDBObject document = new BasicDBObject();
			document.put("taskId", taskData.getTaskId().toString());
			document.put("employeeId", taskData.getEmployeeId().toString());
			document.put("taskDesc", taskData.getTaskDesc());
			document.put("impediment", taskData.getImpediment());
			document.put("projectId", taskData.getProjectId());
			document.put("taskDate", taskData.getTaskDate().toString());
			document.put("timeSpent", taskData.getTimeSpent());
			document.put("timeStamp", taskData.getTimeStamp().toString());
			collection.insert(document);
			DBObject query = new BasicDBObject("timeStamp", taskData.getTimeStamp());
			result = collection.find(query);
		} catch (Exception e) {
			// TODO: handle exception
		}
		while (result.hasNext()) {
			return true;
		}
		return false;
	}


	@SuppressWarnings("deprecation")

	public boolean updateTask(TaskData taskData) throws Exception {
		DB db;
		DBCursor result = null;
		try {
			MongoClient mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection collection = db.getCollection("Task");
			// String taskDateView = taskData.getTaskDate();
			String updationTaskId = taskData.getTaskId();
			String updationEmployeeId = taskData.getEmployeeId();
			BasicDBObject updateQuery = new BasicDBObject();
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(new BasicDBObject("taskId", updationTaskId));
			obj.add(new BasicDBObject("employeeId", updationEmployeeId));
			updateQuery.put("$and", obj);
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("taskId", taskData.getTaskId());
			newDocument.put("employeeId", taskData.getEmployeeId());
			newDocument.put("taskDesc", taskData.getTaskDesc());
			newDocument.put("impediment", taskData.getImpediment());
			newDocument.put("projectId", taskData.getProjectId());
			newDocument.put("taskDate", taskData.getTaskDate());
			newDocument.put("timeSpent", taskData.getTimeSpent());
			newDocument.put("timeStamp", taskData.getTimeStamp());
			collection.update(updateQuery, newDocument);
			result = collection.find(updateQuery);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public boolean subtractTask(TaskData taskData) throws Exception {
		DB db;
		DBCursor result = null;
		try {

			MongoClient mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection collection = db.getCollection("Task");
			String deletionTaskId = taskData.getTaskId();
			String deletionEmployeeId = taskData.getEmployeeId();
			BasicDBObject deleteQuery = new BasicDBObject();
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(new BasicDBObject("taskId", deletionTaskId));
			obj.add(new BasicDBObject("employeeId", deletionEmployeeId));
			deleteQuery.put("$and", obj);
			collection.remove(deleteQuery);
			result = collection.find(deleteQuery);
			while (result.hasNext()) {
				return false;
			}
		} catch (Exception e) {
		}
		return true;
	}
}