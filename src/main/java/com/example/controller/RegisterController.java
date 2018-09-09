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
import com.example.util.LoggedValidator;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {

	@Autowired
	UserDAO ud;
	
	/**
	 * Returning the sign up page
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String register(HttpSession session) {	
		if(LoggedValidator.checksIfUserIsLogged(session)) {
			return  "userViews/indexLogged";
		}
		if(LoggedValidator.checksIfAdminIsLogged(session)) {
			return "adminViews/adminIndexPage";
		}
		return "notLoggedIn/register";
	}
	
	
	/**
	 * If user details are valid insert the user in DB, send him welcome email and
	 * redirect to login
	 * @throws ParseException 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String register(Model model, HttpSession session, HttpServletRequest req) throws ParseException {
		if(LoggedValidator.checksIfUserIsLogged(session)) {
			return  "userViews/indexLogged";
		}
		if(LoggedValidator.checksIfAdminIsLogged(session)) {
			return "adminViews/adminIndexPage";
		}
		String[] inputs = this.addInputsInStringArray(req);
		try {
			if(this.checkInputsForErrors(inputs, model)){
				this.addInputsInTheModel(inputs, model);
				return "notLoggedIn/register";
			}else{
				User user = new User(inputs[0],inputs[1],inputs[2],inputs[3],inputs[4],0);
					ud.insertUser(user);
					user = ud.getFullUserByEmail(inputs[3]);
				session.setAttribute("User", user);
				/*notificationService.sendNotification(u);*/
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	private String[] addInputsInStringArray(HttpServletRequest req){
		String[] inputs = new String[5];
		inputs[0] = req.getParameter("firstName");//1
		inputs[1] = req.getParameter("lastName");//2
		inputs[2] = req.getParameter("password");//3
		inputs[3] = req.getParameter("email");//4
		inputs[4] = req.getParameter("city");//5
		return inputs;
	}
	
	private void addInputsInTheModel(String[] inputs, Model model){
		model.addAttribute("firstName", inputs[0]);
		model.addAttribute("lastName", inputs[1]);
		model.addAttribute("password", inputs[2]);
		model.addAttribute("email", inputs[3]);
		model.addAttribute("address", inputs[4]);
	}
	
	private boolean checkInputsForErrors(String[] inputs, Model model) throws SQLException{
		boolean result = false;
			if(inputs[0] == null || inputs[0].isEmpty()){			
				model.addAttribute("errorFirstName","Enter valid first name!");
				result = true;
			}
			if(inputs[1] == null || inputs[1].isEmpty()){				
				model.addAttribute("errorLastName","Enter valid last name!");
				result = true;
			}
			if(inputs[2] == null || inputs[2].isEmpty() || inputs[2].length()<6){			
				model.addAttribute("errorPassword","Enter valid password with 6 or more length!");
				result = true;
			}
			if(!inputs[3].matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){				
				model.addAttribute("errorEmail","Enter valid email!");
				result = true;
			}
			if(ud.checksIfEmailIsNotFree(inputs[3])){
				model.addAttribute("errorEmail","Email is already being used!");
				result = true;
			}
			if(inputs[4] == null || inputs[4].isEmpty()){				
				model.addAttribute("errorCity","Enter valid city!");
				result = true;
			}
			return result;
	}
}
