package com.app.core.models.services;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.mail.MailException;

import com.app.core.models.entity.User;


public interface IEmailService {

	public void sendEmail(User user) throws MailException;

	public void sendEmailWithAttachment(User user) throws MailException, MessagingException, IOException;
}
