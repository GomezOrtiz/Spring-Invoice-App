package com.cursoudemy.springboot.app.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cloudinary.utils.ObjectUtils;
import com.cursoudemy.springboot.app.CloudinaryConfig;
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
public class LoginController extends AbstractController {
	
	private Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	//Views
	private static final String LOGIN = "auth/login";
	private static final String NEW_USER_FORM_VIEW = "auth/newUser";
	private static final String PROFILE_VIEW = "auth/profile";
	
	//Redirections
	private static final String REDIRECT_TO_CLIENTS = "redirect:/clients";
	
	//Attributes
	private static final String TITLE = "title";
	private static final String USER = "user";
	private static final String WARNING = "warning";
	private static final String ERROR = "error";
	private static final String SUCCESS = "success";
	private static final String ROLES = "roles";
	private static final String IMAGE = "image";
	
	//Messages
	@Autowired
	private MessageSource messages;
	
	//Beans
	@Autowired
	private UserService userService;
	@Autowired
	private UserFormValidator userFormValidator;
	@Autowired
	private CloudinaryConfig cloudinary;
	
	//Methods
	@RequestMapping(value="/user/new", method=RequestMethod.GET)
	public String signup(Model model, Locale locale) {
		
		User user = new User();
		
		model.addAttribute(TITLE, messages.getMessage("forms.user.add.title", null, locale));
		model.addAttribute(USER, user);
		
		return NEW_USER_FORM_VIEW;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/user/new", method=RequestMethod.POST)
	public String signup(@ModelAttribute("user") User user, @RequestParam("formRoles") List<String> roles, @RequestParam("file") MultipartFile file, BindingResult result, Model model, RedirectAttributes redirect, SessionStatus status, Locale locale) {
		
		userFormValidator.validate(user, result);
		
		if(roles.size() == 0) {
			result.rejectValue(ROLES, "forms.user.errors.roles.empty");
		} else {
			user.setRoles(user.parseRoles(roles));
		}
		
		if (!file.isEmpty()) {
			try {
				Map uploadResult = cloudinary.getCloudinary().uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
				user.setImage((String) uploadResult.get("secure_url"));
			} catch (IOException e) {
				result.rejectValue(IMAGE, "forms.user.errors.image.upload");
				LOGGER.info("Se ha producido un error: " + e);
			}
		}
		
		if (result.hasErrors()) {
			model.addAttribute(TITLE, messages.getMessage("forms.user.add.title", null, locale));
			return NEW_USER_FORM_VIEW;
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
	
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String profile(Model model, Locale locale) {
		
		model.addAttribute(TITLE, messages.getMessage("user.profile.title", null, locale));
		
		return PROFILE_VIEW;
	}
}
