package com.cursoudemy.springboot.app.controller;

import java.security.Principal;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cursoudemy.springboot.app.model.entity.User;
import com.cursoudemy.springboot.app.service.UserService;
import com.cursoudemy.springboot.app.utils.forms.UserFormValidator;

/**
 * Controlador del login y el signup
 *
 * @author David G. Ortiz
 */

@Controller
@SessionAttributes("user")
public class LoginController {
	
	//Views
	private static final String LOGIN = "auth/login";
	private static final String NEW_USER_FORM_VIEW = "auth/newUser";
	
	//Redirections
	private static final String REDIRECT_TO_CLIENTS = "redirect:/clients";
	
	//Attributes
	private static final String TITLE = "title";
	private static final String USER = "user";
	private static final String WARNING = "warning";
	private static final String ERROR = "error";
	private static final String SUCCESS = "success";
	
	//Messages
	@Autowired
	private MessageSource messages;
	
	//Beans
	@Autowired
	private UserService userService;
	@Autowired
	private UserFormValidator userFormValidator;
	
	//Methods
	@RequestMapping(value="/user/new", method=RequestMethod.GET)
	public String signup(Model model, Locale locale) {
		
		User user = new User();
		
		model.addAttribute(TITLE, messages.getMessage("forms.user.add.title", null, locale));
		model.addAttribute(USER, user);
		
		return NEW_USER_FORM_VIEW;
	}
	
	@RequestMapping(value="/user/new", method=RequestMethod.POST)
	public String signup(@ModelAttribute("user") User user, BindingResult result, Model model, RedirectAttributes redirect, SessionStatus status, Locale locale) {
		
		userFormValidator.validate(user, result);
		
		if (result.hasErrors()) {
			model.addAttribute(TITLE, messages.getMessage("forms.user.add.title", null, locale));
			return NEW_USER_FORM_VIEW;
		}
		
		if(user.getEnabled() == null) {
			user.setEnabled(true);
		}
		
		userService.create(user);
		status.setComplete();

		redirect.addFlashAttribute(SUCCESS, messages.getMessage("forms.user.add.success", null, locale));
		
		return REDIRECT_TO_CLIENTS;
	}
	
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
