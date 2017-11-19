package controllers;

import play.*;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Finally;

import java.util.*;

import models.*;

public class Application extends Controller {
	protected static models.KeyStore config;
	protected static models.User user = null;
	
	@Before
	public static void startConfig() {
		config = new models.KeyStore();
		renderArgs.put("config", config);
	}
    public static void index() {
    	List<models.Menu> menus = models.Menu.find("isParent=?", true).fetch();
    	List<models.Banner> banners = models.Banner.findAll();
        render(menus, banners);
    }
    public static void login(){
		render();
	}
    public static void signup(){
    	render();
    }
    public static void logout(){
    	session.clear();
    	index();
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