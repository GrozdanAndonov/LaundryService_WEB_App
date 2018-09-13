package com.example.controller.admin;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.dao.OrderDAO;
import com.example.model.pojo.Order;
import com.example.model.pojo.User;
import com.example.util.DateFormatConverter;
import com.example.util.LoggedValidator;


@Controller
public class AdminNormalOrderController {
	
	@Autowired
	OrderDAO od;
	
	private static String first_date_finished_orders;
	private static String second_date_finished_orders;
	
	private static String first_date_finished_orders_searching;
	private static String second_date_finished_orders_searching;

	@RequestMapping(value="viewNormalOrders", method= RequestMethod.GET)
	public String showOrderPage(HttpSession session) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		
		return "adminViews/normalOrders/viewNormalOrders";
	}
	
	
	@RequestMapping(value="viewUncheckedNormalOrders", method= RequestMethod.GET)
	public String showUncheckedOrderPage(HttpSession session, Model model) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		List<Order> orders = null;
		try {
		orders = od.findAllUncheckedUnexpress();
		} catch (SQLException e) {
			e.printStackTrace(); //TODO
		}
		model.addAttribute("orders",orders);
		return "adminViews/normalOrders/viewUncheckedOrders";
	}
	
	
	@RequestMapping(value="uncheckedOrderDetails/{orderId}", method= RequestMethod.GET)
	public String showUncheckedOrderDetailsPage(@PathVariable int orderId, HttpSession session, Model model) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		Order order = null;
		try {
		order = od.findUncheckedUnExpressOrderById(orderId);
		} catch (SQLException e) {
			e.printStackTrace(); //TODO
		}
		model.addAttribute("order",order);
		return "adminViews/normalOrders/uncheckedOrderDetails";
	}
	
	@RequestMapping(value="checkOrder/{orderId}", method= RequestMethod.GET)
	public String checkOrder(@PathVariable int orderId, HttpSession session, Model model) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		try {
		od.setUncheckedOrderToChecked(orderId);
		} catch (SQLException e) {
			e.printStackTrace(); //TODO
		}
		return "redirect:/viewUncheckedNormalOrders";
	}
	
	@RequestMapping(value="viewCheckedNormalOrders", method= RequestMethod.GET)
	public String listCheckedOrders(HttpSession session, Model model) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		List<Order> orders = null;
		try {
		orders = od.findAllCheckedUnExpressOrders();
		} catch (SQLException e) {
			e.printStackTrace(); //TODO
		}
		model.addAttribute("orders",orders);
		return "adminViews/normalOrders/viewCheckedOrders";
	}
	
	
	@RequestMapping(value="checkedOrderDetails/{orderId}", method= RequestMethod.GET)
	public String showCheckedOrderDetailsPage(@PathVariable int orderId, HttpSession session, Model model) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		Order order = null;
		try {
		order = od.findCheckedUnExpressOrderById(orderId);
		} catch (SQLException e) {
			e.printStackTrace(); //TODO
		}
		model.addAttribute("order",order);
		return "adminViews/normalOrders/checkedOrderDetails";
	}
	
	@RequestMapping(value="finishOrder/{orderId}", method= RequestMethod.POST)
	public String finishOrder(@PathVariable int orderId, HttpSession session, Model model,
			HttpServletRequest request, RedirectAttributes attr) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		String price = request.getParameter("cost");
		String discount = request.getParameter("discount");
		
		if(validateInputForFinishingForm(attr, price, discount)) {
			try {
				od.setUnfinishedOrderToFinished(orderId, Double.parseDouble(price), Double.parseDouble(discount));
			} catch (NumberFormatException e) {
				attr.addFlashAttribute("msgError", "Something went wrong! Please try again later!");
				e.printStackTrace(); 
				return "redirect:/checkedOrderDetails/"+orderId;
			} catch (SQLException e) {
				attr.addFlashAttribute("msgError", "Something went wrong! Please try again later!");
				e.printStackTrace(); 
				return "redirect:/checkedOrderDetails/"+orderId;
			}
			return "redirect:/viewCheckedNormalOrders";
		}
		
		return "redirect:/checkedOrderDetails/"+orderId;
	}
	
	public static boolean validateInputForFinishingForm(RedirectAttributes attr, String cost, String discount) {
		boolean result = true;
		if(!cost.isEmpty()) {
			try {
				if(Double.parseDouble(cost) < 0) {
					result = addFlashAttribute(attr, cost, "cost");
				}
			}catch(NumberFormatException e) {
				result = addFlashAttribute(attr, cost, "cost");
			}
		}else {
			result = addFlashAttribute(attr, cost, "cost");
		}
			
		if(!discount.isEmpty()) {
			try {
				if(Double.parseDouble(discount) < 0) {
					result = addFlashAttribute(attr, discount, "discount");
				}
			}catch(NumberFormatException e) {
				result = addFlashAttribute(attr, discount, "discount");
			}
		}else {
			result = addFlashAttribute(attr, discount, "discount");
		}
		return result;
	}
	
	private static boolean addFlashAttribute(RedirectAttributes attr, String data, String about) {
		attr.addFlashAttribute(about+"Error", "Enter valid "+about+"!");
		attr.addFlashAttribute(about, data);
		return false;
	}
	
	
	@RequestMapping(value = "viewFinishedNormalOrders", method=RequestMethod.GET)
	public String listFinishedOrders(HttpSession session, Model model) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
	
		User user = (User) session.getAttribute("User");
		first_date_finished_orders = DateFormatConverter.convertFromDBToSearchingCalendarsView(user.getDateCreated());
		second_date_finished_orders = DateFormatConverter.convertFromDBToSearchingCalendarsView(new Date());
		
		model.addAttribute("firstDate", first_date_finished_orders);
		model.addAttribute("secondDate", second_date_finished_orders);
		return "adminViews/normalOrders/viewFinishedOrders";
	}
	
	
	@RequestMapping(value= "searchFinishedOrdersBetweenDates", method = RequestMethod.POST)
	public String searchFinishedOrdersBetweenDates(HttpSession session, HttpServletRequest request, RedirectAttributes attr) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		
		String firstDate = request.getParameter("firstDate");
		String secondDate = request.getParameter("secondDate");
		List<Order> orders = null;
		try {
			firstDate = DateFormatConverter.convertFromPMTo24Hour(firstDate);
			secondDate = DateFormatConverter.convertFromPMTo24Hour(secondDate);
			first_date_finished_orders_searching = firstDate;
			second_date_finished_orders_searching = secondDate;
			
			orders = od.findAllFinishedUnExpressOrdersBetweenDates(firstDate, secondDate);
		} catch (ParseException e1) {
			// TODO 
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO 
			e.printStackTrace();
		}
		double total = 0;
		for(Order order : orders) {
			total+=order.getCost();
		}
		
		attr.addFlashAttribute("total",total);
		attr.addFlashAttribute("orders",orders);
		attr.addFlashAttribute("showContent", new Object());
		return "redirect:/viewFinishedNormalOrders";
	}
	
	@RequestMapping(value="finishedOrderDetails/{orderId}", method=RequestMethod.GET)
	public String viewFinishedOrderDetails(@PathVariable int orderId, HttpSession session, Model model) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		Order order = null;
		try {
		order = od.findFinishedUnExpressOrderById(orderId);
		} catch (SQLException e) {
			e.printStackTrace(); //TODO
		}
		double result = 0;
		if(order!=null) {
		double discounts = order.getDiscount()+order.getTotalDiscount();
			result = (discounts/100)*order.getCost();
			System.out.println(result);
		}
		model.addAttribute("discount", ((double) Math.round(result * 100) / 100));
		result = (double) Math.round((order.getCost()-result) * 100) / 100;
		model.addAttribute("totalPrice", result);
		model.addAttribute("order",order);
		return "adminViews/normalOrders/finishedOrderDetails";
	}
	
	@RequestMapping(value="backToFinishedOrdersList", method=RequestMethod.GET)
	public String backToFinishedOrdersList(HttpSession session, Model model) throws ParseException {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		List<Order> orders = null;
		try {
			orders = od.findAllFinishedUnExpressOrdersBetweenDates(first_date_finished_orders_searching, second_date_finished_orders_searching);
		} catch (SQLException e) {
			// TODO 
			e.printStackTrace();
		}
		double total = 0;
		for(Order order : orders) {
			total+=order.getCost();
		}
		model.addAttribute("total",total);
		model.addAttribute("orders",orders);
		model.addAttribute("showContent", new Object());
		model.addAttribute("firstDate", first_date_finished_orders);
		model.addAttribute("secondDate", second_date_finished_orders);
		return "adminViews/normalOrders/viewFinishedOrders";
		
		
	}
	
	
	
	
}
