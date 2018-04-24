package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class SettingsController {

	@RequestMapping(value="/aboutUser")
	public String aboutUser(Model model, HttpSession session){
		
		model.addAttribute("numOrder",0);
		model.addAttribute("numDiscounts",0);
		model.addAttribute("numComments",0);
		
		return "aboutUser";
	}
	
	@RequestMapping(value="/settings")
	public String settings(Model model, HttpSession session){
		return "settingsUser";
	}
	
}
