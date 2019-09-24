package com.fakecorp.invoicing.app.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.fakecorp.invoicing.app.model.entity.User;
import com.fakecorp.invoicing.app.service.UserService;
import com.fakecorp.invoicing.app.utils.forms.UserFormValidator;
import com.fakecorp.invoicing.app.utils.upload.CloudinaryUploader;

/**
 * Controlador del login, la creación de usuarios y el perfil
 *
 * @author David G. Ortiz
 */

@Controller
@SessionAttributes("user")
public class AuthController extends BaseController {
	
	private Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	//Views
	private static final String LOGIN = "auth/login";
	private static final String NEW_USER_FORM_VIEW = "auth/newUser";
	private static final String PROFILE_VIEW = "auth/profile";
	
	//Redirections
	private static final String REDIRECT_TO_CLIENTS = "redirect:/clients";
	
	//Attributes
	private static final String USER = "user";
	private static final String ROLES = "roles";
	private static final String IMAGE = "image";
	
	//Beans
	@Autowired
	private UserService userService;
	@Autowired
	private UserFormValidator userFormValidator;
	@Autowired
	private CloudinaryUploader cloudinary;
	
	//Methods
	@RequestMapping(value="/user/new", method=RequestMethod.GET)
	public String signup(Model model) {
		
		User user = new User();
		
		addTitle(model, "forms.user.add.title");
		model.addAttribute(USER, user);
		
		return NEW_USER_FORM_VIEW;
	}
	
	@RequestMapping(value="/user/new", method=RequestMethod.POST)
	public String signup(@ModelAttribute("user") User user, @RequestParam("formRoles") List<String> roles, @RequestParam("file") MultipartFile file, BindingResult result, Model model, RedirectAttributes redirect, SessionStatus status) {
		
		userFormValidator.validate(user, result);
		
		if(roles.size() == 0) {
			result.rejectValue(ROLES, "forms.user.errors.roles.empty");
		} else {
			user.setRoles(user.parseRoles(roles));
		}
		
		if (!file.isEmpty()) {
			try {
				user.setImage(cloudinary.upload(file));
			} catch (IOException e) {
				result.rejectValue(IMAGE, "forms.user.errors.image.upload");
				LOGGER.info("Se ha producido un error al subir la imagen a Cloudinary: " + e);
			}
		}
		
		if (result.hasErrors()) {
			addTitle(redirect, "forms.user.add.title");
			return NEW_USER_FORM_VIEW;
		}
		
		userService.create(user);
		status.setComplete();
		
		addSuccessMessage(redirect, "forms.user.add.success", user.getUsername());
		
		return REDIRECT_TO_CLIENTS;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(@RequestParam(value="logout", required=false) String logout, @RequestParam(value="error", required=false) String error, Model model, Principal principal, RedirectAttributes redirect) {
		
		if(null != principal) {
			addWarningMessage(redirect, "login.already.logged");
			return REDIRECT_TO_CLIENTS;
		}
		
		if(null != logout) {
			addSuccessMessage(model, "login.logout.success");
		}
		
		if(null != error) {
			addErrorMessage(model, "login.error");
		}
		
		return LOGIN;
	}
	
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String profile(Model model) {
		
		addTitle(model, "user.profile.title");
		
		return PROFILE_VIEW;
	}
}
