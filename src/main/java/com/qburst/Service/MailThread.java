package com.qburst.Service;

import com.qburst.Model.ProjectMemberModel;

public class MailThread implements Runnable{
	
	ProjectMemberModel member;
	String projectname;
	String name;
	
	Thread mailthread;
	 private String mailthreadname;
	 MailThread(ProjectMemberModel member, String projectname, String name) {
		 this.member = member;
		 this.projectname = projectname;
		 this.mailthreadname = name;
	 }
	
	@Override
	public void run() {
		 try {
			    Thread.sleep(500);
			   } catch (InterruptedException e) {
			    System.out.println("Thread has been interrupted");
			   }
		 
			SendEmailService mailService = new SendEmailService();

	  mailService.sendEmail(member, projectname, mailthreadname);
	 }
	public void start() {
		  if (mailthread == null) {
		   mailthread = new Thread(this, mailthreadname);
		   mailthread.start();
		  }
	}
}
