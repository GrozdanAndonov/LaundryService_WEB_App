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
		Object o = session.getAttribute("logged");
		boolean logged = (o != null && ((boolean) o));
		User sessionUser = (User) session.getAttribute("User");
		
		if(session.isNew() || !logged || sessionUser == null) {
			return true;
		}
		return false;
	}
	
}
