package com.qburst.Service;

import java.text.SimpleDateFormat;
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
		Feedback fb = new Feedback();
		IdTokenVerification id_verifier = new IdTokenVerification();
		FeedbackDao fdao = new FeedbackDao();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = formatter.format(date);

		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {
				feedback.setFeedbackId(String.valueOf(date.getTime()));
				feedback.setUserName(user.getName());
				feedback.setUserEmail(user.getEmail());
				feedback.setFeedbackDate(strDate);
				fb = fdao.insertFeedback(feedback);
				FeedbackMailThread fbThread = new FeedbackMailThread(feedback);
				fbThread.start();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return fb;
	}

	public List<Feedback> getFeedbacks(int pagenum, int numOfRec, String token) {
		List<Feedback> feedbackList = new ArrayList<Feedback>();
		UsersData user = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		FeedbackDao fdao = new FeedbackDao();
		ScrumDao sdao = new ScrumDao();
		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {
				user = sdao.insertIntoTable(user);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (user.getUserType().equals("Admin")) {
			try {
				feedbackList = fdao.readFeedbacks(pagenum, numOfRec);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return feedbackList;
	}
}
