package controllers.mail;

import java.util.Date;

import play.mvc.Mailer;

import models.User;

public class Mail extends Mailer{

	public static void general(User user, Date date, String subject, String body){
		setSubject("Hello %s", user.email);
        addRecipient(user.email);
        setFrom("Num <info@alchemist.mn>");
        send(user, date, subject, body);
	}
	
}
