package com.qburst.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qburst.Controller.IdTokenVerification;
import com.qburst.DAO.FeedbackDao;
import com.qburst.DAO.ScrumDao;
import com.qburst.Model.Feedback;
import com.qburst.Model.UsersData;

public class FeedbackService {

	public Feedback addFeedback(Feedback feedback, String token) throws Exception {
		UsersData user = new UsersData();
		Feedback newFeedback = new Feedback();
		IdTokenVerification id_verifier = new IdTokenVerification();
		FeedbackDao fdao = new FeedbackDao();
		ScrumDao sdao = new ScrumDao();
		Date date = new Date();
		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {
				user = sdao.getIndividualUser(user.getEmail());
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (user.getEmployeeID() != null) {
			try {
				if (feedback.getFeedbackDescription().trim().length() > 0) {
					feedback.setFeedbackId(String.valueOf(date.getTime()));
					feedback.setFeedbackDate(date);
					feedback.setUserId(user.getEmployeeID());
					feedback.setUserEmail(user.getEmail());
					feedback.setUserName(user.getName());
					feedback.setUserImage(user.getImageurl());
					newFeedback = fdao.insertFeedback(feedback);
					FeedbackMailThread fbThread = new FeedbackMailThread(newFeedback);
					fbThread.start();
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return newFeedback;
	}

	public List<Feedback> getFeedbacks(String token) {
		UsersData user = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		FeedbackDao fdao = new FeedbackDao();
		ScrumDao sdao = new ScrumDao();
		List<Feedback> feedbackList = new ArrayList<Feedback>();
		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {
				user = sdao.getIndividualUser(user.getEmail());
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (user.getUserType().equals("Admin")) {
			try {
				feedbackList = fdao.readFeedbacks();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return feedbackList;
	}
}
