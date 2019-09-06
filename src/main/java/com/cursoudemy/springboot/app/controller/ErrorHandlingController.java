package com.cursoudemy.springboot.app.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador para manejar los errores
 *
 * @author David G. Ortiz
 */

@Controller
public class ErrorHandlingController implements ErrorController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

	//Attributes
	private static final String ERROR_PATH = "/error";
	private static final String ERROR_VIEW = "error/error";
	private static final String ERROR_404_VIEW = "error/404";
	private static final String ERROR_403_VIEW = "error/403";
	private static final String ERROR_500_VIEW = "error/403";
	
	//Methods
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
    	
    	Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    	
    	if (null != status) {
    		Integer statusCode = Integer.valueOf(status.toString());
    		
        	LOGGER.info("Se ha producido un error " + statusCode);
    		
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return ERROR_404_VIEW;
            } else if (statusCode == HttpStatus.FORBIDDEN.value()){
            	return ERROR_403_VIEW;
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return ERROR_500_VIEW;
            }
    	}

        return ERROR_VIEW;
    }

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

}
