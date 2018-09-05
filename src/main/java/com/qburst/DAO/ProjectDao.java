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
import com.qburst.Model.UsersData;

public class ProjectDao extends connection {
	
	/*
	 * Create database called Scrum and create a collection called Project.
	 */
	@SuppressWarnings("deprecation")
	public ProjectData insertProject(ProjectData projectData) throws Exception {
		DB db;
//		DBCursor<ProjectData> result = null;
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
			}
			System.out.println("Here3");
		    coll.insert(projectData);
			result = coll.find().is("projectId", projectData.getProjectId());
			if (result.hasNext()) {
				System.out.println("Here4");
				pdata = result.next();
				return pdata;
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public List<ProjectData> getProjects() throws Exception {
		DB db;
		List<ProjectData> projectlist = new ArrayList<ProjectData>();
		ProjectData pdata = new ProjectData();
		try {
			MongoClient mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Project");
			JacksonDBCollection<ProjectData, Object> coll = JacksonDBCollection.wrap(table, ProjectData.class,
			        Object.class);		
			DBCursor<ProjectData> result = coll.find();
			while (result.hasNext()) {
				pdata = result.next();			
				projectlist.add(pdata);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return projectlist;
	}

}
