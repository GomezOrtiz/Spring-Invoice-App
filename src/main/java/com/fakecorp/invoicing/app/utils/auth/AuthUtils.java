package com.fakecorp.invoicing.app.utils.auth;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fakecorp.invoicing.app.model.dao.UserDao;
import com.fakecorp.invoicing.app.model.entity.User;

@Component
public class AuthUtils {
	
	@Autowired
	private UserDao userDao;
	
	public String getCurrentUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			return auth.getName();
		} else {
			return null;
		}
	}
	
	public User getLoggedInUser() {
		return userDao.findByUsername(getCurrentUserName());
	}
	
	public boolean hasRole(String role) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication auth = context.getAuthentication();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		if(null != context && null != auth && null != authorities) {
			for (GrantedAuthority authority : authorities) {
				if(role.equals(authority.getAuthority())) {
					return true;
				}
			}
		}		
		return false;
	}
	
	public boolean isAdmin() {
		return hasRole("ROLE_ADMIN");
	}

	
}
