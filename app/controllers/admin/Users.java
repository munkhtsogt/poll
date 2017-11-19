package controllers.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import controllers.Controller;

public class Users extends Controller {

	private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 25;
    private static final String[] PARTS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz".split("");
    
	public static void index(){
		if (session.contains("user")) {
            String email = session.get("user");
            if (email.equals("admin")) render();
		}
		else Index.index();;
	}
	public static void add(){
		render();
	}
	public static void addImpl(String name, int age, String email){
		models.User user = new models.User(name, age, email);
		user.setPassword(generateRandomString(6));
		user.save();
		if(user.email != null && !user.email.equals("")){
			models.Mail mail = new models.Mail();
			mail.to = user;
			mail.to.save();
			mail.subject = "Inserted!";
			mail.body = "Now you have Poll system's user!";
			mail.save();
		}
	}
	public static void edit(long userId){
		models.User user = models.User.findById(userId);
		render(user);
	}
	public static void editImpl(long userId, String name, String email){
		models.User user = models.User.findById(userId);
		user.name = name;
		user.email = email;
		user.save();
		if(user.email != null && !user.email.equals("")){
			models.Mail mail = new models.Mail();
			mail.to = user;
			mail.to.save();
			mail.subject = "Updated!";
			mail.body = "Poll system admin changed your user information!";
			mail.save();
		}
	}
	public static void delete(long userId){
		models.User user = models.User.findById(userId);
		user.delete();
	}
	public static void users(long[] userIds, Integer page_size, Integer page){
        List<models.User> users = new ArrayList<models.User>();
        Double num_pages = null;
        if(userIds == null || userIds.length == 0)
        {
                num_pages = Math.ceil(Double.parseDouble(Long.toString(models.User.count()))/
                                Double.parseDouble(page_size.toString()));
                page = page - 1;
                double from = page * Double.parseDouble(page_size.toString());
                int p = (int) from;
                users = models.User.find("isAdmin=? order by age asc", false).from(p).fetch(page_size);
        }       
        else {
                for(int i=0; i<userIds.length; i++) {
                        models.User user = models.User.find("id=?", userIds[i]).first();
                        users.add(user);
                }
                num_pages = Math.ceil(Double.parseDouble(Long.toString(users.size()))/
                                Double.parseDouble(page_size.toString()));
        }
        page = page + 1;
        render(users, num_pages, page);
	}
	private static String generateRandomString(int length) {
		 Random generator = new Random();
		 if (length == 0) {
		     length = MIN_LENGTH
		            + generator.nextInt(MAX_LENGTH - MIN_LENGTH);
		 }
		 String s = "";
		 for (int i = 0; i < length; i++) {
		     s += PARTS[generator.nextInt(PARTS.length - 1) + 1];
		 }
		 return s;
	}
	public static void search(String[] queries, String[] parameters){
        String sqlQuery = "";
        List<models.User> users = new ArrayList<models.User>();
        if(parameters != null)
        {
                for(int i=0; i<parameters.length; i++) {
                        if( i != parameters.length - 1)
                                sqlQuery +="lower("+ parameters[i]+ ") like lower('%" + queries[i] + "%') and ";
                        else 
                                sqlQuery +="lower("+ parameters[i]+ ") like lower('%" + queries[i] + "%')";
                }
                sqlQuery += " and lower(isAdmin) like lower('%false%')";
                users = models.User.find(sqlQuery).fetch();
        }
        else
                users = models.User.find("isAdmin=?", false).fetch();
        renderJSON(users);
	}
	public static void sorting(String parameter, Integer click){
	        List<models.User> users = null;
	        if(click % 2 == 0 ) {
	                users = models.User.find("isAdmin=? order by "+ parameter +" asc", false).fetch();
	        }
	        else {
	                users = models.User.find("isAdmin=? order by "+ parameter +" desc", false).fetch();
	        }
	        renderJSON(users);
	}
}
