package com.qburst.Service;

import com.qburst.Model.Feedback;
import com.qburst.Model.UsersData;

public class FeedbackMailThread extends Thread {

	private SendEmailService mailService = new SendEmailService();

	private Feedback feedback = new Feedback();
	
	private UsersData user = new UsersData();

	public FeedbackMailThread(Feedback fb, UsersData user) {
		this.feedback = fb;
		this.user = user;
	}

	@Override
	public void run() {
		mailService.sendFeedbackNotificationEmail(this.feedback, this.user);
	}
}
