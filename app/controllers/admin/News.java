package controllers.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.db.jpa.Blob;
import play.libs.MimeTypes;

import controllers.Controller;

public class News extends Controller {

	public static void index(){
		if (session.contains("user")) {
            String email = session.get("user");
            if (email.equals("admin")) render();
		}
		else Index.index();
	}
	public static void list(long[] newsIds, Integer page_size, Integer page){
        List<models.News> newses = new ArrayList<models.News>();
        Double num_pages = null;
        if(newsIds == null || newsIds.length == 0)
        {
                num_pages = Math.ceil(Double.parseDouble(Long.toString(models.News.count()))/
                                Double.parseDouble(page_size.toString()));
                page = page - 1;
                double from = page * Double.parseDouble(page_size.toString());
                int p = (int) from;
                newses = models.News.find("order by title asc").from(p).fetch(page_size);
        }       
        else {
                for(int i=0; i<newsIds.length; i++) {
                        models.News News = models.News.find("id=?", newsIds[i]).first();
                        newses.add(News);
                }
                num_pages = Math.ceil(Double.parseDouble(Long.toString(newses.size()))/
                                Double.parseDouble(page_size.toString()));
        }
        page = page + 1;
        render(newses, num_pages, page);
	}
	public static void add(){
		render();
	}
	public static void addImpl(String title, Date date, File picture, String link, String description) 
			throws FileNotFoundException{
		models.News news = new models.News();
		
		news.title = title;
		news.picture = new Blob();
		if(picture != null)
			news.picture.set(new FileInputStream(picture), MimeTypes.getContentType(title));
		news.date = date;
		news.link = link;
		news.description = description;
		news.save();
		index();
	}
	public static void edit(long newsId){
		models.News news = models.News.findById(newsId);
		render(news);
	}
	public static void editImpl(long newsId, String title, Date date, File picture, String link, String description) 
			throws FileNotFoundException{
		models.News news = models.News.findById(newsId);
		if(news.picture != null) {
			news.picture.getFile().delete();
		}
		news.title = title;
		news.picture = new Blob();
		news.picture.set(new FileInputStream(picture), MimeTypes.getContentType(title));
		news.date = date;
		news.link = link;
		news.description = description;
		news.save();
		index();
	}
	public static void delete(long newsId){
		models.News news = models.News.findById(newsId);
		news.picture.getFile().delete();
		news.delete();
	}
	public static void getPicture(long id) {
        models.News news = models.News.findById(id);
        response.setContentTypeIfNotSet(news.picture.type());
        renderBinary(news.picture.get());
    }
	public static void search(String[] queries, String[] parameters){
        String sqlQuery = "";
        List<models.News> newses = new ArrayList<models.News>();
        if(parameters != null)
        {
                for(int i=0; i<parameters.length; i++) {
                        if( i != parameters.length - 1)
                                sqlQuery +="lower("+ parameters[i]+ ") like lower('%" + queries[i] + "%') and ";
                        else 
                                sqlQuery +="lower("+ parameters[i]+ ") like lower('%" + queries[i] + "%')";
                }
                newses = models.News.find(sqlQuery).fetch();
        }
        else
        	newses = models.News.findAll();
        renderJSON(newses);
	}
	public static void sorting(String parameter, Integer click){
	        List<models.News> newses = null;
	        if(click % 2 == 0 ) {
	        	newses = models.News.find("order by "+ parameter +" desc").fetch();
	        }
	        else {
	        	newses = models.News.find("order by "+ parameter +" asc").fetch();
	        }
	        renderJSON(newses);
	}
}
