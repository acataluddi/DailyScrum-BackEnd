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
		DBCursor results = null;
		String UserType;
		UsersData user = new UsersData();
		MongoClient mongo;
		try {

			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Employee");
			BasicDBObject document = new BasicDBObject();
			DBObject query = new BasicDBObject("Email", usersData.getEmail());
			results = table.find(query);
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
			while (result.hasNext()) {
				return user;
			}
		} catch (Exception e) {
		} finally {
			if (result != null
//					&& mongo != null
			) {
				result.close();
				results.close();
//				mongo.close();
			}
		}

		return user;
	}

	@SuppressWarnings("deprecation")
	public UsersData getIndividualUser(String email) throws Exception {
		DB db;
		UsersData user = new UsersData();
		DBCursor result = null;
		MongoClient mongo;
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Employee");
			DBObject query = new BasicDBObject("Email", email);
			result = table.find(query);
			while (result.hasNext()) {
				List<DBObject> cursor = result.toArray();
				BasicDBObject userObj = (BasicDBObject) cursor.get(0);
				String Name = userObj.getString("Name");
				String Email = userObj.getString("Email");
				String imageURL = userObj.getString("imageurl");
				String type = userObj.getString("userType");
				String id = userObj.getString("EmployeeID");
				user.setEmployeeID(id);
				user.setName(Name);
				user.setEmail(Email);
				user.setImageurl(imageURL);
				user.setUserType(type);
				return user;
			}
		} catch (Exception e) {
		} finally {
			if (result != null
//					&& mongo != null
			) {
				result.close();
//				mongo.close();
			}
		}
		return user;
	}

	@SuppressWarnings("deprecation")
	public List<UsersData> readUserList(int pagenum, int num_of_rec) throws Exception {
		DB db;
		List<UsersData> userlist = new ArrayList<UsersData>();
		MongoClient mongo;
		try {

			mongo = databaseConnection();
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
//		finally {
//			if (mongo != null) {
//				mongo.close();
//			}
//		}
		return userlist;
	}

	@SuppressWarnings("deprecation")
	public List<TaskData> readTaskList(String viewTaskDate, String viewTaskMemberEmail, String viewTaskProjectId)
			throws Exception {

		DB db;
		MongoClient mongo;
		List<TaskData> tasklist = new ArrayList<TaskData>();
		try {

			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection collection = db.getCollection("Task");
			BasicDBObject andQuery = new BasicDBObject();
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(new BasicDBObject("taskDate", viewTaskDate));
			obj.add(new BasicDBObject("memberEmail", viewTaskMemberEmail));
			obj.add(new BasicDBObject("projectId", viewTaskProjectId));
			andQuery.put("$and", obj);
			List<DBObject> cursor = collection.find(andQuery).toArray();
			for (int i = 0; i < cursor.size(); i++) {
				BasicDBObject userObj = (BasicDBObject) cursor.get(i);
				String TaskID = userObj.getString("taskId");
				String memberEmail = userObj.getString("memberEmail");
				String description = userObj.getString("description");
				String Impediment = userObj.getString("impediments");
				String ProjectID = userObj.getString("projectId");
				String TaskDate = userObj.getString("taskDate");
				int hourSpent = userObj.getInt("hourSpent");
				int minuteSpent = userObj.getInt("minuteSpent");
				boolean taskCompleted = userObj.getBoolean("taskCompleted");
				String lastEdit = userObj.getString("lastEdit");

				TaskData task = new TaskData();
				task.setTaskId(TaskID);
				task.setMemberEmail(memberEmail);
				task.setDescription(description);
				task.setImpediments(Impediment);
				task.setProjectId(ProjectID);
				task.setTaskDate(TaskDate);
				task.setHourSpent(hourSpent);
				task.setMinuteSpent(minuteSpent);
				task.setTaskCompleted(taskCompleted);
				task.setLastEdit(lastEdit);
				tasklist.add(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		finally {
//			if (mongo != null) {
//				mongo.close();
//			}
//		}
		return tasklist;
	}

	public UsersData userTypeUpdate(UsersData usersData) throws SQLException {

		DB db;
		DBCursor cursor = null;
		MongoClient mongo;
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection collection = db.getCollection("Employee");
			BasicDBObject updateDocument = new BasicDBObject();
			updateDocument.append("$set", new BasicDBObject().append("userType", usersData.getUserType()));
			BasicDBObject searchQuery = new BasicDBObject().append("Email", usersData.getEmail());
			collection.update(searchQuery, updateDocument);
			System.out.println(updateDocument);
			cursor = collection.find();
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (cursor != null
//					&& mongo != null
			) {
				cursor.close();
//				mongo.close();
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public boolean insertTask(TaskData taskData) throws Exception {

		DB db;
		DBCursor result = null;
		MongoClient mongo;
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection collection = db.getCollection("Task");
			BasicDBObject document = new BasicDBObject();
			document.put("taskId", taskData.getTaskId());
			document.put("memberEmail", taskData.getMemberEmail());
			document.put("description", taskData.getDescription());
			document.put("impediments", taskData.getImpediments());
			document.put("taskDate", taskData.getTaskDate());
			document.put("projectId", taskData.getProjectId());
			document.put("hourSpent", taskData.getHourSpent());
			document.put("minuteSpent", taskData.getMinuteSpent());
			document.put("taskCompleted", taskData.getTaskCompleted());
			document.put("lastEdit", taskData.getLastEdit());
			collection.insert(document);
			DBObject query = new BasicDBObject("taskId", taskData.getTaskId());
			result = collection.find(query);
			while (result.hasNext()) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (result != null
//					&& mongo != null
			) {
				result.close();
//				mongo.close();
			}
		}
		return false;

	}

	@SuppressWarnings("deprecation")

	public boolean updateTask(TaskData taskData) throws Exception {
		DB db;
		DBCursor result = null;
		MongoClient mongo;
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection collection = db.getCollection("Task");
			// String taskDateView = taskData.getTaskDate();
			String updationTaskId = taskData.getTaskId();
			String updationMemberEmail = taskData.getMemberEmail();
			BasicDBObject updateQuery = new BasicDBObject();
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(new BasicDBObject("taskId", updationTaskId));
			obj.add(new BasicDBObject("memberEmail", updationMemberEmail));
			updateQuery.put("$and", obj);
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("taskId", taskData.getTaskId());
			newDocument.put("memberEmail", taskData.getMemberEmail());
			newDocument.put("description", taskData.getDescription());
			newDocument.put("impediments", taskData.getImpediments());
			newDocument.put("projectId", taskData.getProjectId());
			newDocument.put("taskDate", taskData.getTaskDate());
			newDocument.put("hourSpent", taskData.getHourSpent());
			newDocument.put("minuteSpent", taskData.getMinuteSpent());
			newDocument.put("taskCompleted", taskData.getTaskCompleted());
			newDocument.put("lastEdit", taskData.getLastEdit());
			collection.update(updateQuery, newDocument);
			result = collection.find(updateQuery);
		} catch (Exception e) {
			return false;
		} finally {
			if (result != null
//					&& mongo != null
			) {
				result.close();
//				mongo.close();
			}
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public boolean subtractTask(TaskData taskData) throws Exception {
		DB db;
		DBCursor result = null;
		MongoClient mongo;
		try {

			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection collection = db.getCollection("Task");
			String deletionTaskId = taskData.getTaskId();
			String deletionMemberEmail = taskData.getMemberEmail();
			BasicDBObject deleteQuery = new BasicDBObject();
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(new BasicDBObject("taskId", deletionTaskId));
			obj.add(new BasicDBObject("memberEmail", deletionMemberEmail));
			deleteQuery.put("$and", obj);
			collection.remove(deleteQuery);
			result = collection.find(deleteQuery);
			while (result.hasNext()) {
				return false;
			}
		} catch (Exception e) {
		} finally {
			if (result != null
//					&& mongo != null
			) {
				result.close();
//				mongo.close();
			}
		}
		return true;
	}

}