package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.dao.UserDAO;
import com.example.model.pojo.User;
import com.example.util.LoggedValidator;

@Controller
public class WelcomeControler {

	@Autowired
	UserDAO ud;
	
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public String welcome(Model viewModel, HttpSession session) {	
		if(LoggedValidator.checksIfUserIsLogged(session)) {
			return "indexNotLogged";
		}else {
				if(((User) session.getAttribute("User")).getIsAdmin()) {
					return "adminIndexPage";
				}
			return "indexLogged";
		}
	}
	
	@RequestMapping(value = "/aboutUs", method = RequestMethod.GET)
	public String aboutUs(Model viewModel, HttpSession session) {	
		return "aboutUs";
	}
	
	@RequestMapping(value = "/contactUs", method = RequestMethod.GET)
	public String contactUs(Model viewModel, HttpSession session) {	
		if(LoggedValidator.checksIfUserIsLogged(session)) {
			return "indexNotLogged";
		}
			return "contactWithAdmin";
	}
	
}
