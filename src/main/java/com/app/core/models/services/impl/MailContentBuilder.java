package com.app.core.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.app.core.constants.Constans;
import com.app.core.models.entity.User;

@Service
public class MailContentBuilder {

	private TemplateEngine templateEngine;

	@Autowired
	public MailContentBuilder(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public String build(User user, String token) {
		Context context = new Context();
		context.setVariable("firstName", user.getFirstName());
		context.setVariable("lastName", user.getLastName());
		context.setVariable("urlRecovery", Constans.URL_RECOVERY + token + "/recovery");

		String template = templateEngine.process("email/index.html", context);
		return template;
	}
}
