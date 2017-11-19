package models;

import play.db.jpa.Model;

public abstract class VoteMain extends Model {
    public void vote(String ip, String vote) { 
       	increaseVote(ip, vote);
    }

    abstract void increaseVote(String ip, String vote);
}