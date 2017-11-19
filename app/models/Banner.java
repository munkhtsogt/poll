package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;

import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Banner extends Model {

	@Required
	public String name;
	
	public Blob image;
	
	public Date startDate;
	
	public Date endDate;
	
	@Lob
	public String description;
	
	public String link;
	
	public String toString(){
		return this.name;
	}
}
