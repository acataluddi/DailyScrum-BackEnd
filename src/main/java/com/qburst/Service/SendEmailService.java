package com.qburst.Service;

import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.qburst.Model.ProjectMemberModel;

public class SendEmailService {
	public void sendEmail(ProjectMemberModel member, String projectName, String assignee) {
		InputStream input = null;
		Properties property = new Properties();
		try {
			input = SendEmailService.class.getClassLoader().getResourceAsStream("mailAuth.properties");
			property.load(input);
			String host = property.getProperty("Mail_Host");
			String socketport = property.getProperty("SocketFactory_PORT");
			String socketclass = property.getProperty("SocketFactory_CLASS");
			String authstatus = property.getProperty("SMTP_AUTH");
			String port = property.getProperty("SMTP_PORT");
			String fromID = property.getProperty("Email_ID");
			String password = property.getProperty("Password");
			String CC = property.getProperty("CC");
			String loginurl = property.getProperty("Login_URL");

			Properties props = new Properties();

			props.put("mail.smtp.host", host);
			props.put("mail.smtp.socketFactory.port", socketport);
			props.put("mail.smtp.socketFactory.class", socketclass);
			props.put("mail.smtp.auth", authstatus);
			props.put("mail.smtp.port", port);

			String to = member.getemail();
			String name = member.getname();

			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromID, password);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromID));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CC));
			message.setSubject("Allocation Update");
			message.setText("Hi " + name + "," + "\n\nYou have been allocated to  " + projectName + " project as "
					+ member.getrole() + " by " + assignee + ". \n\nIf you have any questions/concerns, "
					+ "please feel free to talk to your reporting manager. "
					+ "\n\nPlease start adding your tasks by logging into " + loginurl + "\n\nThanks,\nDaily Scrum");
			Transport.send(message);

			System.out.println("Done");

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}