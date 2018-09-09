package com.example.controller.admin;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.WebInitializer;
import com.example.controller.user.ProfileController;
import com.example.model.dao.UserDAO;
import com.example.model.pojo.User;
import com.example.util.LoggedValidator;

@Controller
public class AdminController {

	
	@Autowired
	UserDAO ud;
	
	
	
	/**
	 * Secured if not admin
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value="aboutAdminPage", method=RequestMethod.GET)
	public String showAboutAdminPage(HttpSession session) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		return "adminViews/aboutAdminPage";
	}
	
	
	/**
	 * Secured if not admin
	 * 
	 * @param idUser
	 * @param response
	 * @param session
	 */
	@RequestMapping(value="getAvatarsForUsers/adminId={userId}", method=RequestMethod.GET)
	public void getUserAvatarById(@PathVariable("userId") int idUser, HttpServletResponse response, HttpSession session) {
		if(LoggedValidator.checksIfAdminIsLogged(session)) {
			User user;
			String avatarUrl;
			String defaultUrl = WebInitializer.LOCATION+
					File.separator+"default-avatar"+File.separator+"default.jpg";
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
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value="uploadPictureForAdmin", method=RequestMethod.POST)
	public String uploading(@RequestParam("userAvatar") MultipartFile file, HttpServletRequest req, HttpSession session, Model model){
			User user = (User) session.getAttribute("User");
			String filePath = WebInitializer.LOCATION+File.separator + "users" + File.separator + user.getId()+"-"+user.getLastName()+
					File.separator+"avatar";
			
			String extension = ProfileController.getExtensionForFile(file);
			
			boolean isAllowed = ProfileController.checkIfExtensionForFileisAllowed(extension);
			
			if(!isAllowed) {
				model.addAttribute("errorFile", "Upload file with the givven extensions!");
				ProfileController.addAtributesInModel(user, model);
				return "adminViews/adminSettings";
			}
		
			File folders = new File(filePath);
			folders.mkdirs();
			String fullPathToFile = "";
			try {
				fullPathToFile = ProfileController.copyFile(user, file, filePath, extension, model);
			} catch (IllegalStateException | IOException e) {
				model.addAttribute("errorFile", "Sorry, try again later!");
				e.printStackTrace();
				ProfileController.addAtributesInModel(user, model);
				return "adminViews/adminSettings";
			}
			try {
				ud.insertAvatarUrl(user, fullPathToFile);
				user.setAvatarUrl(fullPathToFile);
				session.setAttribute("User", user);
			} catch (SQLException e) {
				ProfileController.addAtributesInModel(user, model);
				model.addAttribute("errorFile", "Sorry, try again later!");
				e.printStackTrace();
				return "adminViews/adminSettings";
			}
			
			model.addAttribute("successFile", "You have successfully uploaded your file.");
			ProfileController.addAtributesInModel(user, model);
			return "adminViews/adminSettings";
	
}
	
	@RequestMapping(value="getAvatarForAdmin", method=RequestMethod.GET)
	public void getUserAvatar(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
		if(LoggedValidator.checksIfAdminIsLogged(session)){
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
	}
	
}
