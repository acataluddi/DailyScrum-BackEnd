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
import com.qburst.Model.ProjectData;

public class ProjectDao extends connection {

	/*
	 * Create database called Scrum and create a collection called Project.
	 */
	@SuppressWarnings("deprecation")
	public ProjectData insertProject(ProjectData projectData) throws Exception {
		DB db;

		ProjectData pdata = new ProjectData();
		DBCursor<ProjectData> result = null;
		MongoClient mongo;
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Project");
			JacksonDBCollection<ProjectData, Object> coll = JacksonDBCollection.wrap(table, ProjectData.class,

					Object.class);
			DBObject query = new BasicDBObject("projectId", projectData.getProjectId());
			result = coll.find(query);
			if (result.hasNext()) {
				pdata = result.next();
				return pdata;
			}
			coll.insert(projectData);
			result = coll.find().is("projectId", projectData.getProjectId());
			if (result.hasNext()) {
				pdata = result.next();
				return pdata;
			}
		} catch (Exception e) {
		}
		finally{
			if(result!=null
//					&& mongo!=null
			) {
			result.close();
//			mongo.close();
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public List<ProjectData> getProjects(String memberEmail) throws Exception {

		DB db;
		List<ProjectData> projectlist = new ArrayList<ProjectData>();
		ProjectData pdata = new ProjectData();
		DBCursor<ProjectData> result = null;
		MongoClient mongo;
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Project");
			JacksonDBCollection<ProjectData, Object> coll = JacksonDBCollection.wrap(table, ProjectData.class,

					Object.class);
			DBObject query = new BasicDBObject("members.email", memberEmail);
			memberEmail = memberEmail.trim();
			
			if (memberEmail.equals("getall")) {
				result = coll.find();
			} else {
				result = coll.find(query);
			}
			while (result.hasNext()) {
				pdata = result.next();

				projectlist.add(pdata);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(result!=null
//					&& mongo!=null
			) {
			result.close();
//			mongo.close();
			}
		}
		return projectlist;
	}

	@SuppressWarnings("deprecation")
	public boolean deleteProject(String incomingdata) throws Exception {
		DB db;
		com.mongodb.DBCursor result = null;
		MongoClient mongo;
		try {

			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection collection = db.getCollection("Project");
			// String deletionProjectId = projectData.getProjectId();
			BasicDBObject deleteQuery = new BasicDBObject();
			System.out.println("ScrumDAO");
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(new BasicDBObject("projectId", incomingdata));
			deleteQuery.put("$and", obj);
			collection.remove(deleteQuery);
			System.out.println("Project Deleted");
			result = collection.find(deleteQuery);
			while (result.hasNext()) {
				return false;
			}
		} catch (Exception e) {
		}
		finally{
			if(result!=null
//					&& mongo!=null
			) {
			result.close();
//			mongo.close();
			}
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public boolean updateProject(ProjectData projectData) throws Exception {
		DB db;
		MongoClient mongo;
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Project");
			JacksonDBCollection<ProjectData, Object> coll = JacksonDBCollection.wrap(table, ProjectData.class,
					Object.class);
			coll.update(DBQuery.is("projectId", projectData.getProjectId()), projectData);
		} catch (Exception e) {
			return false;
		}
//		finally{
//			if(mongo!=null) {
//			mongo.close();
//			}
//		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public ProjectData getIndividualProject(String projectId) throws Exception {

		DB db;
		ProjectData pdata = new ProjectData();
		DBCursor<ProjectData> result = null;
		MongoClient mongo;
		try {
			mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Project");
			JacksonDBCollection<ProjectData, Object> coll = JacksonDBCollection.wrap(table, ProjectData.class,
					Object.class);
			DBObject query = new BasicDBObject("projectId", projectId);
			
			result = coll.find(query);
			while (result.hasNext()) {
				pdata = result.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(result!=null
//					&& mongo!=null
			) {
			result.close();
//			mongo.close();
			}
		}
		return pdata;
	}

}
