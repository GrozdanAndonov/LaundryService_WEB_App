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
public class AdminExpressOrderController {

	@Autowired
	OrderDAO od;
	
	private static String first_date_finished_orders;
	private static String second_date_finished_orders;
	
	private static String first_date_finished_orders_searching;
	private static String second_date_finished_orders_searching;
	
	@RequestMapping(value="viewExpressOrders", method= RequestMethod.GET)
	public String showOrderPage(HttpSession session) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		
		return "adminViews/expressOrders/viewExpressOrders";
	}
	
	@RequestMapping(value="viewUncheckedExpressOrders", method= RequestMethod.GET)
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
		orders = od.findAllUncheckedExpress();
		} catch (SQLException e) {
			e.printStackTrace(); //TODO
		}
		model.addAttribute("orders",orders);
		return "adminViews/expressOrders/viewUncheckedOrders";
	}
	
	@RequestMapping(value="uncheckedExpressOrderDetails/{orderId}", method= RequestMethod.GET)
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
		order = od.findUncheckedExpressOrderById(orderId);
		} catch (SQLException e) {
			e.printStackTrace(); //TODO
		}
		model.addAttribute("order",order);
		return "adminViews/expressOrders/uncheckedExpressOrderDetails";
	}
	
	@RequestMapping(value="checkExpressOrder/{orderId}", method= RequestMethod.GET)
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
		return "redirect:/viewUncheckedExpressOrders";
	}
	
	@RequestMapping(value="viewCheckedExpressOrders", method= RequestMethod.GET)
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
		orders = od.findAllCheckedExpressOrders();
		} catch (SQLException e) {
			e.printStackTrace(); //TODO
		}
		model.addAttribute("orders",orders);
		return "adminViews/expressOrders/viewCheckedOrders";
	}
	
	@RequestMapping(value="checkedExpressOrderDetails/{orderId}", method= RequestMethod.GET)
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
		order = od.findCheckedExpressOrderById(orderId);
		} catch (SQLException e) {
			e.printStackTrace(); //TODO
		}
		model.addAttribute("order",order);
		return "adminViews/expressOrders/checkedOrderDetails";
	}
	
	
	@RequestMapping(value="finishExpressOrder/{orderId}", method= RequestMethod.POST)
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
		
		if(AdminNormalOrderController.validateInputForFinishingForm(attr, price, discount)) {
			try {
				od.setUnfinishedOrderToFinished(orderId, Double.parseDouble(price), Double.parseDouble(discount));
			} catch (NumberFormatException e) {
				attr.addFlashAttribute("msgError", "Something went wrong! Please try again later!");
				e.printStackTrace(); 
				return "redirect:/checkedExpressOrderDetails/"+orderId;
			} catch (SQLException e) {
				attr.addFlashAttribute("msgError", "Something went wrong! Please try again later!");
				e.printStackTrace(); 
				return "redirect:/checkedExpressOrderDetails/"+orderId;
			}
			return "redirect:/viewCheckedExpressOrders";
		}
		
		return "redirect:/checkedExpressOrderDetails/"+orderId;
	}
	
	@RequestMapping(value = "viewFinishedExpressOrders", method=RequestMethod.GET)
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
		return "adminViews/expressOrders/viewFinishedOrders";
	}
	
	@RequestMapping(value= "searchFinishedExpressOrdersBetweenDates", method = RequestMethod.POST)
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
			
			orders = od.findAllFinishedExpressOrdersBetweenDates(firstDate, secondDate);
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
		return "redirect:/viewFinishedExpressOrders";
	}
	
	@RequestMapping(value="finishedExpressOrderDetails/{orderId}", method=RequestMethod.GET)
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
		order = od.findFinishedExpressOrderById(orderId);
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
		return "adminViews/expressOrders/finishedOrderDetails";
	}
	
	@RequestMapping(value="backToFinishedExpressOrdersList", method=RequestMethod.GET)
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
			orders = od.findAllFinishedExpressOrdersBetweenDates(first_date_finished_orders_searching, second_date_finished_orders_searching);
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
		return "adminViews/expressOrders/viewFinishedOrders";
	}
}


