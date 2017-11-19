package controllers.tree;

import java.util.List;

import controllers.Controller;

public class Index extends Controller{

	public static void index(){
		render();
	}
	public static void list(){
		List<models.Menu> menus = models.Menu.find("isParent=?", true).fetch();
		render(menus);
	}
	public static void menuName(long menuId){
		models.Menu menu = models.Menu.findById(menuId);
		renderJSON(menu);
	}
}
