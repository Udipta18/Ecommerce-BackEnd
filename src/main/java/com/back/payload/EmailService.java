package com.back.payload;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class EmailService {
 
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendEmail(String toEmail, String body, String subject) {

		//SimpleMailMessage message = new SimpleMailMessage();
		try {
		MimeMessage createMimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(createMimeMessage);

		helper.setFrom("dasdsa1208@gmail.com");
		helper.setTo(toEmail);
		helper.setSubject(subject);
		helper.setText(body, true);
		

		javaMailSender.send(createMimeMessage);
		System.out.println("Mail Sent Successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
} 
