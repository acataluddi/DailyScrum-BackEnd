package com.qburst.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.qburst.Model.ProjectData;
import com.qburst.Model.TaskData;
import com.qburst.Model.UsersData;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class ScrumDao extends connection {
	StringBuilder hash = new StringBuilder();

	public boolean insertIntoTable(UsersData usersData) throws Exception {
		/*
		 * Create database called Scrum and create a collection called Employee. Then
		 * this step is done to auto increment the Employee ID field -
		 * db.Employee.insert({EmployeeID : "id", seq : 0})
		 */
		DB db;
		DBCursor result = null;
		try {
			db = databaseConnection();
			DBCollection table = db.getCollection("Employee");
			BasicDBObject document = new BasicDBObject();
			DBObject query = new BasicDBObject("Email", usersData.getEmail());
			DBCursor results = table.find(query);
			while (results.hasNext()) {
				return false;
			}
			document.put("EmployeeID", getNextSequence1("id")); // used to calculate the next value of the EmployeID
			document.put("Name", usersData.getName());
			document.put("Email", usersData.getEmail());
			document.put("UserType", usersData.getUserType());
			table.insert(document);
			DBObject querycheck = new BasicDBObject("Email", usersData.getEmail());
			result = table.find(querycheck);
		} catch (Exception e) {
		}
		while (result.hasNext()) {
			return true;
		}
		return false;
	}

	public Object getNextSequence1(String name) throws Exception {
		DB db;
		db = databaseConnection();
		DBCollection collection = db.getCollection("Employee");
		BasicDBObject find = new BasicDBObject();
		find.put("EmployeeID", name);
		BasicDBObject update = new BasicDBObject();
		update.put("$inc", new BasicDBObject("seq", 1));
		DBObject obj = collection.findAndModify(find, update);
		return obj.get("seq");
	}

	// Neeraj: Code for add Project

	/*
	 * Create database called Scrum and create a collection called Project. Then
	 * this step is done to auto increment the ProjectId field -
	 * db.Project.insert({ProjectId:"id",seq : 0})
	 */

	public boolean insertProject(ProjectData projectData) throws Exception {
		DB db;
		DBCursor result = null;
		try {
			db = databaseConnection();
			DBCollection table = db.getCollection("Project");
			BasicDBObject document = new BasicDBObject();
			DBObject query = new BasicDBObject("ProjectName", projectData.getProjectName());
			DBCursor results = table.find(query);
			while (results.hasNext()) {
				return false;
			}
			document.put("ProjectId", getNextSequence2("id"));
			document.put("ProjectName", projectData.getProjectName());
			document.put("Description", projectData.getProjectDesc());
			document.put("Member", projectData.getMemberId());
			table.insert(document);

			DBObject querycheck = new BasicDBObject("ProjectName", projectData.getProjectName());
			result = table.find(querycheck);
		} catch (Exception e) {
		}
		while (result.hasNext()) {
			return true;
		}
		return false;
	}

	public Object getNextSequence2(String name) throws Exception {
		DB db;
		db = databaseConnection();
		DBCollection collection = db.getCollection("Project");
		BasicDBObject find = new BasicDBObject();
		find.put("ProjectId", name);
		BasicDBObject update = new BasicDBObject();
		update.put("$inc", new BasicDBObject("seq", 1));
		DBObject obj = collection.findAndModify(find, update);
		return obj.get("seq");
	}

	// Neeraj: Code for delete Project

	public boolean subtractProject(ProjectData projectData) throws Exception {
		DB db;
		DBCursor result = null;
		try {
			db = databaseConnection();
			MongoClient mongo = databaseConnection2();
			MongoDatabase database = mongo.getDatabase("Scrum");
			MongoCollection<Document> collection = database.getCollection("Project");
			System.out.println("Collection Project selected successfully");
			int deletionId = projectData.getProjectId();
			System.out.println("deletion ID is" + deletionId);
			collection.deleteOne(Filters.eq("ProjectId", deletionId));
			System.out.println("Document deleted successfully...");
			DBCollection table = db.getCollection("Project");

			DBObject query = new BasicDBObject("ProjectId", deletionId);
			result = table.find(query);
			while (result.hasNext()) {
				return false;
			}
		} catch (Exception e) {
		}
		return true;
	}

	// Neeraj: Code for edit project

	public boolean updateProject(ProjectData projectData) throws Exception {
		DB db;
		try {
			MongoClient mongo = databaseConnection2();
			MongoDatabase database = mongo.getDatabase("Scrum");
			MongoCollection<Document> collection = database.getCollection("Project");
			System.out.println("Collection Project selected successfully");
			int editId = projectData.getProjectId();
			collection.updateOne(Filters.eq("ProjectId", editId),
					Updates.set("ProjectName", projectData.getProjectName()));
			collection.updateOne(Filters.eq("ProjectId", editId),
					Updates.set("Description", projectData.getProjectDesc()));

			// Neeraj: Code for update the array "Member"

			BasicDBObject updateQuery = new BasicDBObject();
			updateQuery.append("$set", new BasicDBObject().append("Member", projectData.getMemberId()));
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.append("ProjectId", editId);
			db = databaseConnection();
			DBCollection table = db.getCollection("Project");
			table.update(searchQuery, updateQuery);

			System.out.println("Document update successfully...");
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public List<UsersData> readUserList(int pagenum, int num_of_rec) throws Exception {
		DB db;
		List<UsersData> userlist = new ArrayList<UsersData>();
		try {
			db = databaseConnection();
			DBCollection collection = db.getCollection("Employee");
			List<DBObject> cursor = collection.find().skip(num_of_rec * (pagenum - 1)).limit(num_of_rec).toArray();
			for (int i = 0; i < cursor.size(); i++) {
				BasicDBObject userObj = (BasicDBObject) cursor.get(i);
				String Name = userObj.getString("Name");
				String Email = userObj.getString("Email");
				String UserType = userObj.getString("UserType");
				UsersData user = new UsersData();
				user.setName(Name);
				user.setEmail(Email);
				user.setUserType(UserType);
				userlist.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userlist;
	}

	public UsersData MemberProjectUpdate(UsersData usersData) throws SQLException {
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
}