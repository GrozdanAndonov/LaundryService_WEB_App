package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String uploading(@RequestParam("userAvatar") MultipartFile file, HttpServletRequest req, HttpSession session, Model model){
	
			if(LoggedValidator.checksIfUserIsLogged(session)){
			return "indexNotLogged";
			}
			
			User user = (User) session.getAttribute("User");
			String filePath = WebInitializer.LOCATION+File.separator + "users" + File.separator + user.getId()+"-"+user.getLastName()+
					File.separator+"avatar";
			
			String extension = this.getExtensionForFile(file);
			
			boolean isAllowed = this.checkIfExtensionForFileisAllowed(extension);
			
			if(!isAllowed) {
				model.addAttribute("errorFile", "Upload file with the givven extensions!");
				addAtributesInModel(user, model);
				return "settingsUser";
			}
		
			File folders = new File(filePath);
			folders.mkdirs();
			String fullPathToFile = "";
			try {
				fullPathToFile = this.copyFile(user, file, filePath, extension, model);
			} catch (IllegalStateException | IOException e) {
				model.addAttribute("errorFile", "Sorry, try again later!");
				e.printStackTrace();
				addAtributesInModel(user, model);
				return "settingsUser";
			}
			try {
				ud.insertAvatarUrl(user, fullPathToFile);
				user.setAvatarUrl(fullPathToFile);
				session.setAttribute("User", user);
			} catch (SQLException e) {
				addAtributesInModel(user, model);
				model.addAttribute("errorFile", "Sorry, try again later!");
				e.printStackTrace();
				return "settingsUser";
			}
			
			model.addAttribute("successFile", "You have successfully uploaded your file.");
			 addAtributesInModel(user, model);
			return "settingsUser";
	
}

	private String copyFile(User user, MultipartFile file, String filePath, String extension, Model model) throws IllegalStateException, IOException {
		String fullPathToFile = filePath+File.separator+user.getFirstName()+"-"+user.getLastName()+extension;
		File f = new File(fullPathToFile);
			file.transferTo(f);
			return fullPathToFile;
	}

	private boolean checkIfExtensionForFileisAllowed(String extension) {
		boolean isAllowedExtension = false;
		String[] allowedExt = new String[] {".jpg", ".jpeg", ".png" };
		
		for (String ext : allowedExt) {
			if(ext.equals(extension)) {
				isAllowedExtension = true;
			}
		}
		
		return isAllowedExtension;
	}
	
	private String getExtensionForFile(MultipartFile file) {
		MimeType type = null;
		try {
			MimeTypes types = MimeTypes.getDefaultMimeTypes();
			 type = types.forName(file.getContentType());
		} catch (MimeTypeException e) {
			e.printStackTrace();
		}
			return type.getExtension();
	}

	private void addAtributesInModel(User user, Model model) {
		model.addAttribute("firstName", user.getFirstName());
		model.addAttribute("lastName",user.getLastName());
		model.addAttribute("email",user.getEmail());
		model.addAttribute("streetAddress", user.getStreetAddress());
		model.addAttribute("city", user.getCity());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		model.addAttribute("dateOfCreation",dateFormat.format(user.getDateCreated()));
		model.addAttribute("zip", user.getZipCode());
	}
	
	
	@RequestMapping(value="getAvatar", method=RequestMethod.GET)
	public void getUserAvatar(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
		User user = (User) session.getAttribute("User");
		String defaultUrl = WebInitializer.LOCATION+
				File.separator+"default-avatar"+File.separator+"default.jpg";
		
		String avatarUrl = user.getAvatarUrl();

		if(avatarUrl == null || avatarUrl.isEmpty()) {
			avatarUrl = defaultUrl;
		}
		
		File file = new File(avatarUrl);
		try {
			OutputStream out = response.getOutputStream();
			Path path = file.toPath();
		    Files.copy(path, out);
		    out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="getAvatarsForUsers/adminId={userId}", method=RequestMethod.GET)
	public void getUserAvatarById(@PathVariable("userId") int idUser, HttpServletResponse response) {
		User user;
		String avatarUrl;
		String defaultUrl = WebInitializer.LOCATION+
				File.separator+"default-avatar"+File.separator+"default.jpg";
		System.out.println(idUser);
		try {
			user = ud.getUserById(idUser);
			 avatarUrl = user.getAvatarUrl();
			 if(avatarUrl == null || avatarUrl.isEmpty()) {
					avatarUrl = defaultUrl;
				}
			 File file = new File(avatarUrl);
			 OutputStream out = response.getOutputStream();
				Path path = file.toPath();
			    Files.copy(path, out);
			    out.flush();
			    System.out.println(avatarUrl);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		
	
		
		
		
	}
	

	
}


