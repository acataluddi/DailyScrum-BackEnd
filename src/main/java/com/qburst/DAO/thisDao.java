package com.qburst.DAO;

import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.qburst.Model.Data;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class thisDao {
	
	StringBuilder hash = new StringBuilder();
	
	public Data insertDataBase(Data data) throws Exception{
		//Data s1 = new Data();
		String saltStr;
		String saltedPassword;
		byte[] hashedBytes;
		char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {

			MongoClient mongo = new MongoClient("localhost", 27017);

			DB db = mongo.getDB("test");

			DBCollection table = db.getCollection("Employee");

			BasicDBObject document = new BasicDBObject();
			
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			
			document.put("Employee ID", data.getMemberID());
			document.put("Name", data.getName());
			document.put("Email", data.getEmail());
			
			saltStr = getSaltString();
			saltedPassword = saltStr + data.getPassword();
			hashedBytes = sha.digest(saltedPassword.getBytes());
			for (int idx = 0; idx < hashedBytes.length; ++idx) {
				byte b = hashedBytes[idx];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
			
			document.put("Password", hash.toString());
			document.put("Hash", saltStr);
			document.put("Role", data.getRole());
			table.insert(document);
		}catch(Exception e) {
			
		}
		return data;
		
		
		
	}
	
	protected static String getSaltString() {
		String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		float me;
		int index;
		Random rnd = new Random();
		while (salt.length() < 18) { // length of the random string.
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