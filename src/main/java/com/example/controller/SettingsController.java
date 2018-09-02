package com.example.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.dao.UserDAO;
import com.example.model.pojo.User;
import com.example.util.LoggedValidator;



@Controller

public class SettingsController {

	@Autowired
	UserDAO ud;
	
	@RequestMapping(value="/aboutUser")
	public String aboutUser(Model model, HttpSession session){
		if(LoggedValidator.checksIfUserIsLogged(session)) {
			return "indexNotLogged";
		}
		model.addAttribute("numOrder",0);
		model.addAttribute("numDiscounts",0);
		model.addAttribute("numComments",0);
		
		return "aboutUser";
	}
	
	
	
	
	@RequestMapping(value="/settings")
	public String settings( Model model, HttpSession session){
		if(LoggedValidator.checksIfUserIsLogged(session)) {
			return "indexNotLogged";
		}
		User user = (User) session.getAttribute("User");
		addAtributesInModel(user, model);
		return "settingsUser";
	}
	
	
	@RequestMapping(value="/settingsWithErrors")
	public String settingsWithErrors( Model model, HttpSession session){
		if(LoggedValidator.checksIfUserIsLogged(session)) {
			return "indexNotLogged";
		}
		return "settingsUser";
	}
	
	
	@RequestMapping(value="changeUserData", method = RequestMethod.POST)
	public String changeUserValues(HttpSession session, HttpServletRequest req, Model model, RedirectAttributes attr) {
		if(LoggedValidator.checksIfUserIsLogged(session)) {
			return "indexNotLogged";
		}
		User user = (User) session.getAttribute("User");
		
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String streetAddress = req.getParameter("streetAddress");
		String city = req.getParameter("city");
		String zip = req.getParameter("zip");
		String telNumber = req.getParameter("telNumber");
		int zip2 = Integer.parseInt(zip);
		
		if(!firstName.equals(user.getFirstName())|| !lastName.equals(user.getLastName()) || !email.equals(user.getEmail()) 
				|| !streetAddress.equals(user.getStreetAddress()) || !city.equals(user.getCity()) || !(zip2 == user.getZipCode()) || !telNumber.equals(user.getTelNumber())) {
			if(checkIfInputIsCorrect( firstName,  lastName,  city,  streetAddress,  email, telNumber, attr)) {
			try {
				if(ud.updateUser(user.getId(), firstName, lastName, email, streetAddress, city, zip2, telNumber)) {
					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setEmail(email);
					user.setCity(city);
					user.setStreetAddress(streetAddress);
					user.setZipCode(zip2);
					user.setTelNumber(telNumber);
					attr.addFlashAttribute("msgSuccess", "Changes made successfully!");
					addAtributesInRedirect(user, firstName, lastName, email, streetAddress, city, zip, telNumber,attr);
					return "redirect:settingsWithErrors";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				attr.addFlashAttribute("msgError", "Something went wrong! Try again later!");
			}
			}
		}
		addAtributesInRedirect(user, firstName, lastName, email, streetAddress, city, zip, telNumber,attr);
		return "redirect:settingsWithErrors";
	}
	
	
	private boolean checkIfInputIsCorrect(String firstName, String lastName, String city, String streetAddress, String email, String telNumber, RedirectAttributes attr) {
		boolean result = true;
		if(firstName.isEmpty()) {
			attr.addFlashAttribute("firstNameError", "First name must not be empty!");
			result = false;
		}
		if(telNumber.isEmpty()) {
			attr.addFlashAttribute("telNumberError", "Enter valid telephone number!");
			result = false;
		}
		if(lastName.isEmpty()) {
			attr.addFlashAttribute("lastNameError", "Last name must not be empty!");
			result = false;
		}
		if(city.isEmpty()) {
			attr.addFlashAttribute("cityError", "City must not be empty!");
			result = false;
		}
		if(!email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			attr.addFlashAttribute("emailError","Enter valid email address!");
			result = false;
		}
		if(streetAddress.isEmpty()) {
			attr.addFlashAttribute("streetError", "Street address must not be empty!");
			result = false;
		}
		return result;
	}
	
	
	private void addAtributesInRedirect(User user, String firstName, String lastName, String email, String streetAddress,String city, String zip, String telNumber, RedirectAttributes attr) {
		attr.addFlashAttribute("firstName", firstName);
		attr.addFlashAttribute("lastName", lastName);
		attr.addFlashAttribute("email", email);
		attr.addFlashAttribute("streetAddress", streetAddress);
		attr.addFlashAttribute("city", city);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		attr.addFlashAttribute("dateOfCreation",dateFormat.format(user.getDateCreated()));
		attr.addFlashAttribute("zip", zip);
		attr.addFlashAttribute("telNumber", telNumber);
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
		model.addAttribute("telNumber", user.getTelNumber());
	}
	
}
