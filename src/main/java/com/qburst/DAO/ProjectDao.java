package com.qburst.DAO;

import java.util.ArrayList;
import java.util.List;


import org.bson.Document;

import org.mongojack.DBCursor;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.qburst.Model.ProjectData;
import com.qburst.Model.UsersData;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.qburst.Model.ProjectData;

public class ProjectDao extends connection {

	/*
	 * Create database called Scrum and create a collection called Project.
	 */
	@SuppressWarnings("deprecation")
	public ProjectData insertProject(ProjectData projectData) throws Exception {
		DB db;

		ProjectData pdata = new ProjectData();
		try {
			MongoClient mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Project");
			JacksonDBCollection<ProjectData, Object> coll = JacksonDBCollection.wrap(table, ProjectData.class,

					Object.class);
			DBObject query = new BasicDBObject("projectId", projectData.getProjectId());
			DBCursor<ProjectData> result = coll.find(query);
			if (result.hasNext()) {
				pdata = result.next();
				return pdata;

			};
			coll.insert(projectData);
			result = coll.find().is("projectId", projectData.getProjectId());
			if (result.hasNext()) {
				pdata = result.next();
				return pdata;
			}
		} catch (Exception e) {
		}
		return null;
	}


	@SuppressWarnings("deprecation")
	public List<ProjectData> getProjects(String memberEmail) throws Exception {

		DB db;
		List<ProjectData> projectlist = new ArrayList<ProjectData>();
		ProjectData pdata = new ProjectData();
		try {
			MongoClient mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Project");
			JacksonDBCollection<ProjectData, Object> coll = JacksonDBCollection.wrap(table, ProjectData.class,

					Object.class);
			DBObject query = new BasicDBObject("members.email", memberEmail);
			memberEmail = memberEmail.trim();
			DBCursor<ProjectData> result;
			if(memberEmail.equals("getall")) { 
				result = coll.find();				
			}
			else {
				result = coll.find(query);				
			}
			while (result.hasNext()) {
				pdata = result.next();

				projectlist.add(pdata);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return projectlist;
	}


	@SuppressWarnings("deprecation")
	public boolean deleteProject(String incomingdata) throws Exception {
		DB db;
		com.mongodb.DBCursor result = null;
		try {

			MongoClient mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection collection = db.getCollection("Project");
//			String deletionProjectId = projectData.getProjectId();
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
		return true;
	}
	
	

	@SuppressWarnings("deprecation")
	public boolean updateProject(ProjectData projectData) throws Exception {
		DB db;
		try {
			MongoClient mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Project");
			JacksonDBCollection<ProjectData, Object> coll = JacksonDBCollection.wrap(table, ProjectData.class,
					Object.class);
			coll.update(DBQuery.is("projectId", projectData.getProjectId()), projectData);
		} catch (Exception e) {
			return false;
		}
		return true;
	}


}
