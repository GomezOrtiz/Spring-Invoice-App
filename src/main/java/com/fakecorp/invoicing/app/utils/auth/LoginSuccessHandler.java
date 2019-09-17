package com.fakecorp.invoicing.app.utils.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fakecorp.invoicing.app.service.UserService;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
		
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthUtils authUtils;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {		
		
		userService.saveLastConnection(authUtils.getLoggedInUser());
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
}
