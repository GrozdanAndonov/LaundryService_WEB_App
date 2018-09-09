package com.example.controller.admin;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	@RequestMapping(value = "/showUsers", method = RequestMethod.GET)
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
			model.addAttribute("users", users);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "adminViews/adminListUsers";
	}
	
	public static long getDateDiff(Date lastLoginDate, TimeUnit timeUnit) {
	    long diffInMillies = new Date().getTime() - lastLoginDate.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
}
