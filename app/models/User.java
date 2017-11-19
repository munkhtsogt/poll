package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import controllers.admin.Company;
import net.sf.oval.constraint.Email;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name = "`USER`")
public class User extends Model{
	
	public Integer age = 0;
	
	public String name;
	
	@Email
	public String email;
	
	private String password;
	
	public Boolean isAdmin = false;
	
	public User(String name, Integer age, String email){
		this.name = name;
		this.age = age;
		this.email = email;
	}
	
	public void setPassword(String pwd){
		password = pwd;
	}
	
	public String getPassword(){
		return password;
	}
}
