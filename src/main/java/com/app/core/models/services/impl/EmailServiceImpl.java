package com.app.core.models.services.impl;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.app.core.constants.Constans;
import com.app.core.models.entity.User;
import com.app.core.models.services.IEmailService;

@Service
public class EmailServiceImpl implements IEmailService {

	private JavaMailSender javaMailSender;
	
	private MailSender mailSender;
	private SimpleMailMessage templateMessage;

	@Autowired
	public EmailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	

	@Override
	public void sendEmail(User user) throws MailException {

		SimpleMailMessage mail = new SimpleMailMessage();
		//mail.setTo(user.getEmail());
		mail.setTo(user.getEmail());
		mail.setFrom(Constans.EMAIL);
		mail.setSubject("Testing Mail API");
		mail.setText("Hurray ! You have done that dude...");
		
		
		javaMailSender.send(mail);

	}

	@Override
	public void sendEmailWithAttachment(User user) throws MailException, MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

		helper.setTo(user.getEmail());
		helper.setSubject("Recuperar contraseña");
		helper.setText("Porfa, recupera tu contraseña.");
		helper.setSentDate(new Date());
				
		
		

		FileSystemResource file = new FileSystemResource("/home/rockhard/Desktop/Registration.pdf");
		helper.addAttachment(file.getFilename(), file);

		javaMailSender.send(mimeMessage);

	}

}
