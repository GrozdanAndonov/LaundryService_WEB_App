package com.example.controller.user;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.pojo.User;
import com.example.util.CaptchaSettings;
import com.example.util.LoggedValidator;
import com.example.util.MailSender;


@Controller
@RequestMapping(value="/contacts")
public class ContactsController {
	
	@Autowired
	MailSender sendMail;
	
	@Autowired
	CaptchaSettings captcha;
	
	@RequestMapping(method = RequestMethod.GET)
	public String contacts(HttpServletRequest request){	
		return "notLoggedIn/contacts";
	}
	
	/**
	 * Secured if not user
	 * 
	 * @param request
	 * @param session
	 * @param attr
	 * @return
	 */
	@RequestMapping(value="/contactWithAdmin", method = RequestMethod.GET)
	public String contactWithAdmins(HttpServletRequest request, HttpSession session, RedirectAttributes attr){	
		if(!LoggedValidator.checksIfUserIsLogged(session)){
			if(LoggedValidator.checksIfAdminIsLogged(session)) {
				return "adminViews/adminIndexPage";
			}
			return "notLoggedIn/indexNotLogged";
		}
		return "userViews/contactViewWithAdmin";
	}
	
	/**
	 * Secured if not user
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @param attr
	 * @return
	 */
	@RequestMapping(value="/contactWithAdmin", method = RequestMethod.POST)
	public String sendMessageToAdmin(HttpServletRequest request, HttpSession session, Model model, RedirectAttributes attr){	
		if(!LoggedValidator.checksIfUserIsLogged(session)){
			if(LoggedValidator.checksIfAdminIsLogged(session)) {
				return "adminViews/adminIndexPage";
			}
			return "notLoggedIn/indexNotLogged";
		}
		
		String text = request.getParameter("text");
		User user = (User) session.getAttribute("User");
		if(checkIfInputIsCorrect(text, model, request, attr)) {
			try {
				this.sendMail.sendMessage(user.getFirstName()+"-"+user.getLastName(), user.getEmail(), text, request.getRemoteAddr());
				model.addAttribute("msgSuccess", "Thank you! Your message has been sent successfully!");
			} catch (AddressException e) {
				model.addAttribute("errorMsg", "Something went wrong! Please try again later.");
				model.addAttribute("enteredText",text);
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
				model.addAttribute("enteredText",text);
				model.addAttribute("errorMsg", "Something went wrong! Please try again later.");
			}
		}
		return "redirect:contactWithAdmin";
	}
	
	private boolean checkIfInputIsCorrect(String text, Model model, HttpServletRequest request, RedirectAttributes attr) {
		boolean	result = true;
		if(text == null || text.isEmpty()) {
			attr.addFlashAttribute("errorText", "Add text in the message!");	
			model.addAttribute("errorText", "Add text in the message!");
			result = false;
		}
		if(!this.captcha.isCaptchaValid(request.getParameter("g-recaptcha-response"))) {
			attr.addFlashAttribute("recaptchaError", "Validate recaptcha!");
			model.addAttribute("recaptchaError", "Validate recaptcha!");
			result = false;
		}
		return result;
	}
		
	@RequestMapping(method = RequestMethod.POST)
	public String sendMessage(HttpServletRequest req, Model model){		
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String text = req.getParameter("text");
		int[] errorResult = new int[4];
		int langCode = 1;
		if (LocaleContextHolder.getLocale().toString().equals("en")) {
			langCode = 0;
		}
		String ip = req.getRemoteAddr();

		if (name.isEmpty()) {
			errorResult[0] = 1;
		}
		if (!email
				.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			errorResult[1] = 1;
		}
		if (text.isEmpty()) {
			errorResult[2] = 1;
		}
		if (!this.captcha.isCaptchaValid(req.getParameter("g-recaptcha-response"))) {
			errorResult[3] = 1;
		}
		try {
			if (errorResult[0] == 0 && errorResult[1] == 0 && errorResult[2] == 0 && errorResult[3] == 0) {
				this.sendMail.sendMessage(name, email, text, ip);
				if (langCode == 0) {
					model.addAttribute("msgSuccess", "Thank you! Your message has been sent successfully!");
				} else {
					model.addAttribute("msgSuccess", "Съобщението Ви беше изпратено успешно!");
				}
				return "successMailSend";
			} else {
				model.addAttribute("enteredName", name);
				model.addAttribute("enteredEmail", email);
				model.addAttribute("enteredText", text);
				this.setErrorAtributesWithLanguageCode(langCode, errorResult, model);
			}
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "notLoggedIn/contacts";
	}
	private void setErrorAtributesWithLanguageCode(int langCode, int[] errorResult, Model model) {
		if (errorResult[0] == 1) {
			if (langCode == 0) {
				model.addAttribute("errorName", "Enter name!");
			} else {
				model.addAttribute("errorName", "Въведете име в формата!");
			}
		}

		if (errorResult[1] == 1) {
			if (langCode == 0) {
				model.addAttribute("errorEmail", "Enter valid email!");
			} else {
				model.addAttribute("errorEmail", "Въведете валиден имейл!");
			}
		}

		if (errorResult[2] == 1) {
			if (langCode == 0) {
				model.addAttribute("errorText", "Enter text!");
			} else {
				model.addAttribute("errorText", "Въведете текст в съобщението!");
			}
		}
		if (errorResult[3] == 1) {
			if (langCode == 0) {
				model.addAttribute("recaptchaError", "Validate recaptcha!");
			} else {
				model.addAttribute("recaptchaError", "Неуспешна верификация!");
			}
		}
	}

}
