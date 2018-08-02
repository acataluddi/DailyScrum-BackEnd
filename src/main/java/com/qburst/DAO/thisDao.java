package com.qburst.DAO;

import java.sql.SQLException;
import java.util.List;
import com.qburst.Model.Data;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class thisDao extends connection{
	
	StringBuilder hash = new StringBuilder();
	
	public boolean insertIntoTable(Data data) throws Exception{
		
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
			
			DBObject query = new BasicDBObject("Email", data.getEmail());
			DBCursor results = table.find(query);
			while (results.hasNext()) {
				return false;
			}

			document.put("EmployeeID", getNextSequence("id"));	//used to calculate the next value of the EmployeID
			document.put("Name", data.getName());
			document.put("Email", data.getEmail());
			document.put("Role", data.getDesignation());
			table.insert(document);
			
			DBObject querycheck = new BasicDBObject("Email", data.getEmail());
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

	public Data MemberProjectUpdate(Data data)throws SQLException {
		return null;
	}
	public Data MemberTaskUpdate(Data data)throws SQLException {
		return null;
	}
	public List<Data> readProjectNames()throws SQLException {
		return null;
	}
	public List<Data> readEmployeeData()throws SQLException {
		return null;
	}
	public List<Data> readProjectMemberData() throws SQLException {
		return null;
	}
	public List<Data> readYesterdayTask() throws SQLException {
		return null;
	}
	public List<Data> readTodayTask() throws SQLException {
		return null;
	}
	public boolean login(Data data) {
		return false;
	}
	public List<Data> readData(int n)throws SQLException {
		return null;
	}
}