package com.example.util;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.example.model.pojo.User;

@Component
public class LoggedValidator {
	
	private LoggedValidator(){
		//utility class-no instances needed - static methods only
	}
	
	public static boolean checksIfUserIsLogged(HttpSession session){
		User sessionUser = (User) session.getAttribute("User");
		if(session.isNew() || sessionUser == null) {
				return false;
		}else if(sessionUser != null && sessionUser.getIsAdmin()) {
			return false;
		}
		return true;
	}
	
	public static boolean checksIfAdminIsLogged(HttpSession session) {
			User sessionUser = (User) session.getAttribute("User");
			if(sessionUser != null) {
			return sessionUser.getIsAdmin();
			}
		return false;
	}
	
}
