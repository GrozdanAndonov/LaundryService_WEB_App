package com.example.controller.user;

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
		if(!LoggedValidator.checksIfUserIsLogged(session)){
			if(LoggedValidator.checksIfAdminIsLogged(session)) {
				return "adminViews/adminIndexPage";
			}
			return "notLoggedIn/indexNotLogged";
		}
		model.addAttribute("numOrder",0);
		model.addAttribute("numDiscounts",0);
		model.addAttribute("numComments",0);
		
		return "userViews/aboutUser";
	}
	
	
	
	
	@RequestMapping(value="/settings")
	public String settings( Model model, HttpSession session){
		if(!LoggedValidator.checksIfUserIsLogged(session)){
			if(LoggedValidator.checksIfAdminIsLogged(session)) {
				return "adminViews/adminIndexPage";
			}
			return "notLoggedIn/indexNotLogged";
		}
		User user = (User) session.getAttribute("User");
		addAtributesInModel(user, model);
		return "userViews/settingsUser";
	}
	
	
	@RequestMapping(value="/settingsWithErrors")
	public String settingsWithErrors( Model model, HttpSession session){
		if(!LoggedValidator.checksIfUserIsLogged(session)){
			if(LoggedValidator.checksIfAdminIsLogged(session)) {
				return "adminViews/adminIndexPage";
			}
			return "notLoggedIn/indexNotLogged";
		}
		return "userViews/settingsUser";
	}
	
	
	@RequestMapping(value="changeUserData", method = RequestMethod.POST)
	public String changeUserValues(HttpSession session, HttpServletRequest req, Model model, RedirectAttributes attr) {
		if(!LoggedValidator.checksIfUserIsLogged(session)) {
			return "notLoggedIn/indexNotLogged";
		}
		User user = (User) session.getAttribute("User");
		
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String streetAddress = req.getParameter("streetAddress");
		String city = req.getParameter("city");
		String zip = req.getParameter("zip");
		String telNumber = req.getParameter("telNumber");
		String bulstat = req.getParameter("bulstat");
		
		
		if(!firstName.equals(user.getFirstName())|| !lastName.equals(user.getLastName()) || !email.equals(user.getEmail()) 
				|| !streetAddress.equals(user.getStreetAddress()) || !city.equals(user.getCity()) || !(zip.equals(String.valueOf(user.getZipCode())))
				|| !telNumber.equals(user.getTelNumber()) || !bulstat.equals(user.getBulstatNumber())) {
			if(checkIfInputIsCorrect( firstName,  lastName,  city,  streetAddress,  email, telNumber, bulstat, zip, attr)) {
			try {
				int zip2 = Integer.parseInt(zip);
				if(ud.updateUser(user.getId(), firstName, lastName, email, streetAddress, city, zip2, telNumber, bulstat)) {
					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setEmail(email);
					user.setCity(city);
					user.setStreetAddress(streetAddress);
					user.setZipCode(zip2);
					user.setTelNumber(telNumber);
					user.setBulstatNumber(bulstat);
					attr.addFlashAttribute("msgSuccess", "Changes made successfully!");
					addAtributesInRedirect(user, firstName, lastName, email, streetAddress, city, zip, telNumber, bulstat,attr);
					return "redirect:settingsWithErrors";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				attr.addFlashAttribute("msgError", "Something went wrong! Try again later!");
			}
			}
		}
		addAtributesInRedirect(user, firstName, lastName, email, streetAddress, city, zip, telNumber, bulstat,attr);
		return "redirect:settingsWithErrors";
	}
	
	
	private boolean checkIfInputIsCorrect(String firstName, String lastName, String city, String streetAddress, String email, String telNumber, String bulstat, String zip, RedirectAttributes attr) {
		boolean result = true;
		if(!firstName.matches("^\\D{2,}$")) {
			attr.addFlashAttribute("firstNameError", "First name is not valid!");
			result = false;
		}
		if(!telNumber.matches("^[0-9]{10}$")) {
			attr.addFlashAttribute("telNumberError", "Enter valid telephone number! Example: 0888111222");
			result = false;
		}
		if(!lastName.matches("^\\D{2,}$")) {
			attr.addFlashAttribute("lastNameError", "Last name is not valid!");
			result = false;
		}
		if(!city.matches("^\\D{2,}$")) {
			attr.addFlashAttribute("cityError", "City is not valid!");
			result = false;
		}
		if(!email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			attr.addFlashAttribute("emailError","Enter valid email address!");
			result = false;
		}
		if(!streetAddress.matches("^[\\w\\s]{2,}$")) {
			attr.addFlashAttribute("streetError", "Street address must be valid! Example: Goce Delchev 4");
			result = false;
		}
		if(!bulstat.matches("^[0-9]{9}$")) {
			attr.addFlashAttribute("bulstatError", "Enter valid bulstat! Example: 12345678");
			result = false;
		}
		if(!zip.matches("^\\d{2,}$")) {
			attr.addFlashAttribute("zipError", "Enter valid zip code! Example: 2800");
			result = false;
		}
		return result;
	}
	
	
	private void addAtributesInRedirect(User user, String firstName, String lastName, String email, String streetAddress,String city, String zip, String telNumber, String bulstat,RedirectAttributes attr) {
		attr.addFlashAttribute("firstName", firstName);
		attr.addFlashAttribute("lastName", lastName);
		attr.addFlashAttribute("email", email);
		attr.addFlashAttribute("streetAddress", streetAddress);
		attr.addFlashAttribute("city", city);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		attr.addFlashAttribute("dateOfCreation",dateFormat.format(user.getDateCreated()));
		attr.addFlashAttribute("zip", zip);
		attr.addFlashAttribute("telNumber", telNumber);
		attr.addFlashAttribute("bulstat",bulstat);
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
		model.addAttribute("bulstat", user.getBulstatNumber());
	}
	
}
