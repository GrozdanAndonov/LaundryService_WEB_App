package com.example.controller.admin;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.dao.AdminDAO;
import com.example.model.dao.OrderDAO;
import com.example.model.dao.UserDAO;
import com.example.model.pojo.Order;
import com.example.model.pojo.User;
import com.example.util.DateFormatConverter;
import com.example.util.LoggedValidator;

@Controller
public class AdminUserDetailsController {

	@Autowired
	UserDAO ud;
	
	@Autowired
	AdminDAO ad;
	
	@Autowired
	OrderDAO od;
	
	private static String first_date_unchecked_orders;
	private static String second_date_unchecked_orders;
	
	@RequestMapping(value = "viewUncheckedOrdersForUser/{userId}", method = RequestMethod.GET)
	public String getAllUncheckedOrdersForUser(@PathVariable("userId") int idUser, Model model, HttpSession session) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		User  user = null;
		try {
		  user = ud.getUserById(idUser);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("userDetails", user);
		first_date_unchecked_orders = DateFormatConverter.convertFromDBToSearchingCalendarsView(user.getDateCreated());
		second_date_unchecked_orders = DateFormatConverter.convertFromDBToSearchingCalendarsView(new Date());
		model.addAttribute("firstDate", first_date_unchecked_orders);
		model.addAttribute("secondDate", second_date_unchecked_orders);
		
		return "adminViews/uncheckedOrdersForUserDetails";
	}
	
	@RequestMapping(value = "viewUncheckedOrdersForUserAfterRedirect", method = RequestMethod.GET)
	public String getAllUncheckedOrdersForUserAfterRedirect(HttpSession session, Model model) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		model.addAttribute("firstDate", first_date_unchecked_orders);
		model.addAttribute("secondDate", second_date_unchecked_orders);
		
		return "adminViews/uncheckedOrdersForUserDetails";
		
	}
	
	@RequestMapping(value = "searchUncheckedOrdersBetweenDatesForUser", method = RequestMethod.POST)
	public String findUncheckedOrdersBetweenDates(HttpSession session, RedirectAttributes attr) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		User user = (User) session.getAttribute("userDetails");
		HashSet<Order> orders = (HashSet<Order>) user.getOrders();
		
		TreeSet<Order> uncheckedOrders = new TreeSet<>(new Comparator<Order>() {

			@Override
			public int compare(Order o1, Order o2) {
				int result = o1.getDateCreated().compareTo(o2.getDateCreated());
				if(result == 0) {
					return -1;
				}
				return result;
			}
		});
		for(Order order : orders) {
			if(!order.isAccepted()) {
				order.setDateCreatedForView(DateFormatConverter.convertFromDBToSearchingCalendarsView(order.getDateCreated()));
				order.setDateFinishedForView("");
				order.setFirstName(user.getFirstName());
				order.setLastName(user.getLastName());
				uncheckedOrders.add(order);
			}
		}
		attr.addFlashAttribute("orders", uncheckedOrders);
		attr.addFlashAttribute("showContent", new Object());
		return "redirect:/viewUncheckedOrdersForUserAfterRedirect";
	}
	
	
	@RequestMapping(value="orderDetails/{orderId}", method = RequestMethod.GET)
	public String showOrderDetails(@PathVariable int orderId, HttpSession session, Model model) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		User user = (User) session.getAttribute("userDetails");
		Order orderResult  = null;
		for(Order order : user.getOrders()) {
			if(order.getId() == orderId) {
				orderResult = order;
			}
		}
		
		model.addAttribute("order", orderResult);
		
		return "adminViews/viewOrderDetails";
	}
	
	@RequestMapping(value="backToListWithUncheckedOrders", method=RequestMethod.GET)
	public String backFromUncheckedOrdersToUserDetails(HttpSession session,  RedirectAttributes attr) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		User user =	(User) session.getAttribute("userDetails");
	HashSet<Order> orders = (HashSet<Order>) user.getOrders();
		
		TreeSet<Order> uncheckedOrders = new TreeSet<>(new Comparator<Order>() {

			@Override
			public int compare(Order o1, Order o2) {
				int result = o1.getDateCreated().compareTo(o2.getDateCreated());
				if(result == 0) {
					return -1;
				}
				return result;
			}
		});
		for(Order order : orders) {
			if(!order.isAccepted()) {
				order.setDateCreatedForView(DateFormatConverter.convertFromDBToSearchingCalendarsView(order.getDateCreated()));
				order.setDateFinishedForView("");
				order.setFirstName(user.getFirstName());
				order.setLastName(user.getLastName());
				uncheckedOrders.add(order);
			}
		}
		attr.addFlashAttribute("orders", uncheckedOrders);
		attr.addFlashAttribute("showContent", new Object());
		return "redirect:/viewUncheckedOrdersForUserAfterRedirect";
	}
	
}
