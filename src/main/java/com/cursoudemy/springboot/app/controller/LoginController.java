package com.cursoudemy.springboot.app.controller;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador del login
 *
 * @author David G. Ortiz
 */

@Controller
public class LoginController {
	
	//Views
	private static final String LOGIN = "auth/login";
	
	//Redirections
	private static final String REDIRECT_TO_CLIENTS = "redirect:/clients";
	
	//Attributes
	private static final String WARNING = "warning";
	private static final String ERROR = "error";
	private static final String SUCCESS = "success";

	
	//Messages
	@Autowired
	private MessageSource messages;
	
	//Beans
	
	//Methods
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(@RequestParam(value="logout", required=false) String logout, @RequestParam(value="error", required=false) String error, Model model, Principal principal, RedirectAttributes redirect, Locale locale) {
		
		if(null != principal) {
			redirect.addFlashAttribute(WARNING, messages.getMessage("login.already.logged", null, locale));
			return REDIRECT_TO_CLIENTS;
		}
		
		if(null != logout) {
			model.addAttribute(SUCCESS, messages.getMessage("login.logout.success", null, locale));
		}
		
		if(null != error) {
			model.addAttribute(ERROR, messages.getMessage("login.error", null, locale));
		}
		
		return LOGIN;
	}
}
