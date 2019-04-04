package com.app.core.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.app.core.models.entity.User;

@Service
public class MailClient {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailContentBuilder mailContentBuilder;

	@Autowired
	public MailClient(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

//    public void prepareAndSend(String recipient, String message) {
//        //TODO implement
//    }

	public void prepareAndSend(User user, String token) {

		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(user.getEmail());
			messageHelper.setTo(user.getEmail());
			messageHelper.setSubject("Recuperacion");
			String content = mailContentBuilder.build(user, token);
			messageHelper.setText(content, true);
		};
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
