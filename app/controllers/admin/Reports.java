package controllers.admin;

import java.util.HashMap;
import java.util.Map;

import controllers.Controller;

public class Reports extends Controller {

	public static void index(){
		render();
	}
	
	public static void report(String type){
		models.Report report = models.ReportFactory.createReport(type);
		
		int totalVoters = report.getTotalVoters();
		int numberOfPolls = report.numberOfComments();
		int numberOfComments = report.numberOfComments();
		
		Map<String, Integer> dic = new HashMap<>();
		
		dic.put("totalVoters", totalVoters);
		dic.put("numberOfPolls", numberOfPolls);
		dic.put("numberOfComments", numberOfComments);
		
		renderJSON(dic);
	}
}
