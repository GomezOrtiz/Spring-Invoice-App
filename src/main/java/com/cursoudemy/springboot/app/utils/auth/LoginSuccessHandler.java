package com.cursoudemy.springboot.app.utils.auth;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private final Logger LOGGER = Logger.getLogger(getClass());

	@Autowired
	private AuthUtils authUtils;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {		
				
		String successMsg = "El usuario " + authUtils.getCurrentUserName() + " ha iniciado sesión con éxito a fecha de " + new Date() + ". ";
		
		if(authUtils.isAdmin()) {
			LOGGER.info(successMsg + "Tiene permisos de administración");
		} else {
			LOGGER.info(successMsg + "No tiene permisos de administración");
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
}
