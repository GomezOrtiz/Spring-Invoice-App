package com.cursoudemy.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.cursoudemy.springboot.app.model.dao.UserDao;
import com.cursoudemy.springboot.app.model.entity.User;

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
