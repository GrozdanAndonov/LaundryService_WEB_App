package com.example.controller;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.util.LoggedValidator;

@Controller
public class WelcomeControler {

	
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public String welcome(Model viewModel, HttpSession session) {	
		if(LoggedValidator.checksIfUserIsLogged(session)) {
			return "indexNotLogged";
		}else {
			return "indexLogged";
		}
	}
	
	@RequestMapping(value = "/aboutUs", method = RequestMethod.GET)
	public String aboutUs(Model viewModel, HttpSession session) {	
		return "aboutUs";
	}
	
}
