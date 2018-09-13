package com.example.controller.admin;

import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
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
	
	private static String first_date_checked_orders;
	private static String second_date_checked_orders;
	
	private static String first_date_finished_orders;
	private static String second_date_finished_orders;
	
	@RequestMapping(value = "viewUncheckedOrdersForUser", method = RequestMethod.GET)
	public String getAllUncheckedOrdersForUser(Model model, HttpSession session) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		User  user = (User) session.getAttribute("userDetails");
		
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
	public String findUncheckedOrdersBetweenDates(HttpSession session, RedirectAttributes attr, HttpServletRequest request) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		User user = (User) session.getAttribute("userDetails");
		first_date_unchecked_orders = request.getParameter("firstDate");
		second_date_unchecked_orders = request.getParameter("secondDate");
		
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
		
		
		Date firstDate = new Date();
		Date secondDate = new Date();
		try {
			firstDate = DateFormatConverter.convertFromCalendarViewToDate(first_date_unchecked_orders);
			secondDate = DateFormatConverter.convertFromCalendarViewToDate(second_date_unchecked_orders);
		} catch (ParseException e) {
			e.printStackTrace();//TODO
		}
		for(Order order : orders) {
			if(!order.isAccepted() && order.getDateCreated().before(secondDate) && order.getDateCreated().after(firstDate)) {
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
	
	
	@RequestMapping(value="orderUncheckedDetails/{orderId}", method = RequestMethod.GET)
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
		
		return "adminViews/viewUncheckedOrderDetails";
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
	
	@RequestMapping(value="searchCheckedOrdersBetweenDatesForUser", method = RequestMethod.POST)
	public String showCheckedOrders(HttpSession session, Model model, RedirectAttributes attr, HttpServletRequest request) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		User user = (User) session.getAttribute("userDetails");
		first_date_checked_orders = request.getParameter("firstDate");
		second_date_checked_orders = request.getParameter("secondDate");
		
			Set<Order> orders = user.getOrders();
			Set<Order> checkedOrders = new TreeSet<>(new Comparator<Order>() {
				@Override
				public int compare(Order o1, Order o2) {
				long result =o1.getDateCreated().getTime()-o2.getDateCreated().getTime();
					if(result == 0) {
						result = -1;
					}
					return (int) result;
				}
			});
			
			Date firstDate = new Date();
			Date secondDate = new Date();
			try {
				firstDate = DateFormatConverter.convertFromCalendarViewToDate(first_date_checked_orders);
				secondDate = DateFormatConverter.convertFromCalendarViewToDate(second_date_checked_orders);
			} catch (ParseException e) {
				e.printStackTrace(); //TODO
			}
			
			for(Order order : orders) {
				if(order.isAccepted() && order.getDateCreated().before(secondDate) && order.getDateCreated().after(firstDate)) {
					order.setDateCreatedForView(DateFormatConverter.convertFromDBToSearchingCalendarsView(order.getDateCreated()));
					order.setDateFinishedForView("");
					order.setFirstName(user.getFirstName());
					order.setLastName(user.getLastName());
					checkedOrders.add(order);
				}
			}
			
			attr.addFlashAttribute("orders", checkedOrders);
			attr.addFlashAttribute("showContent", new Object());
			return "redirect:/viewCheckedOrdersAfterRedirect";
	}
	
	@RequestMapping(value="viewCheckedOrdersAfterRedirect", method= RequestMethod.GET)
	public String showCheckedOrdersForUserDetailsAfterRedirect(HttpSession session, Model model) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		
		model.addAttribute("firstDate", first_date_checked_orders);
		model.addAttribute("secondDate", second_date_checked_orders);
		return "adminViews/checkedOrdersForUserDetails";
	}
	
	@RequestMapping(value="viewCheckedOrdersForUserDetails", method = RequestMethod.GET)
	public String showCheckedOrdersForUserDetails(Model model, HttpSession session) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		
		User  user = (User) session.getAttribute("userDetails");
		first_date_checked_orders = DateFormatConverter.convertFromDBToSearchingCalendarsView(user.getDateCreated());
		second_date_checked_orders = DateFormatConverter.convertFromDBToSearchingCalendarsView(new Date());
		model.addAttribute("firstDate", first_date_checked_orders);
		model.addAttribute("secondDate", second_date_checked_orders);
		return "adminViews/checkedOrdersForUserDetails";
	}
	
	@RequestMapping(value="backToViewCheckedOrdersForUserDetails", method = RequestMethod.GET)
	public String backToViewCheckedOrdersForUserDetails(HttpSession session, Model model, RedirectAttributes attr) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		User user = (User) session.getAttribute("userDetails");
			Set<Order> orders = user.getOrders();
			Set<Order> checkedOrders = new TreeSet<>(new Comparator<Order>() {
				@Override
				public int compare(Order o1, Order o2) {
				long result =o1.getDateCreated().getTime()-o2.getDateCreated().getTime();
					if(result == 0) {
						result = -1;
					}
					return (int) result;
				}
			});
			
			for(Order order : orders) {
				if(order.isAccepted()) {
					order.setDateCreatedForView(DateFormatConverter.convertFromDBToSearchingCalendarsView(order.getDateCreated()));
					order.setDateFinishedForView("");
					order.setFirstName(user.getFirstName());
					order.setLastName(user.getLastName());
					checkedOrders.add(order);
				}
			}
			
			attr.addFlashAttribute("orders", checkedOrders);
			attr.addFlashAttribute("showContent", new Object());
			return "redirect:/viewCheckedOrdersAfterRedirect";
	}
	
	@RequestMapping(value="orderCheckedDetails/{orderId}", method=RequestMethod.GET)
	public String viewCheckedOrderDetails(HttpSession session, Model model, @PathVariable int orderId) {
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
		
		return "adminViews/viewCheckedOrderDetails";
	}
	
	@RequestMapping(value = "viewFinishedOrdersForUser", method = RequestMethod.GET)
	public String viewFinishedOrdersForUser(HttpSession session, Model model) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		
		User  user = (User) session.getAttribute("userDetails");
		first_date_finished_orders = DateFormatConverter.convertFromDBToSearchingCalendarsView(user.getDateCreated());
		second_date_finished_orders = DateFormatConverter.convertFromDBToSearchingCalendarsView(new Date());
		model.addAttribute("firstDate", first_date_finished_orders);
		model.addAttribute("secondDate", second_date_finished_orders);
		
		return "adminViews/finishedOrdersForUserDetails";
		
	}
	
	
	@RequestMapping(value="searchFinishedOrdersBetweenDatesForUser", method = RequestMethod.POST)
	public String showFinishedOrders(HttpSession session, Model model, RedirectAttributes attr, HttpServletRequest request) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		User user = (User) session.getAttribute("userDetails");
		
		first_date_finished_orders = request.getParameter("firstDate");
		second_date_finished_orders = request.getParameter("secondDate");
		
		Set<Order> finishedOrders = new TreeSet<>(new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2) {
				int result = (int) (o1.getDateFinished().getTime()-o2.getDateFinished().getTime());
				if(result == 0) {
					result = -1;
				}
				return result;
			}
		});
		Date firstDate = new Date();
		Date secondDate = new Date();
		try {
			 firstDate = DateFormatConverter.convertFromCalendarViewToDate(first_date_finished_orders);
			secondDate =  DateFormatConverter.convertFromCalendarViewToDate(second_date_finished_orders);
		} catch (ParseException e) {
			e.printStackTrace(); //TODO
		}
		for(Order order: user.getOrders()) {
			if(order.getDateFinished() != null  && order.getDateFinished().before(secondDate) && order.getDateFinished().after(firstDate)) {
				order.setDateCreatedForView(DateFormatConverter.convertFromDBToSearchingCalendarsView(order.getDateCreated()));
				order.setDateFinishedForView(DateFormatConverter.convertFromDBToSearchingCalendarsView(order.getDateFinished()));
				order.setFirstName(user.getFirstName());
				order.setLastName(user.getLastName());
			finishedOrders.add(order);
			}
		}
			attr.addFlashAttribute("orders", finishedOrders);
			attr.addFlashAttribute("showContent", new Object());
			return "redirect:/viewFinishedOrdersAfterRedirect";
	
	}
	
	@RequestMapping(value = "viewFinishedOrdersAfterRedirect", method = RequestMethod.GET)
	public String viewFinishedOrdersAfterRedirect(HttpSession session, Model model) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		
		model.addAttribute("firstDate", first_date_finished_orders);
		model.addAttribute("secondDate", second_date_finished_orders);
		return "adminViews/finishedOrdersForUserDetails";
	}
	
	@RequestMapping(value = "orderFinishedDetails/{orderId}", method = RequestMethod.GET)
	public String viewFinishedOrderDetails(HttpSession session, @PathVariable int orderId, Model model) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		
		User user = (User) session.getAttribute("userDetails");
		
		for(Order order : user.getOrders()) {
			if(order.getId() == orderId) {
				model.addAttribute("order", order);
			}
		}
		return "adminViews/viewFinishedOrderDetails";
	}

	@RequestMapping(value = "backToListWithFinishedOrders", method = RequestMethod.GET)
	public String backToListWithFinishedOrders(HttpSession session, RedirectAttributes attr) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		
		User user = (User) session.getAttribute("userDetails");
		
		Set<Order> finishedOrders = new TreeSet<>(new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2) {
				int result = (int) (o1.getDateFinished().getTime()-o2.getDateFinished().getTime());
				if(result == 0) {
					result = -1;
				}
				return result;
			}
		});
		Date firstDate = null;
		Date secondDate = null;
		try {
			 firstDate = DateFormatConverter.convertFromCalendarViewToDate(first_date_finished_orders);
			secondDate =  DateFormatConverter.convertFromCalendarViewToDate(second_date_finished_orders);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		for(Order order: user.getOrders()) {
			if(order.getDateFinished() != null  && order.getDateFinished().before(secondDate) && order.getDateFinished().after(firstDate)) {
				order.setDateCreatedForView(DateFormatConverter.convertFromDBToSearchingCalendarsView(order.getDateCreated()));
				order.setDateFinishedForView(DateFormatConverter.convertFromDBToSearchingCalendarsView(order.getDateFinished()));
				order.setFirstName(user.getFirstName());
				order.setLastName(user.getLastName());
			finishedOrders.add(order);
			}
		}
			attr.addFlashAttribute("orders", finishedOrders);
			attr.addFlashAttribute("showContent", new Object());
			return "redirect:/viewFinishedOrdersAfterRedirect";
	}
	
}
