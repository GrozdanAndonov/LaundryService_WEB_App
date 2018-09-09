package com.example.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.WebInitializer;
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
	@RequestMapping(value="settingsAdmin", method = RequestMethod.GET)
	public String showSettings( HttpSession session) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		
		return "adminViews/adminSettings";
	}
	
	/**
	 * Secured if not admin
	 * 
	 * @param session
	 * @param attr
	 * @param req
	 * @return
	 */
	@RequestMapping(value="changeAdminData", method=RequestMethod.POST)
	public String changeAdminSettings(HttpSession session, RedirectAttributes attr, HttpServletRequest req) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		System.out.println(req.getAttribute("firstName"));
		System.out.println(req.getAttribute("lastName"));
		System.out.println(req.getAttribute("firstEmail"));
		System.out.println(req.getAttribute("firstTelNumber"));
		System.out.println(req.getAttribute("secondEmail"));
		System.out.println(req.getAttribute("secondTelNumber"));
		return "redirect:settingsAdmin";
	}
	
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
}
