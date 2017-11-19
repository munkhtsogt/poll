package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Poll extends VoteMain {

	@OneToOne
	public Menu category;
	
	public String name;
	
	@Lob
	public String description;
	
	@OneToMany
	public List<Answer> answers = new ArrayList<>();
	
	@OneToOne
	public User user;
	
	public Date startDate = new Date();
	
	public Date endDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 10));

    void increaseVote(String ip, String vote) {
		for(Answer answer: this.answers){
			if(answer.name.equals(vote)){
				VoterFactory.createVoter(ip, this, answer);	
				answer.add();
				answer.save();
			}
		}
    }
}
