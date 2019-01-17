package com.qburst.DAO;

import com.mongodb.MongoClient;

public abstract class connection {

	private static MongoClient mongo = null;

	public MongoClient databaseConnection() throws Exception {
		if (mongo == null)
			mongo = new MongoClient("localhost", 27017);
		return mongo;
	}

	public void finalize(){
		if (mongo != null)
			mongo.close();
	}
}
