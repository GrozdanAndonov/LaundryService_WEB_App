package com.example.controller.user;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Set;

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
@RequestMapping(value="orderList")
public class ListOrdersController {

	@Autowired
	private OrderDAO od;
	
	private static String staticDateFrom = "";
	private static String staticDateTo = "";
	
	private static String staticDateFromFinishedOrders = "";
	private static String staticDateToFinishedOrders = "";
	
	private static int userOrderForEditId = 0;
	
	/**
	 * Secured if not user
	 * 
	 * @param session
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping(value="showFinishedOrderListPage", method = RequestMethod.GET)
	public String listOrderPage(HttpSession session, Model model, HttpServletRequest req) {
		if(!LoggedValidator.checksIfUserIsLogged(session)){
			if(LoggedValidator.checksIfAdminIsLogged(session)) {
				return "adminViews/adminIndexPage";
			}
				return "notLoggedIn/indexNotLogged";
		}
		
		User user = (User) session.getAttribute("User");
		String newDate = DateFormatConverter.convertFromDBToSearchingCalendarsView(user.getDateCreated());
		String curDate = DateFormatConverter.convertFromDBToSearchingCalendarsView(new Date());
		model.addAttribute("firstDate", newDate);
		model.addAttribute("secondDate", curDate);
		
		return "userViews/userListFinishedOrders";
	}
	
	/**
	 * Secured if not user
	 * 
	 * @param session
	 * @param req
	 * @param attr
	 * @return
	 * @throws ParseException
	 * @throws SQLException
	 */
	@RequestMapping(value="searchOrdersBetweenDates", method = RequestMethod.POST)
	public String searchOrdersByDate(HttpSession session,  HttpServletRequest req,  RedirectAttributes attr) throws ParseException, SQLException {
		if(!LoggedValidator.checksIfUserIsLogged(session)){
			if(LoggedValidator.checksIfAdminIsLogged(session)) {
				return "adminViews/adminIndexPage";
			}
			return "notLoggedIn/indexNotLogged";
		}
		User user = (User) session.getAttribute("User");
		
		String firstDate = DateFormatConverter.convertFromPMTo24Hour(req.getParameter("firstDate")); // coming 08/30/2018 10:05 PM
		String secondDate = DateFormatConverter.convertFromPMTo24Hour(req.getParameter("secondDate")); // coming 08/30/2018 10:05 PM
		staticDateFromFinishedOrders = firstDate;
		staticDateToFinishedOrders = secondDate;
		setDataForFinishedOrders(user, attr);
		
		return "redirect:showFinishedOrderListPage";
	}
	
