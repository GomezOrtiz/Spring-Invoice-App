package com.fakecorp.invoicing.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fakecorp.invoicing.app.model.dao.UserDao;
import com.fakecorp.invoicing.app.model.entity.User;

/**
 * Controlador abstracto para pasar a todas las vistas de la aplicación el usuario logueado
 *
 * @author David G. Ortiz
 */

@Controller
public class AbstractController {
	
	@Autowired
	private UserDao userDao;
	
	@ModelAttribute("loggedInUser")
	public User getAuthenticatedUser() {
		
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
			UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		    return userDao.findByUsername(principal.getUsername());
		}
		return null;
	}
}
