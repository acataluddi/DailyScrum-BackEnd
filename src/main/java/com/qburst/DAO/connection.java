package com.qburst.DAO;

import com.mongodb.MongoClient;

public abstract class connection {
	public MongoClient databaseConnection() throws Exception {

		MongoClient mongo = new MongoClient("localhost", 27017);
		return mongo;
	}
}
