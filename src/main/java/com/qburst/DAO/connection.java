package com.qburst.DAO;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public abstract class connection {
	
	public DB databaseConnection() throws Exception {
		
		MongoClient mongo = new MongoClient("localhost", 27017);

		DB db = mongo.getDB("Scrum");
		
		return db;
		
	}

}
