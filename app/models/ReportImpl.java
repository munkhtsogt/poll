package models;

import java.util.List;

public class ReportImpl implements Report{

	private String type;
	
	public ReportImpl(String type){
		this.type = type;
	}
	
	@Override
	public int getTotalVoters() {
		
		int totalVoters = 0;
		if(type.equals("answer")){
			List<Answer> answers = Answer.findAll();
			for(Answer answer: answers){
				totalVoters += answer.vote;
			}
		}
		else {
			totalVoters = (int) Voter.count();
		}
		return totalVoters;
	}

	@Override
	public int numberOfPolls() {
		return (int) Poll.count();
	}

	@Override
	public int numberOfComments() {
		return (int) Comment.count();
	}

}
