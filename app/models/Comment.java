package models;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Comment extends Model{

	@Required
	public String name;
	
	public String ip;
	
	@Lob
	public String comment;
	
	@OneToOne
	public Poll poll;
	
	public String toString(){
		return this.name;
	}
}
