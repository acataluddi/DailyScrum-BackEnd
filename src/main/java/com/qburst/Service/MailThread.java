package com.qburst.Service;

import com.qburst.Model.ProjectMember;
import com.qburst.Model.UsersData;

public class MailThread implements Runnable{
	
	ProjectMember member;
	String projectname;
	UsersData user;
	
	Thread mailthread;
	 private String mailthreadname;
	 MailThread(ProjectMember member, String projectname, UsersData user) {
		 this.member = member;
		 this.projectname = projectname;
		 this.user = user;
	 }
	
	@Override
	public void run() {
		 try {
			    Thread.sleep(500);
			   } catch (InterruptedException e) {
			    System.out.println("Thread has been interrupted");
			   }
		 
			SendEmailService mailService = new SendEmailService();

	  mailService.sendEmail(member, projectname, user);
	 }
	public void start() {
		  if (mailthread == null) {
		   mailthread = new Thread(this);
		   mailthread.start();
		  }
	}
}
