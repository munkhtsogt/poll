package controllers.admin;

import java.io.File;

import javax.activation.MimetypesFileTypeMap;

import play.i18n.Lang;

import controllers.Controller;

public class Company extends Controller {

	public static void index() {
		if (session.contains("user")) {
            String email = session.get("user");
            if (email.equals("admin")) render();
		}
		else Index.index();
	}
	
	public static void update(String companyName, String language, 
			File companyLogo) {

		models.KeyStore config = new models.KeyStore();
		
		if (companyLogo != null) {
			String mime = new MimetypesFileTypeMap()
				.getContentType(companyLogo);
			if (mime.equals("image/jpeg") || mime.equals("image/pjpeg") ||
					mime.equals("image/gif") ||
					mime.equals("image/png")) {
				if (companyLogo.length() < 2*1024*1024) {
					companyLogo.renameTo(new File("public/images/logos", 
							companyLogo.getName()));
					config.put("companyLogo", companyLogo.getName());
				}
			}
		}
		
		
		config.put("companyName", companyName);
		config.put("language", language); // Setting-up application language
		config.commit();
		config.close();
		
		Lang.change(language);
		
		index();
		
	}

}