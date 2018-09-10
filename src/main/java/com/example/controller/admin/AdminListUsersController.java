package com.example.controller.admin;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.dao.AdminDAO;
import com.example.model.dao.UserDAO;
import com.example.model.pojo.User;
import com.example.util.LoggedValidator;

@Controller
public class AdminListUsersController {

	@Autowired
	UserDAO ud;
	
	@Autowired
	AdminDAO ad;
	
	/**
	 * Secured if not admin
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "showUsers", method = RequestMethod.GET)
	public String aboutUs(Model model, HttpSession session) {	
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		
		try {
			List<User> users = ad.findAllUsersForAdminList();
			for(User user : users) {
				user.setDaysFromLastLogin(getDateDiff(user.getDateLastLogIn(),TimeUnit.DAYS));
			}
			TreeSet<User> usersOrdered = new TreeSet<>(new Comparator<User>() {
				@Override
				public int compare(User o1, User o2) {
					int result = (int) (o2.getDaysFromLastLogin()-o1.getDaysFromLastLogin());
					 if(result ==0) {
						  result = o2.getOrders().size()-o1.getOrders().size();
						 if(result == 0) {
							 result = -1;
						 }
					 }
					 return result;
				}
			});
			usersOrdered.addAll(users);
			model.addAttribute("users", usersOrdered);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "adminViews/adminListUsers";
	}
	
	public static long getDateDiff(Date lastLoginDate, TimeUnit timeUnit) {
	    long diffInMillies = new Date().getTime() - lastLoginDate.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	
	@RequestMapping(value = "deleteUser/{userId}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable int userId, HttpSession session, RedirectAttributes attr) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		try {
			ad.deleteUser(new User(userId));
			attr.addFlashAttribute("msgDeletedUserSuccess", "User has been deleted successfully!");
		} catch (SQLException e) {
			e.printStackTrace();
			attr.addFlashAttribute("msgDeletedUserError", "Something went wrong! Please try again later!");
		}
		return "redirect:/showUsers";
		
	}
	
	
	@RequestMapping(value = "userDetails/{userId}", method = RequestMethod.GET)
	public String viewUserDetails(HttpSession session, @PathVariable int userId, Model model) {
		if(!LoggedValidator.checksIfAdminIsLogged(session)) {
			if(!LoggedValidator.checksIfUserIsLogged(session)) {
				return "notLoggedIn/indexNotLogged";
			}else {
				return "userViews/indexLogged";
			}
		}
		User user = null;
		try {
			user = ud.getUserById(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		model.addAttribute("user", user);
		return "adminViews/viewUserDetails";
	}
}