	/**
	 * Secured if not user
	 * 
	 * @param orderId
	 * @param session
	 * @param model
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value="orderDetails/{orderId}", method = RequestMethod.GET)
	public String showOrderDetails(@PathVariable int orderId, HttpSession session, Model model) throws SQLException {
		if(!LoggedValidator.checksIfUserIsLogged(session)){
			if(LoggedValidator.checksIfAdminIsLogged(session)) {
				return "adminViews/adminIndexPage";
			}
			return "notLoggedIn/indexNotLogged";
		}
		setDataForOrderDetails(session, orderId, model);
		return "userViews/userViewOrderDetails";
	}
	
	/**
	 * Secured if not user
	 * 
	 * @param orderId
	 * @param session
	 * @param model
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value="unfinishedOrderDetails/{orderId}", method = RequestMethod.GET)
	public String showUnfinishedOrderDetails(@PathVariable int orderId, HttpSession session, Model model) throws SQLException {
		if(!LoggedValidator.checksIfUserIsLogged(session)){
			if(LoggedValidator.checksIfAdminIsLogged(session)) {
				return "adminViews/adminIndexPage";
			}
			return "notLoggedIn/indexNotLogged";
		}
		setDataForOrderDetails(session, orderId, model);
		return "userViews/userViewUnfinishedOrderDetails";
	}
	
	private void setDataForOrderDetails(HttpSession session, int orderId, Model model) throws SQLException {
		User user = (User) session.getAttribute("User");
		Order order = od.findOrderByIdForUser(user, new Order(orderId));
		model.addAttribute("order", order);
	}
	
	/**
	 * Secured if not user
	 * 
	 * @param session
	 * @param model
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value="showOpenListOrderPage", method = RequestMethod.GET)
	public String showOpenOrderList(HttpSession session, Model model) throws SQLException {
		if(!LoggedValidator.checksIfUserIsLogged(session)){
			if(LoggedValidator.checksIfAdminIsLogged(session)) {
				return "adminViews/adminIndexPage";
			}
			return "notLoggedIn/indexNotLogged";
		}
		
		User user = (User) session.getAttribute("User");
		String newDate = DateFormatConverter.convertFromDBToSearchingCalendarsView(user.getDateCreated());
		String curDate = DateFormatConverter.convertFromDBToSearchingCalendarsView(new Date());
		model.addAttribute("firstDate", newDate);
		model.addAttribute("secondDate", curDate);
		
		return "userViews/userListUnfinishedOrders";
	}
	
	
	/**
	 * Secured if not user
	 * 
	 * @param attr
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value="findCheckedAndUncheckedOrders", method = RequestMethod.POST)
	public String showcheckedAndUncheckedOrders(RedirectAttributes attr, HttpSession session,  HttpServletRequest req) {
		if(!LoggedValidator.checksIfUserIsLogged(session)){
			if(LoggedValidator.checksIfAdminIsLogged(session)) {
				return "adminViews/adminIndexPage";
			}
			return "notLoggedIn/indexNotLogged";
		}
		
		User user = (User) session.getAttribute("User");
		String dateFrom = ""; 
		String dateTo = "";
		try {
			dateTo = DateFormatConverter.convertFromPMTo24Hour(req.getParameter("secondDate")); // coming 08/30/2018 10:05 PM
			dateFrom = DateFormatConverter.convertFromPMTo24Hour(req.getParameter("firstDate")); // coming 08/30/2018 10:05 PM
			staticDateFrom = DateFormatConverter.convertFromPMTo24Hour(req.getParameter("firstDate"));
			staticDateTo = DateFormatConverter.convertFromPMTo24Hour(req.getParameter("secondDate"));
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		setDataForUnfinishedOrders(attr, user, dateFrom, dateTo);
		return "redirect:showOpenListOrderPage";
	}
	
	/**
	 * Secured if not user
	 * 
	 * @param orderId
	 * @param session
	 * @param attr
	 * @return
	 */
	@RequestMapping(value="/deleteUncheckedOrder/{orderId}", method = RequestMethod.GET)
	public String deleteOrder(@PathVariable int orderId, HttpSession session, RedirectAttributes attr) {
		if(!LoggedValidator.checksIfUserIsLogged(session)){
			if(LoggedValidator.checksIfAdminIsLogged(session)) {
				return "adminViews/adminIndexPage";
			}
			return "notLoggedIn/indexNotLogged";
		}
		User user = (User) session.getAttribute("User");
		try {
			od.deleteOrderForUser(new Order(orderId), user);
			attr.addFlashAttribute("msgDeletedOrderSuccess", "Order has been deleted successfully!");
		} catch (SQLException e) {
			e.printStackTrace();
			attr.addFlashAttribute("msgDeletedOrderError", "Something went wrong! Please try again later!");//pass error code to fmt message
		}
		setDataForUnfinishedOrders(attr, user, staticDateFrom, staticDateTo);
		
		return "redirect:/orderList/showOpenListOrderPage";
	}
	
