package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Voter extends Model {
	public String ip;
	public Date date;
	
	@OneToOne
	public Poll poll;
	
	@OneToOne
	public Answer answer;
}
