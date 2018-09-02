package com.example.controller;

import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.dao.UserDAO;
import com.example.model.pojo.User;


@Controller
public class LoginController {

	@Autowired
	UserDAO ud;
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession s) {
		s.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value = "/logged", method = RequestMethod.POST)
	public String logged(HttpServletRequest req, HttpSession s, Model model, HttpSession session) throws SQLException, ParseException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
	
		if(!checkIfEmailAndPasswordExists(email, password, model)){
			model.addAttribute("email", email);
			model.addAttribute("password", password);
			return "login";
		}
		
		User user = ud.getFullUserByEmail(email);
		
		
		session.setAttribute("User", user);
		session.setAttribute("logged", true);

		return "redirect:/";
	}
	
	
	private boolean checkIfEmailAndPasswordExists(String email, String password, Model model) throws SQLException{
		boolean emailExists = false;
		boolean passwordMatches = false;
		boolean emailEntered = true;
		boolean passwordEntered = true;
		if(email!=null && !email.isEmpty()){
			emailExists = ud.checksIfEmailExists(email);
		}else{
			emailEntered = false;
			model.addAttribute("errorEmail", "Email not entered!");
		}
		if(password!=null && !password.isEmpty()){
			passwordMatches = ud.checksIfPasswordAndEmailMatchesForUser(password, email);	
		}else{
			passwordEntered = false;
			model.addAttribute("errorPassword", "Password not entered!");
		}
		
		if(emailEntered && passwordEntered){
		if(emailExists && passwordMatches){
			return true;
		}else if(emailExists && !passwordMatches){
			model.addAttribute("errorPassword", "Wrong password entered!");
		}else{
			model.addAttribute("errorEmail", "Email and password combination not correct!");
		}
		}
		return false;
		
	}
	
	
}
