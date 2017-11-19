package controllers;

import controllers.admin.Index;
import controllers.admin.Panel;
import play.mvc.Before;

public class Controller extends play.mvc.Controller {
	
	protected static models.User user = null;
	protected static models.KeyStore config;
	
	@Before
	public static void startConfig() {
		config = new models.KeyStore();
		renderArgs.put("config", config);
	}

	@Before
	public static void defineUser() {
		if (session.contains("user")) {
			String email = session.get("user");
			if (email.equals("admin")) {
				user = models.User.find("email=? and isAdmin=?", "admin", true).first();
				renderArgs.put("admin", user);
			} 
			else {
				user = models.User.find("email=?", email).first();
				renderArgs.put("user", user);
			}
		}
	}
}
