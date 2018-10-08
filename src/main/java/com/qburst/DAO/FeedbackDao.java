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
import com.qburst.Model.Feedback;

public class FeedbackDao extends connection {

	@SuppressWarnings({ "deprecation", "resource" })
	public Feedback insertFeedback(Feedback feedback) throws Exception {
		DB db;

		Feedback fb = new Feedback();
		DBCursor<Feedback> result = null;
		MongoClient mongo = null;
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Feedback");
			JacksonDBCollection<Feedback, Object> coll = JacksonDBCollection.wrap(table, Feedback.class, Object.class);
			DBObject query = new BasicDBObject("feedbackId", feedback.getFeedbackId());
			result = coll.find(query);
			if (result.hasNext()) {
				fb = result.next();
				result.close();
				return fb;
			}
			coll.insert(feedback);
			result = coll.find().is("feedbackId", feedback.getFeedbackId());
			if (result.hasNext()) {
				fb = result.next();
				result.close();
				return fb;
			}
		} catch (Exception e) {
		} finally {
			if (result != null) {
				result.close();
			}
			if (mongo != null) {
				mongo.close();
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public List<Feedback> readFeedbacks(int pagenum, int num_of_rec) throws Exception {

		DB db;
		List<Feedback> feedbackList = new ArrayList<Feedback>();
		Feedback fb = new Feedback();
		DBCursor<Feedback> result = null;
		MongoClient mongo = null;
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Feedback");
			JacksonDBCollection<Feedback, Object> coll = JacksonDBCollection.wrap(table, Feedback.class, Object.class);
			result = coll.find().skip(num_of_rec * (pagenum - 1)).limit(num_of_rec);
			while (result.hasNext()) {
				fb = result.next();
				feedbackList.add(fb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (result != null) {
				result.close();
			}
			if (mongo != null) {
				mongo.close();
			}
		}
		return feedbackList;
	}
}
