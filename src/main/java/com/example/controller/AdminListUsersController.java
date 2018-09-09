package com.example.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.dao.UserDAO;
import com.example.util.LoggedValidator;

@Controller
public class AdminListUsersController {

	@Autowired
	UserDAO ud;
	
	/**
	 * Secured if not admin
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/showUsers", method = RequestMethod.GET)
	public String aboutUs(Model model, HttpSession session) {	
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		
		try {
			model.addAttribute("users", ud.findAllUsersForAdminList());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "adminViews/adminListUsers";
	}
}
