package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;

import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class News extends Model {

	public String title;
	
	public Blob picture;
	
	public String link;
	
	public Date date = new Date();
	
	@Lob
	public String description;
	
	public String getDate(Date date){
        if(date != null){
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                return dateFormat.format(date).toString();
        }
        else return "";
	}
}
