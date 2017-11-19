package controllers.main;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import models.Comment;
import models.News;
import models.VoterFactory;
import models.VoteMain;
import controllers.Application;
import controllers.Controller;

public class Index extends Controller{
	
	public static void index(){
		render();
	}
	
	public static void loginHandler(String email, String password){
		models.User user = models.User.find("email=? and password=?", email, password).first();
		if(user != null) {
			session.put("user", user.email);
			renderJSON(1);
		}
		else {
			renderJSON(0);
		}
	}
	
	public static void signupHandler(String name, String email, String password){
		models.User user = models.User.find("email=?", email).first();
		if(user != null) renderJSON(0);
		
		user = new models.User(name, 0, email);
		user.setPassword(password);
		user.save();
		session.put("user", user.email);
		renderJSON(1);
	}
	
	public static void subs(long menuId){
		models.Menu menu = models.Menu.findById(menuId);
		List<models.Menu> subs = (menu != null) ? menu.subs : null;
		render(subs);
	}
	
	public static void news(){
		List<models.News> newses = models.News.findAll();
		render(newses);
	}
	
	public static void getNewsPicture(long id) {
        models.News news = models.News.findById(id);
        response.setContentTypeIfNotSet(news.picture.type());
        renderBinary(news.picture.get());
    }
	
	public static void newsmore(long newsId){
		models.News news = models.News.findById(newsId);
		render(news);
	}
	
	public static void polls(long menuId){
		models.Menu menu = models.Menu.findById(menuId);
		List<models.Poll> polls = models.Poll.find("category=?", menu).fetch();
		render(polls);
	}
	
	public static void getBannerPicture(long id) {
        models.Banner banner = models.Banner.findById(id);
        response.setContentTypeIfNotSet(banner.image.type());
        renderBinary(banner.image.get());
    }
	
	public static void poll(long pollId){
		models.Poll poll = models.Poll.findById(pollId);
		List<models.Comment> comments = models.Comment.find("poll=?", poll).fetch();
		render(poll, comments);
	}
	
	public static void vote(long pollId, String vote) throws UnknownHostException{
		InetAddress addr = InetAddress.getLocalHost();
		String ip = addr.getHostAddress().toString();
		VoteMain vpoll = models.Poll.findById(pollId);
		vpoll.vote(ip, vote);
		models.Poll poll = models.Poll.findById(pollId);
		// for(models.Answer answer: poll.answers){
		// 	if(answer.name.equals(vote)){
				
		// 		InetAddress addr = InetAddress.getLocalHost();
		// 		String ip = addr.getHostAddress().toString();
		// 		VoterFactory.createVoter(ip, poll, answer);	
		// 		answer.vote++;
		// 		answer.save();
		// 	}
		// }
		renderJSON(poll);
	}
	
	public static void addComment(long pollId, String comment) throws UnknownHostException{
		InetAddress addr = InetAddress.getLocalHost();
		String ip = addr.getHostAddress().toString();
		models.Poll poll = models.Poll.findById(pollId);
		
		models.Comment comm = new models.Comment();
		comm.name = "anonymous";
		comm.ip = ip;
		comm.comment = comment;
		comm.poll = poll;
		comm.save();
		renderJSON(comm);
	}
}
