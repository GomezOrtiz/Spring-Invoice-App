package com.cursoudemy.springboot.app.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cursoudemy.springboot.app.model.dao.UserDao;
import com.cursoudemy.springboot.app.model.entity.User;

/**
 * Controlador para manejar los errores
 *
 * @author David G. Ortiz
 */

@Controller
@RequestMapping("/error")
public class ErrorHandlingController implements ErrorController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

	//Attributes
	private static final String ERROR_PATH = "/error";
	private static final String ERROR_VIEW = "error/error";
	private static final String ERROR_404 = "redirect:/error/404";
	private static final String ERROR_403 = "redirect:/error/403";
	private static final String ERROR_500 = "redirect:/error/500";
	private static final String ERROR_404_VIEW = "error/404";
	private static final String ERROR_403_VIEW = "error/403";
	private static final String ERROR_500_VIEW = "error/500";
	
	//Beans
	@Autowired
	private UserDao userDao;
	
	//Methods
    @RequestMapping("")
    public String handleError(HttpServletRequest request) {
    	
    	Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    	Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
    	
    	if (null != status) {
    		Integer statusCode = Integer.valueOf(status.toString());
    		
        	LOGGER.info("Se ha producido un error " + statusCode + ": " + errorMessage);
    		
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return ERROR_404;
            } else if (statusCode == HttpStatus.FORBIDDEN.value()){
            	return ERROR_403;
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return ERROR_500;
            }
    	}

        return ERROR_VIEW;
    }
    
    @RequestMapping("/404")
    public String handle404() {
        return ERROR_404_VIEW;
    }
    
    @RequestMapping("/403")
    public String handle403() {
        return ERROR_403_VIEW;
    }
    
    @RequestMapping("/500")
    public String handle500() {
        return ERROR_500_VIEW;
    }

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
	
	@ModelAttribute("loggedInUser")
	public User getAuthenticatedUser() {
		
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
			UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		    return userDao.findByUsername(principal.getUsername());
		}
		return null;
	}

}
