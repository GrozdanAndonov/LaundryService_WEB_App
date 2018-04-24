package com.example.controller;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.util.CaptchaSettings;
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
		return "contacts";
	}
		
	@RequestMapping(value="/sendMsg", method = RequestMethod.POST)
	public String sendMessage(HttpServletRequest req){		
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String text =  req.getParameter("text");
		int nameCode = 1;
		int emailCode = 1;
		int textCode = 1;
		int captchaCode = 1;
		
		String ip = req.getRemoteAddr();
		
		if(name.isEmpty()){
			nameCode = 0;
		}
		if(!email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
			emailCode = 0;
		}
		if(text.isEmpty()){
			textCode = 0;
		}
		if(!this.captcha.isCaptchaValid(req.getParameter("g-recaptcha-response"))){
			captchaCode = 0;
		}
		try {
			if(nameCode == 1 && emailCode == 1 && textCode==1 && captchaCode==1){		
			this.sendMail.sendMessage(name , email, text, ip);
			return "redirect:success";
			}else{
				return "redirect:notSuccess/"+nameCode+"/"+emailCode+"/"+textCode+"/"+captchaCode;
			}
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}		
		return "index";
	}
	
	
	@RequestMapping(value="/success", method = RequestMethod.GET)
	public String getResponseGood(Model model){
		int result = 0;
		/*if(LocaleContextHolder.getLocale().toString().equals("en")){
			result = 0;
		}*/
		if(result==0){
		model.addAttribute("msg","Thank you! Your message has been sent successfully!");
		}else{
			model.addAttribute("msg","Съобщението Ви беше изпратено успешно!");
		}
		
		
		return "successMailSend";		
	}
	@RequestMapping(value="/notSuccess/{nameCode}/{emailCode}/{txtCode}/{recaptchaCode}", method = RequestMethod.GET)
	public String getResponseBad(Model model,
			@PathVariable("nameCode") int nameCode,
			@PathVariable("emailCode") int emailCode,
			 @PathVariable("txtCode") int txtCode,
			 @PathVariable("recaptchaCode") int recaptchaCode){
		int result = 0;
		/*if(LocaleContextHolder.getLocale().toString().equals("en")){
			result = 0;
		}*/
		if(result==0){
		model.addAttribute("msg","Processing error. Please, try again!");
		}else{
			model.addAttribute("msg","Грешка при обработката. Моля опитайте отново!");
		}
		
		if(nameCode == 0){
			if(result == 0){
			model.addAttribute("nameError", "Enter name!");
			}else{
				model.addAttribute("nameError", "Въведете име в формата!");
			}
		}
		if(emailCode == 0){
			if(result == 0){
			model.addAttribute("emailError", "Enter valid email!");
			}else{
				model.addAttribute("emailError", "Въведете валиден имейл!");
			}
		}
		if(txtCode == 0){
			if(result == 0){
			model.addAttribute("textError", "Enter text!");
			}else{
				model.addAttribute("textError", "Въведете текст в съобщението!");
			}
		}
		if(recaptchaCode == 0){
			if(result == 0){
			model.addAttribute("recaptchaError", "Validate recaptcha!");	
			}else{
				model.addAttribute("recaptchaError", "Неуспешна верификация!");
			}
		}		
		
		return "NotSuccess";		
	}
	
}
