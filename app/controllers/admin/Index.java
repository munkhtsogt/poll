package controllers.admin;

import controllers.Controller;

public class Index extends Controller{

	public static void index(){
		render();
	}
	public static void login(String email, String password){
		if(email.equals("admin")){
			models.User admin = models.User
					.find("email=? and password=? and isAdmin=?", email, password, true).first();
			if (admin == null) Index.index();		
			session.put("user", "admin");
			Panel.index();
		}
		else Index.index();
	}
	public static void logout(){
		session.clear();
		Index.index();
	}
}
