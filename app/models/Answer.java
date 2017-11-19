package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
public class Answer extends Model{

	public String name;
	
	public Integer vote = 0;
	
	@OneToOne
	public Poll poll;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVote() {
		return vote;
	}

	public void setVote(Integer vote) {
		this.vote = vote;
	}

	public Answer(String name){
		this.name = name;
	}
	
	public void add(){
		this.vote ++;
	}

}
