package com.example.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.dao.DiscountDAO;
import com.example.model.dao.OrderDAO;
import com.example.model.dao.UserDAO;
import com.example.model.pojo.Order;
import com.example.model.pojo.User;
import com.example.util.LoggedValidator;

@Controller
@RequestMapping(value="orderCreate")
public class CreateOrderController {
	
	@Autowired
	UserDAO ud;
	
	@Autowired
	OrderDAO od;
	
	@RequestMapping(value="showCreateOrderPage", method = RequestMethod.GET)
	public String createOrderPage(HttpSession session, Model model, RedirectAttributes attr) {
		if(LoggedValidator.checksIfUserIsLogged(session)){
			return "notLoggedIn/indexNotLogged";
		}
		addUserDefaultValuesForOrderForm(session, model);
		return "userViews/userCreateOrder";
	}
	
	@RequestMapping(value="showCreateOrderPageWithErrors", method = RequestMethod.GET)
	public String createOrderPageAfterErrors(HttpSession session, Model model, RedirectAttributes attr) {
		if(LoggedValidator.checksIfUserIsLogged(session)){
			return "notLoggedIn/indexNotLogged";
		}
		
		return "userViews/userCreateOrder";
	}
	
	@RequestMapping(value="createOrder", method = RequestMethod.POST)
	public String createOrder(HttpSession session, HttpServletRequest request, RedirectAttributes attr) {
		if(LoggedValidator.checksIfUserIsLogged(session)){
			return "notLoggedIn/indexNotLogged";
		}
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String telNum = request.getParameter("telNumber");
		String checkBox = request.getParameter("isExpress");
		boolean isExpress = true;
		if(checkBox == null) {
			isExpress = false;
		}
		String city = request.getParameter("city");
		String strAddress = request.getParameter("streetAddress");
		String email = request.getParameter("email");
		String note = request.getParameter("text");
		
		
		if(validateInputForOrder(firstName, lastName, telNum, city, strAddress, email, note,isExpress, attr)) {
			addUserAtributesForFormAfterRedirect(firstName, lastName, telNum, city, strAddress, email, note, false, attr);
			User user = (User) session.getAttribute("User");
			try {
				od.createOrderForUser(user, new Order(user, firstName, lastName, email, city, strAddress, telNum, note, isExpress, false));
			} catch (SQLException e) {
				e.printStackTrace();
				attr.addFlashAttribute("msgError", "Something went wrong, try again later!");
				return "redirect:showCreateOrderPageWithErrors";
			}
			attr.addFlashAttribute("msgSuccess", "Order has been created successfully!");
			return "redirect:showCreateOrderPageWithErrors";
		}
		addUserAtributesForFormAfterRedirect(firstName, lastName, telNum, city, strAddress, email, note, isExpress, attr);
		return "redirect:showCreateOrderPageWithErrors";
	}
	
	
	
	public static void addUserAtributesForFormAfterRedirect( String firstName, String lastName, String telNum, String city, 
			String strAddress, String email, String note, boolean isExpress,  RedirectAttributes attr) {
		attr.addFlashAttribute("firstName", firstName);
		attr.addFlashAttribute("lastName", lastName);
		attr.addFlashAttribute("email", email);
		attr.addFlashAttribute("streetAddress", strAddress);
		attr.addFlashAttribute("city", city);
		attr.addFlashAttribute("telNumber", telNum);
		attr.addFlashAttribute("enteredText", note);
		if(isExpress) {
			attr.addFlashAttribute("isExpressV", "on");
		}
	}
	
	private void addUserDefaultValuesForOrderForm(HttpSession session, Model model) {
		User user = (User) session.getAttribute("User");
		model.addAttribute("firstName", user.getFirstName());
		model.addAttribute("lastName", user.getLastName());
		model.addAttribute("email", user.getEmail());
		model.addAttribute("streetAddress", user.getStreetAddress());
		model.addAttribute("city", user.getCity());
		model.addAttribute("telNumber", user.getTelNumber());
	}
	
	public static boolean validateInputForOrder(String firstName, String lastName, String telNum, String city, 
			String strAddress, String email, String note, boolean isExpress,  RedirectAttributes attr) {
		boolean isCorrect = true;
		if(firstName == null || firstName.isEmpty()) {
			attr.addFlashAttribute("firstNameError", "First name is required!");
			isCorrect = false;
		}
		if(lastName == null || lastName.isEmpty()) {
			attr.addFlashAttribute("lastNameError", "Last name is required!");
			isCorrect = false;
		}
		if(telNum == null || telNum.isEmpty()) {
			attr.addFlashAttribute("telephoneError", "Telephone number is required!");
			isCorrect = false;
		}
		if(city == null || city.isEmpty()) {
			attr.addFlashAttribute("cityError", "City is required!");
			isCorrect = false;
		}
		if(strAddress == null || strAddress.isEmpty()) {
			attr.addFlashAttribute("streetError", "Street address is required!");
			isCorrect = false;
		}
		if(email == null || email.isEmpty()) {
			attr.addFlashAttribute("emailError", "Email is required!");
			isCorrect = false;
		}
		
		return isCorrect;
	}
	
}
