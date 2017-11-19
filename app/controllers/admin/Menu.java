package controllers.admin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import controllers.Controller;

public class Menu extends Controller{
	
	public static void index(){
		if (session.contains("user")) {
            String email = session.get("user");
            if (email.equals("admin")) render();
		}
		else Index.index();
	}
	public static void list(){
		List<models.Menu> menus = models.Menu.find("isParent=?", true).fetch();
		render(menus);
	}
	public static void subs(long menuId){
		models.Menu menu = models.Menu.findById(menuId);
		List<models.Menu> subs = (menu != null) ? menu.subs : null;
		renderJSON(subs);
	}
	public static void add(long menuId){
		render(menuId);
	}
	public static void addImp(long menuId, String name){
		models.Menu menu = null;
		if(menuId == 0) {
			menu = new models.Menu(name);
			menu.isParent = true;
		}
		else {
			menu = models.Menu.findById(menuId);
			models.Menu m = new models.Menu(name);
			m.save();
			menu.subs.add(m);
		}
		menu.save();
	}
	public static void edit(long menuId){
		models.Menu menu = models.Menu.findById(menuId);
		render(menu);
	}
	public static void editImpl(long menuId, String name){
		models.Menu menu = models.Menu.findById(menuId);
		menu.name = name;
		menu.save();
	}
	public static void delete(long menuId){
		models.Menu parent = models.Menu.findById(menuId);
		List<models.Menu> menus = new ArrayList<models.Menu>();
		menus.add(parent);
		
		// DFS
		Queue<models.Menu> queue = new LinkedList<models.Menu>();
		queue.add(parent);
		while(!queue.isEmpty()){
			models.Menu node = queue.poll();
			if(node.subs.size() != 0){
				for(models.Menu sub: node.subs){
					queue.add(sub);
					menus.add(sub);
				}
			}
		}
		for(models.Menu menu: menus){
			menu.delete();
		}

	}
}
