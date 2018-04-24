package com.example.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class MailSender {

	
	private String to = "grozdan96@gmail.com";
	private String from = "grozdan96@gmail.com";
	private String pass = "grozdan95";
	private Properties props;
	private Session session;
	private MimeMessage msg;
	
	MailSender(){
		this.props = new Properties();
		props.put("mail.smtp.host","smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port","465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		this.session = Session.getDefaultInstance(props,new javax.mail.Authenticator(){
			protected javax.mail.PasswordAuthentication getPasswordAuthentication(){
				return new javax.mail.PasswordAuthentication(from, pass);
			}
		});
	}
	
	public void sendMessage(String sender, String email, String text,String ip) throws AddressException, MessagingException{
		String txt = String.format("Sender:%s\nIP:%s\nEmail: %s\nMessage:%s", sender, ip, email, text);
		this.msg = new MimeMessage(this.session);
		msg.setFrom(new InternetAddress(from));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		msg.setSubject("SAIT-LAUNDRY-SERVICE");
		msg.setText(txt);
		Transport.send(msg);		
		
	}
}
