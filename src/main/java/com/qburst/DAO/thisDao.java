package com.qburst.DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import com.qburst.Model.Data;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class thisDao extends connection{
	
	StringBuilder hash = new StringBuilder();
	
	public Data insertDataBase(Data data) throws Exception{
		
		/*Create database called Scrum and create a collection called Employee. 
		 * Then this step is done to auto increment the Employee ID field - 
		 * db.Employee.insert({EmployeeID : "id", seq : 0})
		 * */
		
		DB db;
		String saltStr;
		String saltedPassword;
		
		try {

			db = databaseConnection();
			

			DBCollection table = db.getCollection("Employee");

			BasicDBObject document = new BasicDBObject();

			document.put("EmployeeID", getNextSequence("id"));	//used to calculate the next value of the EmployeID
			document.put("Name", data.getName());
			document.put("Email", data.getEmail());
			
			saltStr = getSaltString();
			saltedPassword = saltStr + data.getPassword();
			hash = getHashedPassword(saltedPassword); 
					
			
			document.put("Password", hash.toString());
			document.put("Hash", saltStr);
			document.put("Role", data.getRole());
			table.insert(document);
		}catch(Exception e) {
			
		}
		return data;
		
		
		
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

	private StringBuilder getHashedPassword(String saltedPassword) {
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] hashedBytes;
		char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		hashedBytes = sha.digest(saltedPassword.getBytes());
		for (int idx = 0; idx < hashedBytes.length; ++idx) {
			byte b = hashedBytes[idx];
			hash.append(digits[(b & 0xf0) >> 4]);
			hash.append(digits[b & 0x0f]);
		}
		return hash;
	}

	protected static String getSaltString() {
		String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		float me;
		int index;
		Random rnd = new Random();
		while (salt.length() < 18) { 
			me = rnd.nextFloat();
			index = (int) (me * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
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