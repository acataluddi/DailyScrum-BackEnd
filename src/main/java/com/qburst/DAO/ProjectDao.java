package com.qburst.DAO;

import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.qburst.Model.ProjectData;

public class ProjectDao extends connection {
	
	/*
	 * Create database called Scrum and create a collection called Project.
	 */
	@SuppressWarnings("deprecation")
	public ProjectData insertProject(ProjectData projectData) throws Exception {
		DB db;
		DBCursor<ProjectData> result = null;
		ProjectData pdata = new ProjectData();
		try {
			MongoClient mongo = databaseConnection();
			db = mongo.getDB("Scrum");
			DBCollection table = db.getCollection("Project");
			JacksonDBCollection<ProjectData, Object> coll = JacksonDBCollection.wrap(table, ProjectData.class,
			        Object.class);
			result = coll.find().is("projectId", projectData.getProjectId());
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
		return null;
	}

}
