package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.WebInitializer;
import com.example.model.dao.UserDAO;
import com.example.model.pojo.User;
import com.example.util.LoggedValidator;

@Component
@MultipartConfig
@Controller
public class ProfileController {

	@Autowired
	UserDAO ud;
	
   
	
@RequestMapping(value="uploadPicture", method=RequestMethod.POST)
	public String uploading(@RequestParam("failche") MultipartFile file, HttpServletRequest req, HttpSession session, Model m){
		if(LoggedValidator.checksIfUserIsLogged(session)){
			return "redirect:/";
		}
		
		User user = (User) session.getAttribute("User");
		
		String filePath =WebInitializer.LOCATION 
				 +File.separator+ "users"
				 +File.separator + user.getId()+"-"+user.getLastName()
				 +File.separator + "avatar";
		
		if(!file.isEmpty()) {
			MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
			MimeType type = null;
			String ext = "";
			try {
				type = allTypes.forName(file.getContentType());
			} catch (MimeTypeException e) {
				e.printStackTrace();
			}
			if(type != null){
			ext = type.getExtension(); 
			}
			
			String[] allowedExt = new String[] {".jpg", "jpeg", ".png", ".gif" };
			boolean isAllowed = false;
			
			for (String string : allowedExt) {
				if(ext.contains(string)){
					isAllowed = true;
				}
			}
			
			if(!isAllowed) {
				m.addAttribute("errorExtension", "You must upload a pic from one of the given extensions!");
				return "settingsUser";
			}
			
			 File folders = new File( filePath );
			    folders.mkdirs();
			
			    File f = new File( filePath 
						 + File.separator
						 + user.getId()+"."+user.getLastName() + ext);
		
			    	try {
						file.transferTo(f);
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
					}
			    try {
					ud.insertAvatarUrl(user, filePath);
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		m.addAttribute("successUpload", "You have successfully uploaded your file.");
		return "settingsUser";
	}
	
	
	
	
	
	
	/*@RequestMapping(value = "/avatar", method = RequestMethod.GET)
	public void displayAvatar(HttpSession s, HttpServletResponse resp, HttpServletRequest req ) {
		User u = (User) req.getSession().getAttribute("user");
		String avatarUrl = null;
		
			avatarUrl =LOCATION 
				 	  +File.separator + "users"
				 	  +File.separator + u.getUsername()
				 	  +File.separator + "avatar"
					  +File.separator + "avatar.jpg";
		
		if(avatarUrl == null) {
			avatarUrl = LOCATION 
					 	+File.separator + "Administration"
					 	+File.separator + "userDefaultPic"
					 	+File.separator + "default.jpg";
		}
		
		File file = new File(avatarUrl);
		
		try (OutputStream out = resp.getOutputStream()) {
		    Path path = file.toPath();
		    Files.copy(path, out);
		    out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
	
}
