package models;

import java.util.Date;

final public class VoterFactory {
	private VoterFactory(){}
	public static void createVoter(String ip, Poll poll, Answer answer){
		models.Voter voter = models.Voter.find("ip=? and poll=? and answer=?", 
				ip, poll, answer).first();

			if(voter == null) {
			answer.vote += 1;
			answer.save();
			voter = new models.Voter();
			voter.ip = ip;
			voter.poll = poll;
			voter.answer = answer;
			voter.date = new Date();
			voter.save();
		}
	}
}
