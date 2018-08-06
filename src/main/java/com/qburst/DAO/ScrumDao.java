package com.qburst.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import com.qburst.Model.ProjectData;
import com.qburst.Model.TaskData;
import com.qburst.Model.UsersData;

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

			document.put("EmployeeID", getNextSequence("id")); // used to calculate the next value of the EmployeID
			document.put("Name", usersData.getName());
			document.put("Email", usersData.getEmail());
			document.put("Role", usersData.getUserType());
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

	public Object getNextSequence(String name) throws Exception {

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
				String UserType = userObj.getString("Role");

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