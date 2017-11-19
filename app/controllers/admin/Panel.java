package controllers.admin;

import controllers.Controller;


public class Panel extends Controller {

	public static void index(){
		if (session.contains("user")) {
            String email = session.get("user");
            if (email.equals("admin")) render();
		}
		else Index.index();
	}
}
