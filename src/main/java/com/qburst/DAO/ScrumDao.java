package com.qburst.DAO;

import java.sql.SQLException;
import java.util.List;
import com.qburst.Model.UsersData;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ScrumDao extends connection{
	
	StringBuilder hash = new StringBuilder();
	
	public boolean insertIntoTable(UsersData usersData) throws Exception{
		
		/*Create database called Scrum and create a collection called Employee. 
		 * Then this step is done to auto increment the Employee ID field - 
		 * db.Employee.insert({EmployeeID : "id", seq : 0})
		 * */
		
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

			document.put("EmployeeID", getNextSequence("id"));	//used to calculate the next value of the EmployeID
			document.put("Name", usersData.getName());
			document.put("Email", usersData.getEmail());
			document.put("Role", usersData.getDesignation());
			table.insert(document);
			
			DBObject querycheck = new BasicDBObject("Email", usersData.getEmail());
			result = table.find(querycheck);
			
		}catch(Exception e) {
			
		}
		
		
		while (result.hasNext()) {
			return true;
		}
		
		return false;
		
		
		
	}
	
	public Object getNextSequence(String name) throws Exception{
		
		DB db;
		
		db = databaseConnection();
		
	    DBCollection collection = db.getCollection("Employee");
	    BasicDBObject find = new BasicDBObject();
	    find.put("EmployeeID", name);
	    BasicDBObject update = new BasicDBObject();
	    update.put("$inc", new BasicDBObject("seq", 1));
	    DBObject obj =  collection.findAndModify(find, update);
	    
	    return obj.get("seq");

	}

	public UsersData MemberProjectUpdate(UsersData usersData)throws SQLException {
		return null;
	}
	public UsersData MemberTaskUpdate(UsersData usersData)throws SQLException {
		return null;
	}
	public List<UsersData> readProjectNames()throws SQLException {
		return null;
	}
	public List<UsersData> readEmployeeData()throws SQLException {
		return null;
	}
	public List<UsersData> readProjectMemberData() throws SQLException {
		return null;
	}
	public List<UsersData> readYesterdayTask() throws SQLException {
		return null;
	}
	public List<UsersData> readTodayTask() throws SQLException {
		return null;
	}
	public boolean login(UsersData usersData) {
		return false;
	}
	public List<UsersData> readData(int n)throws SQLException {
		return null;
	}
}