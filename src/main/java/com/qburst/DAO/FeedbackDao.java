package com.qburst.DAO;

import java.util.ArrayList;
import java.util.List;

import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.operation.OrderBy;
import com.qburst.Model.Feedback;

public class FeedbackDao extends connection {

	@SuppressWarnings("deprecation")
	public Feedback insertFeedback(Feedback feedback) throws Exception {
		DB db;
		Feedback fback = new Feedback();
		DBCursor<Feedback> result = null;
//		MongoClient mongo = null;
		try {
			MongoClient mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Feedback");
			JacksonDBCollection<Feedback, Object> coll = JacksonDBCollection.wrap(table, Feedback.class, Object.class);
			coll.insert(feedback);
			result = coll.find().is("feedbackId", feedback.getFeedbackId());
			if (result.hasNext()) {
				fback = result.next();
				result.close();
				return fback;
			}
		} catch (Exception e) {
		} finally {
			if (result != null) {
				result.close();
			}
//			if (mongo != null) {
//				mongo.close();
//			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public List<Feedback> readFeedbacks() {
		DB db;
		Feedback feedback = new Feedback();
		DBCursor<Feedback> result = null;
//		MongoClient mongo = null;
		List<Feedback> feedbackList = new ArrayList<Feedback>();
		try {
			MongoClient mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Feedback");
			JacksonDBCollection<Feedback, Object> coll = JacksonDBCollection.wrap(table, Feedback.class, Object.class);
			DBObject feedbackDBObject = new BasicDBObject("feedbackDate", OrderBy.DESC.getIntRepresentation());
			result = coll.find().sort(feedbackDBObject);
			while (result.hasNext()) {
				feedback = result.next();
				feedbackList.add(feedback);
			}
			return feedbackList;
		} catch (Exception e) {
		} finally {
			if (result != null) {
				result.close();
			}
//			if (mongo != null) {
//				mongo.close();
//			}
		}
		return null;
	}
}
