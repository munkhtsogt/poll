package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import play.jobs.Every;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
@Every("2min")
public class Bootstrap extends Job {
	
    public void doJob() {
    	
        if(models.User.count() == 0) {
        	models.User admin = new models.User("admin", 0, "admin");
        	admin.setPassword("admin");
        	admin.isAdmin = true;
        	admin.save();
        }
        List<models.Mail> mails = models.Mail.find("sent=?", false).fetch();
        if(mails.size() != 0) {
            Date today = new Date();
            for(models.Mail mail: mails){
                if(getDate(today).equals(getDate(mail.willSendDate))){
                        controllers.mail.Mail.general(mail.to, mail.date, mail.subject, mail.body);
                        mail.sent = true;
                        mail.save();
                }
            }
        }
    }
    public String getDate(Date date){
        if(date != null){
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                return dateFormat.format(date).toString();
        }
        else return "";
    }
}
