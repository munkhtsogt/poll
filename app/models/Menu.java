package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Menu extends Model{

	public String name;
	
	@OneToMany
	public List<Menu> subs;
	
	public Boolean isParent;
	
	public Menu(String name){
		this.name = name;
		this.isParent = false;
		this.subs = new ArrayList<Menu>();
	}
}
