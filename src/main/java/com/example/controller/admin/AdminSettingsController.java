package com.example.controller.admin;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.dao.AdminDAO;
import com.example.model.pojo.User;
import com.example.util.LoggedValidator;

@Controller
public class AdminSettingsController {

	@Autowired
	AdminDAO ad;
	
	/**
	 * Secured if not admin
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value="settingsAdmin", method = RequestMethod.GET)
	public String showSettings( HttpSession session, Model model) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		
		User user = (User) session.getAttribute("User");
		addDataForAdminInModel(user,model);
		return "adminViews/adminSettings";
	}
	
	@RequestMapping(value="settingsAdminRedirect", method = RequestMethod.GET)
	public String showSettingsAfterRedirect(HttpSession session) {
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
		
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String firstEmail = req.getParameter("firstEmail");
		String secondEmail = req.getParameter("secondEmail");
		String firstTelNum = req.getParameter("firstTelNumber");
		String secondTelNum = req.getParameter("secondTelNumber");
			User user = (User) session.getAttribute("User");
			
			if(this.checkIfInputIsValid(attr, user, firstName, lastName, firstEmail, secondEmail, firstTelNum, secondTelNum)) {
				if(this.checkIfDataIsTheSameAsAdminData(user, firstName, lastName, firstEmail, secondEmail, 
						firstTelNum, secondTelNum)) {
					String email = firstEmail;
					if(secondEmail != null && !secondEmail.isEmpty()) {
						email = email.concat(" "+secondEmail);
					}
					String telNum = firstTelNum;
					if(secondTelNum != null && !secondTelNum.isEmpty()) {
						telNum = telNum.concat(" "+secondTelNum);
					}
					try {
						ad.updateAdminSettings(user, firstName, lastName, email, telNum);
						user.setEmail(email);
						user.setFirstName(firstName);
						user.setLastName(lastName);
						user.setTelNumber(telNum);
						attr.addFlashAttribute("msgSuccess", "Information updated successfully!");
					} catch (SQLException e) {
						e.printStackTrace();
						attr.addFlashAttribute("msgError", "Something went wrong! Please try again later!");
					}
				}else {
					attr.addFlashAttribute("msgError", "Data is the same!");
				}
			}
			
				
		addDataForRedirectInForm(attr, firstName, lastName, firstEmail, secondEmail, firstTelNum, secondTelNum);
		return "redirect:settingsAdminRedirect";
	}

	private boolean checkIfDataIsTheSameAsAdminData(User user,String firstName, String lastName,
			String firstEmail, String secondEmail, String firstTelNum, String secondTelNum) {
		 boolean resultFirstName = false;
		 boolean resultLastName = false;
		 boolean resultEmails = false;
		 boolean resultTelNumbers = false;
		if(!user.getFirstName().equals(firstName)) {
			resultFirstName = true;
		}
		if(!user.getLastName().equals(lastName)) {
			resultLastName = true;
		}
		String[] emailsAdmin = user.getEmail().split(" ");
		
		if(emailsAdmin.length == 1) {
			resultEmails = this.checkWithOneThing(emailsAdmin[0], firstEmail, secondEmail);
		}else if(emailsAdmin.length == 2) {
			resultEmails = this.checkWithTwoThings(emailsAdmin[0], emailsAdmin[1], firstEmail, secondEmail);
		}
		
		String[] telephoneAdmins = user.getTelNumber().split(" ");
		if(telephoneAdmins.length == 1) {
			resultTelNumbers = this.checkWithOneThing(telephoneAdmins[0], firstTelNum, secondTelNum);
		} else if(telephoneAdmins.length == 2) {
			resultTelNumbers = this.checkWithTwoThings(telephoneAdmins[0], telephoneAdmins[1], firstTelNum, secondTelNum);
		}
		
		return resultFirstName || resultLastName || resultEmails || resultTelNumbers;
	}
	
	private boolean checkWithOneThing(String userThing, String firstThing, String secondThing) {
		boolean result = false;
		if(!firstThing.isEmpty() && !secondThing.isEmpty()) {
			result = true;
		}
		if(!firstThing.isEmpty() && secondThing.isEmpty()) {
			if(!firstThing.equals(userThing)) {
				result = true;
			}
		}
		if(firstThing.isEmpty() && !secondThing.isEmpty()) {
			if(!secondThing.equals(firstThing)) {
				result = true;
			}
		}
		return result;
	}
	
	private boolean checkWithTwoThings(String userFirstThing,String userSecondThing, String firstThing, String secondThing) {
		boolean result = false;
		if(firstThing.isEmpty() || secondThing.isEmpty()) {
			result = true;
		}
		if(!firstThing.isEmpty() && !secondThing.isEmpty()) {
			if(!(firstThing.equals(userFirstThing) || secondThing.equals(userFirstThing))) {
				result = true;
			}
			if(!(userSecondThing.equals(firstThing) || userSecondThing.equals(secondThing))){
				result = true;
			}
		}
		return result;
	}
	
	
	private boolean checkIfInputIsValid(RedirectAttributes attr, User user,String firstName, String lastName,
			String firstEmail, String secondEmail, String firstTelNum, String secondTelNum) {
		boolean result = true;
		if(firstName != null && !firstName.matches("^\\D{2,}$")) {
			attr.addFlashAttribute("firstNameError", "Enter valid first name!");
			result = false;
		}
		if(lastName != null && !lastName.matches("^\\D{2,}$")) {
			attr.addFlashAttribute("lastNameError", "Enter valid last name!");
			result = false;
		}
		if(firstEmail != null && !firstEmail.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			attr.addFlashAttribute("firstEmailError", "Enter valid email address!");
			result = false;
		}
		if(firstTelNum != null && !firstTelNum.matches("^[0-9]{10}$")) {
			attr.addFlashAttribute("firstTelNumberError", "Enter valid telephone number!");
			result = false;
		}
		
		if(secondEmail != null && !secondEmail.isEmpty() && !secondEmail.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			attr.addFlashAttribute("secondEmailError", "Enter valid second email number!");
			result = false;
		}
		if(secondTelNum != null && !secondTelNum.isEmpty() && !secondTelNum.matches("^[0-9]{10}$")) {
			attr.addFlashAttribute("secondTelNumberError", "Enter valid second telephone number!");
			result = false;
		}
		return result;
	}
	
	
	private void addDataForRedirectInForm(RedirectAttributes attr, String firstName, String lastName,
			String firstEmail, String secondEmail, String firstTelNum, String secondTelNum) {
		attr.addFlashAttribute("firstName",firstName);
		attr.addFlashAttribute("lastName",lastName);
		attr.addFlashAttribute("firstEmail",firstEmail);
		attr.addFlashAttribute("secondEmail",secondEmail);
		attr.addFlashAttribute("firstTelNumber",firstTelNum);
		attr.addFlashAttribute("secondTelNumber",secondTelNum);
	}
	
	private void addDataForAdminInModel(User user, Model model) {
		
		model.addAttribute("firstName", user.getFirstName());
		model.addAttribute("lastName", user.getLastName());
		String[] emails = user.getEmail().split(" ");
		if(emails.length == 1) {
			model.addAttribute("firstEmail", user.getEmail());
		} else if(emails.length == 2) {
			model.addAttribute("firstEmail", emails[0]);
			model.addAttribute("secondEmail", emails[1]);
		}
		String[] phones = user.getTelNumber().split(" ");
		if(phones.length == 1) {
		model.addAttribute("firstTelNumber", user.getTelNumber());
		}else if(phones.length == 2){
			model.addAttribute("firstTelNumber", phones[0]);
			model.addAttribute("secondTelNumber", phones[1]);
		}
		
	}
	
}

