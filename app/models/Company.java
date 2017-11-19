package models;

import java.util.ArrayList;

import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.sleepycat.persist.model.Entity;

import play.db.jpa.Model;


@Entity
public class Company extends Model{

	public String title;
	
	@Lob
	public String description;
	
	public String logo;
	
	@OneToMany
	ArrayList<User> users;
	
	public Company(String title, String description){
		this.title = title;
		this.description = description;
		users = new ArrayList<>();
	}
	
	public void setLogo(String logo){
		this.logo = logo;
	}
	
	public void addUser(User user){
		users.add(user);
	}
}
