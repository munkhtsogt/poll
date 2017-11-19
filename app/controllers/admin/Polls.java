package controllers.admin;

import java.util.ArrayList;
import java.util.List;

import play.modules.excel.RenderExcel;

import controllers.Controller;


public class Polls extends Controller{

	public static void index(){
		if (session.contains("user")) {
            String email = session.get("user");
            if (email.equals("admin")) render();
		}
		else Index.index();
	}
	public static void add(){
		render();
	}
	public static void addImpl(long menuId, String name, String description, String[] parameters){
		models.Poll poll = new models.Poll();
		models.Menu menu = models.Menu.findById(menuId);
		poll.category = menu;
		poll.category.save();
		poll.name = name;
		poll.description = description;
		ArrayList<models.Answer> answers = new ArrayList<>();
		for(int i=0; i<parameters.length; i++) {
			models.Answer answer = new models.Answer(parameters[i]);
			answer.save();
			answers.add(answer);
		}
		System.out.println(answers.size());
		poll.answers = answers;
		
		poll.save();
	}
	public static void detail(long pollId){
		models.Poll poll = models.Poll.findById(pollId);
		render(poll);
	}
	public static void edit(long pollId){
		models.Poll poll = models.Poll.findById(pollId);
		render(poll);
	}
	public static void editImpl(long pollId, long menuId, String name, String description, String[] parameters){
		models.Poll poll = models.Poll.findById(pollId);
		models.Menu menu = models.Menu.findById(menuId);
		poll.category = menu;
		poll.category.save();
		poll.name = name;
		poll.description = description;
		ArrayList<models.Answer> answers = new ArrayList<>();
		for(int i=0; i<parameters.length; i++) {
			if(i < poll.answers.size()) {
				poll.answers.get(i).name = parameters[i];
				poll.answers.get(i).save();
				answers.add(poll.answers.get(i));
			}
			else{
				models.Answer answer = new models.Answer(parameters[i]);
				answer.save();
				answers.add(answer);
			}
		}
		
		poll.answers = answers;
		poll.save();
	}
	public static void delete(long pollId){
		models.Poll poll = models.Poll.findById(pollId);
		poll.delete();
	}
	public static void polls(long[] pollIds, Integer page_size, Integer page){
        List<models.Poll> polls = new ArrayList<models.Poll>();
        Double num_pages = null;
        if(pollIds == null || pollIds.length == 0)
        {
                num_pages = Math.ceil(Double.parseDouble(Long.toString(models.Poll.count()))/
                                Double.parseDouble(page_size.toString()));
                page = page - 1;
                double from = page * Double.parseDouble(page_size.toString());
                int p = (int) from;
                polls = models.Poll.find("order by name asc").from(p).fetch(page_size);
        }       
        else {
                for(int i=0; i<pollIds.length; i++) {
                        models.Poll poll = models.Poll.find("id=?", pollIds[i]).first();
                        polls.add(poll);
                }
                num_pages = Math.ceil(Double.parseDouble(Long.toString(polls.size()))/
                                Double.parseDouble(page_size.toString()));
        }
        page = page + 1;
        render(polls, num_pages, page);
	}
	public static void search(String[] queries, String[] parameters){
        String sqlQuery = "";
        List<models.Poll> polls = new ArrayList<models.Poll>();
        if(parameters != null)
        {
                for(int i=0; i<parameters.length; i++) {
                        if( i != parameters.length - 1)
                                sqlQuery +="lower("+ parameters[i]+ ") like lower('%" + queries[i] + "%') and ";
                        else 
                                sqlQuery +="lower("+ parameters[i]+ ") like lower('%" + queries[i] + "%')";
                }
                polls = models.Poll.find(sqlQuery).fetch();
        }
        else
        	polls = models.Poll.findAll();
        renderJSON(polls);
	}
	public static void sorting(String parameter, Integer click){
	        List<models.Poll> polls = null;
	        if(click % 2 == 0 ) {
	        	polls = models.Poll.find("order by "+ parameter +" asc").fetch();
	        }
	        else {
	        	polls = models.Poll.find("order by "+ parameter +" desc").fetch();
	        }
	        renderJSON(polls);
	}
	public static void generate(){		
		request.format = "xls";
		List<models.Poll> polls = models.Poll.findAll();
		renderArgs.put(RenderExcel.RA_FILENAME, "polls.xls");
		render(polls);
	}
}
