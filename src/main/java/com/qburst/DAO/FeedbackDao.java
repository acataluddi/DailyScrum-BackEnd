package com.qburst.DAO;

import java.util.ArrayList;
import java.util.List;

import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.qburst.Model.Feedback;
import com.qburst.Model.FeedbackMember;
import com.qburst.Model.NavBarMember;

public class FeedbackDao extends connection {

	@SuppressWarnings({ "deprecation", "resource" })
	public FeedbackMember insertFeedback(FeedbackMember feedbackMember) throws Exception {
		DB db;

		FeedbackMember fMember = new FeedbackMember();
		DBCursor<FeedbackMember> result = null;
		MongoClient mongo = null;
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Feedback");
			JacksonDBCollection<FeedbackMember, Object> coll = JacksonDBCollection.wrap(table, FeedbackMember.class,
					Object.class);
			DBObject query = new BasicDBObject("userId", feedbackMember.getUserId());
			result = coll.find(query);
			if (result.hasNext()) {
				coll.update(DBQuery.is("userId", feedbackMember.getUserId()), feedbackMember);
			} else {
				coll.insert(feedbackMember);
			}
			result = coll.find().is("userId", feedbackMember.getUserId());
			if (result.hasNext()) {
				fMember = result.next();
				result.close();
				return fMember;
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
	public FeedbackMember getIndividualMemberFeedback(String userEmail) {
		DB db;
		FeedbackMember feedbackMember = new FeedbackMember();
		DBCursor<FeedbackMember> result = null;
		MongoClient mongo = null;
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Feedback");
			JacksonDBCollection<FeedbackMember, Object> coll = JacksonDBCollection.wrap(table, FeedbackMember.class,
					Object.class);
			DBObject query = new BasicDBObject("userEmail", userEmail);
			result = coll.find(query);
			if (result.hasNext()) {
				feedbackMember = result.next();
				result.close();
				return feedbackMember;
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
	public List<NavBarMember> readFeedbackStatus() throws Exception {

		DB db;
		List<NavBarMember> membersStatusList = new ArrayList<NavBarMember>();
		FeedbackMember feedbackMember = new FeedbackMember();
		DBCursor<FeedbackMember> result = null;
		MongoClient mongo = null;
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Feedback");
			JacksonDBCollection<FeedbackMember, Object> coll = JacksonDBCollection.wrap(table, FeedbackMember.class,
					Object.class);
			result = coll.find();
			while (result.hasNext()) {
				feedbackMember = result.next();
				NavBarMember navBarMember = new NavBarMember();
				navBarMember.setMemberId(feedbackMember.getUserId());
				navBarMember.setMemberName(feedbackMember.getUserName());
				navBarMember.setMemberEmail(feedbackMember.getUserEmail());
				navBarMember.setMemberImage(feedbackMember.getUserImage());
				navBarMember.setHasNewUpdates(feedbackMember.getHasNewUpdates());
				membersStatusList.add(navBarMember);
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
		return membersStatusList;
	}

	@SuppressWarnings({ "deprecation", "resource" })
	public boolean changeFeedbackStatus(String userEmail, boolean status) {
		DB db;
		FeedbackMember feedbackMember = new FeedbackMember();
		DBCursor<FeedbackMember> result = null;
		MongoClient mongo = null;
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Feedback");
			JacksonDBCollection<FeedbackMember, Object> coll = JacksonDBCollection.wrap(table, FeedbackMember.class,
					Object.class);
			DBObject query = new BasicDBObject("userEmail", userEmail);
			result = coll.find(query);
			if (result.hasNext()) {
				feedbackMember = result.next();
				feedbackMember.setHasNewUpdates(status);
				coll.update(DBQuery.is("userId", feedbackMember.getUserId()), feedbackMember);
			}
			result = coll.find().is("userId", feedbackMember.getUserId());
			if (result.hasNext()) {
				feedbackMember = result.next();
				result.close();
				if (feedbackMember.getHasNewUpdates() == status) {
					return true;
				} else {
					return false;
				}
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
		return false;
	}
}
