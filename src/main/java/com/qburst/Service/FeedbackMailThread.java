package com.qburst.Service;

import com.qburst.Model.Feedback;

public class FeedbackMailThread extends Thread {

	private SendEmailService mailService = new SendEmailService();

	private Feedback feedback = new Feedback();

	public FeedbackMailThread(Feedback fb) {
		this.feedback = fb;
	}

	@Override
	public void run() {
		mailService.sendFeedbackNotificationEmail(this.feedback);
	}
}