	/**
	 * Secured if not user
	 * 
	 * @param orderId
	 * @param session
	 * @param attr
	 * @param model
	 * @return
	 */
	@RequestMapping(value="editOrder/{orderId}", method = RequestMethod.GET)
	public String editOrder(@PathVariable int orderId, HttpSession session, RedirectAttributes attr, Model model) {
		if(!LoggedValidator.checksIfUserIsLogged(session)){
			if(LoggedValidator.checksIfAdminIsLogged(session)) {
				return "adminViews/adminIndexPage";
			}
			return "notLoggedIn/indexNotLogged";
		}
		User user = (User) session.getAttribute("User");
		Order order = null;
		try {
			 order = od.findOrderByIdForUser(user, new Order(orderId));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		userOrderForEditId = orderId;
		model.addAttribute("firstName", order.getFirstName());
		model.addAttribute("lastName", order.getLastName());
		model.addAttribute("email", order.getEmail());
		model.addAttribute("streetAddress", order.getStreetAddress());
		model.addAttribute("city", order.getCity());
		model.addAttribute("telNumber", order.getTelNumber());
		model.addAttribute("enteredText", order.getNote());
		if(order.getIsExpress()) {
		model.addAttribute("isExpressV",order.getIsExpress());
		}
		
		return "userViews/userEditOrder";
	}
	
	/**
	 * Secured if not user
	 * 
	 * @param session
	 * @param req
	 * @param attr
	 * @return
	 */
	@RequestMapping(value="editCurrentOrder", method = RequestMethod.POST)
	public String editOrder(HttpSession session, HttpServletRequest req, RedirectAttributes attr) {
		if(!LoggedValidator.checksIfUserIsLogged(session)){
			if(LoggedValidator.checksIfAdminIsLogged(session)) {
				return "adminViews/adminIndexPage";
			}
			return "notLoggedIn/indexNotLogged";
		}
		
		String firstNameInput = req.getParameter("firstName");
		String lastNameInput = req.getParameter("lastName");
		String emailInput = req.getParameter("email");
		String streetAddressInput = req.getParameter("streetAddress");
		String cityInput = req.getParameter("city");
		String telNumberInput = req.getParameter("telNumber");
		String textInput = req.getParameter("text");
		String checkBoxInput = req.getParameter("isExpress");
		boolean isExpress = true;
		if(checkBoxInput == null) {
			isExpress = false;
		}
		
		if(CreateOrderController.validateInputForOrder(firstNameInput, lastNameInput, telNumberInput, cityInput, streetAddressInput, emailInput, textInput, isExpress, attr)) {
			User user = (User) session.getAttribute("User");
			try {
				if(checkEditInfoFromTheDB(firstNameInput, lastNameInput, telNumberInput, cityInput, streetAddressInput, emailInput, textInput, isExpress, attr, user)) {
					od.updateOrder(user, firstNameInput, lastNameInput, telNumberInput, cityInput, streetAddressInput, emailInput, textInput, isExpress, userOrderForEditId);
					attr.addFlashAttribute("msgSuccess", "Order has been updated successfully!");
				}else {
					attr.addFlashAttribute("msgError", "The information you are trying to update is the same!");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				attr.addFlashAttribute("msgError", "Something went wrong! Please try again later!");
			}
		}
		CreateOrderController.addUserAtributesForFormAfterRedirect(firstNameInput, lastNameInput, telNumberInput, cityInput, streetAddressInput, emailInput, textInput, isExpress, attr);
		return "redirect:showEditOrderPageWithErrors";
	}
	
	/**
	 * Secured if not user
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value="showEditOrderPageWithErrors", method = RequestMethod.GET)
	public String showErrorsForEditOrder(HttpSession session) {
		if(!LoggedValidator.checksIfUserIsLogged(session)){
			if(LoggedValidator.checksIfAdminIsLogged(session)) {
				return "adminViews/adminIndexPage";
			}
			return "notLoggedIn/indexNotLogged";
		}
		return "userViews/userEditOrder";
	}
	
	/**
	 * Secured if not user
	 * 
	 * @param session
	 * @param attr
	 * @return
	 */
	@RequestMapping(value = "backToOpenListOrderPage", method = RequestMethod.GET)
	public String backToOpenListOrderPage( HttpSession session, RedirectAttributes attr) {
		if(!LoggedValidator.checksIfUserIsLogged(session)){
			if(LoggedValidator.checksIfAdminIsLogged(session)) {
				return "adminViews/adminIndexPage";
			}
			return "notLoggedIn/indexNotLogged";
		}
		User user = (User) session.getAttribute("User");
		setDataForUnfinishedOrders(attr, user, staticDateFrom, staticDateTo);
		return "redirect:showOpenListOrderPage";
	}
	
	/**
	 * Secured if not user
	 * 
	 * @param session
	 * @param attr
	 * @return
	 */
	@RequestMapping(value="backToFinishedListOrderPage", method = RequestMethod.GET)
	public String backToFinishedListOrderPage(HttpSession session, RedirectAttributes attr) {
		if(!LoggedValidator.checksIfUserIsLogged(session)){
			if(LoggedValidator.checksIfAdminIsLogged(session)) {
				return "adminViews/adminIndexPage";
			}
			return "notLoggedIn/indexNotLogged";
		}
		User user = (User) session.getAttribute("User");
		try {
			setDataForFinishedOrders(user, attr);
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		return "redirect:showFinishedOrderListPage";
	}
	
	private void setDataForFinishedOrders(User user ,RedirectAttributes attr) throws SQLException, ParseException {
		Set<Order> orders =	od.searchFinishedOrdersForUserBetweenDates(user, staticDateFromFinishedOrders, staticDateToFinishedOrders);
		double total = 0;
		
		for(Order order : orders) {
			total+=order.getCost();
		}
		
		attr.addFlashAttribute("orders", orders);
		attr.addFlashAttribute("total",total);
		attr.addFlashAttribute("showContent",new Object());
	}
	
	/**
	 * returns true if the data from DB is the same as the data from the form
	 */
	private boolean checkEditInfoFromTheDB(String firstName, String lastName, String telNum, String city, 
			String strAddress, String email, String note, boolean isExpress,  RedirectAttributes attr, User user) throws SQLException {
		Order order = od.findOrderByIdForUser(user, new Order(userOrderForEditId));
		return checksIfDataFromDBAndFromFormIsTheSame(firstName, lastName, telNum, city, strAddress, email, note, isExpress, order);
	}
	
	/**
	 * returns true if the data from DB is the same as the data from the form
	 */
	private boolean checksIfDataFromDBAndFromFormIsTheSame(String firstName, String lastName, String telNum, String city, 
			String strAddress, String email, String note, boolean isExpress, Order order) {
		
		boolean result = false;
		
		if(!firstName.equals(order.getFirstName())) {
			result = true;
		}
		if(!lastName.equals(order.getLastName())) {
			result = true;
		}
		if(!telNum.equals(order.getTelNumber())) {
			result = true;
		}
		if(!city.equals(order.getCity())) {
			result = true;
		}
		if(!strAddress.equals(order.getStreetAddress())) {
			result = true;
		}
		if(!email.equals(order.getEmail())) {
			result = true;
		}
		if(!note.equals(order.getNote())) {
			result = true;
		}
		if(isExpress != order.getIsExpress()) {
			result = true;
		}
		return result;
	}

	
	private void setDataForUnfinishedOrders(RedirectAttributes attr, User user, String dateFrom, String dateTo) {
		Set<Order> uncheckedOrders = null;
		Set<Order> checkedOrders = null;
		try {
			uncheckedOrders = od.searchUncheckedOrdersForUserBetweenDates(user, dateFrom,dateTo);
			checkedOrders = od.searchCheckedOrdersForUserBetweenDates(user, dateFrom, dateTo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		attr.addFlashAttribute("showContent", new Object());
		attr.addFlashAttribute("uncheckedOrders", uncheckedOrders);
		attr.addFlashAttribute("checkedOrders", checkedOrders);
	}
}