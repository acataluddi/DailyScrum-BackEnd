package com.qburst.DAO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.qburst.Model.Goal;
import com.qburst.Model.GoalMember;
import com.qburst.Model.NavBarMember;
import com.qburst.Model.ProjectData;
import com.qburst.Model.ProjectMember;

public class GoalDao extends connection {

	@SuppressWarnings({ "resource", "deprecation" })
	public GoalMember insertGoal(GoalMember goalMember) throws Exception {
		DB db;
		GoalMember gMember = new GoalMember();
		DBCursor<GoalMember> result = null;
		MongoClient mongo = null;
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Goal");
			JacksonDBCollection<GoalMember, Object> coll = JacksonDBCollection.wrap(table, GoalMember.class,
					Object.class);
			DBObject query = new BasicDBObject("userId", goalMember.getUserId());
			result = coll.find(query);
			if (result.hasNext()) {
				coll.update(DBQuery.is("userId", goalMember.getUserId()), goalMember);
			} else {
				coll.insert(goalMember);
			}
			result = coll.find().is("userId", goalMember.getUserId());
			if (result.hasNext()) {
				gMember = result.next();
				result.close();
				return gMember;
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
	public GoalMember getIndividualMemberGoal(String userEmail) throws Exception {
		DB db;
		GoalMember goalMember = new GoalMember();
		DBCursor<GoalMember> result = null;
		MongoClient mongo = null;
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Goal");
			JacksonDBCollection<GoalMember, Object> coll = JacksonDBCollection.wrap(table, GoalMember.class,
					Object.class);
			DBObject query = new BasicDBObject("userEmail", userEmail);
			result = coll.find(query);
			if (result.hasNext()) {
				goalMember = result.next();
				result.close();
				return goalMember;
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
	public GoalMember updateGoalMember(GoalMember goalMember) {
		DB db;
		GoalMember gMember = new GoalMember();
		DBCursor<GoalMember> result = null;
		MongoClient mongo = null;
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Goal");
			JacksonDBCollection<GoalMember, Object> coll = JacksonDBCollection.wrap(table, GoalMember.class,
					Object.class);
			coll.update(DBQuery.is("userId", goalMember.getUserId()), goalMember);
			result = coll.find().is("userId", goalMember.getUserId());
			if (result.hasNext()) {
				gMember = result.next();
				result.close();
				return gMember;
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
		return gMember;
	}

	@SuppressWarnings("deprecation")
	public List<NavBarMember> readGoalStatusForUser(String userEmail) throws Exception {
		DB db;
		GoalMember goalMember = new GoalMember();
		DBCursor<GoalMember> result = null;
		MongoClient mongo = null;
		List<NavBarMember> membersStatusList = new ArrayList<NavBarMember>();
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Goal");
			JacksonDBCollection<GoalMember, Object> coll = JacksonDBCollection.wrap(table, GoalMember.class,
					Object.class);
			result = coll.find().is("userEmail", userEmail);
			if (result.hasNext()) {
				goalMember = result.next();
				result.close();
			}
			Goal[] goalsArray = goalMember.getGoals();
			for (Goal selectedGoal : goalsArray) {
				boolean memberPresent = false;
				boolean hasUpdatesForUser = selectedGoal.gethasNewUpdatesForUser();
				Iterator<NavBarMember> itr = membersStatusList.iterator();
				String managerEmail = selectedGoal.getManagerEmail();
				while (itr.hasNext()) {
					NavBarMember navMember = (NavBarMember) itr.next();
					if (navMember.getMemberEmail().equals(managerEmail)) {
						if (!navMember.getHasNewUpdates()) {
							navMember.setHasNewUpdates(hasUpdatesForUser);
							itr.remove();
							membersStatusList.add(navMember);
						}
						memberPresent = true;
						break;
					}
				}
				if (!memberPresent) {
					NavBarMember newNavBarMember = new NavBarMember();
					newNavBarMember.setHasNewUpdates(hasUpdatesForUser);
					newNavBarMember.setMemberEmail(selectedGoal.getManagerEmail());
					newNavBarMember.setMemberImage(selectedGoal.getManagerImage());
					newNavBarMember.setMemberName(selectedGoal.getManagerName());
					membersStatusList.add(newNavBarMember);
				}
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
		return membersStatusList;
	}

	@SuppressWarnings("deprecation")
	public String[] getMembersUnderManager(String userEmail) throws Exception {
		DB db;
		ProjectData pdata = new ProjectData();
		DBCursor<ProjectData> projectResult = null;
		MongoClient mongo = null;
		String membersArray[] = null;
		Set<String> projectMembers = new HashSet<String>();
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Project");
			JacksonDBCollection<ProjectData, Object> projectCollection = JacksonDBCollection.wrap(table,
					ProjectData.class, Object.class);
			DBObject query = new BasicDBObject("members.email", userEmail);
			userEmail = userEmail.trim();
			projectResult = projectCollection.find(query);
			while (projectResult.hasNext()) {
				pdata = projectResult.next();
				ProjectMember[] members = pdata.getMembers();
				for (ProjectMember selectedMember : members) {
					projectMembers.add(selectedMember.getemail());
				}
			}
			int n = projectMembers.size();
			membersArray = new String[n];
			membersArray = projectMembers.toArray(membersArray);
			return membersArray;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (projectResult != null && mongo != null) {
				projectResult.close();
				mongo.close();
			}
		}
		return null;
	}
}
