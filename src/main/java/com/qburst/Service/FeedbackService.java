package com.qburst.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qburst.Controller.IdTokenVerification;
import com.qburst.DAO.FeedbackDao;
import com.qburst.DAO.ScrumDao;
import com.qburst.Model.Feedback;
import com.qburst.Model.FeedbackMember;
import com.qburst.Model.NavBarMember;
import com.qburst.Model.UsersData;

public class FeedbackService {

	public Feedback addFeedback(Feedback feedback, String token) throws Exception {
		UsersData user = new UsersData();
		Feedback newFeedback = new Feedback();
		FeedbackMember feedbackMember = new FeedbackMember();
		FeedbackMember fMember = new FeedbackMember();
		IdTokenVerification id_verifier = new IdTokenVerification();
		FeedbackDao fdao = new FeedbackDao();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = formatter.format(date);
		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {
				feedback.setFeedbackId(String.valueOf(date.getTime()));
				feedback.setFeedbackDate(strDate);
				feedbackMember = fdao.getIndividualMemberFeedback(user.getEmail());
				if (feedbackMember != null) {
					Feedback[] previousFeedbacksArray = feedbackMember.getFeedbacks();
					Feedback[] newFeedbackArray = new Feedback[previousFeedbacksArray.length + 1];
					System.arraycopy(previousFeedbacksArray, 0, newFeedbackArray, 0, previousFeedbacksArray.length);
					newFeedbackArray[previousFeedbacksArray.length] = feedback;
					feedbackMember.setFeedbacks(newFeedbackArray);
				} else {
					FeedbackMember newMember = new FeedbackMember();
					newMember.setUserId(user.getEmployeeID());
					newMember.setUserName(user.getName());
					newMember.setUserEmail(user.getEmail());
					newMember.setUserImage(user.getImageurl());
					Feedback[] feedbackArray = new Feedback[] { feedback };
					newMember.setFeedbacks(feedbackArray);
					feedbackMember = newMember;
				}
				feedbackMember.setHasNewUpdates(true);
				feedbackMember.setLastUpdate(strDate);
				fMember = fdao.insertFeedback(feedbackMember);
				Feedback[] newFeedbackArray = fMember.getFeedbacks();
				//retrieving the last feedback to check whether the insertion was successfull
				newFeedback = newFeedbackArray[newFeedbackArray.length - 1];
				FeedbackMailThread fbThread = new FeedbackMailThread(newFeedback, user);
				fbThread.start();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return newFeedback;
	}

	public FeedbackMember getFeedbackMember(String token, String userEmail) {
		UsersData user = new UsersData();
		FeedbackMember feedbackmember = new FeedbackMember();
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
				feedbackmember = fdao.getIndividualMemberFeedback(userEmail);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return feedbackmember;
	}

	public List<NavBarMember> getFeedbackStatus(String token) {
		UsersData user = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		FeedbackDao fdao = new FeedbackDao();
		ScrumDao sdao = new ScrumDao();
		List<NavBarMember> membersStatusList = new ArrayList<NavBarMember>();
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
				membersStatusList = fdao.readFeedbackStatus();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return membersStatusList;
	}

	public boolean updateFeedbackStatus(String token, String updateEmail) {
		UsersData user = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		FeedbackDao fdao = new FeedbackDao();
		ScrumDao sdao = new ScrumDao();
		boolean isUpdated = false;
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
				isUpdated = fdao.changeFeedbackStatus(updateEmail, false);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return isUpdated;
	}
}
