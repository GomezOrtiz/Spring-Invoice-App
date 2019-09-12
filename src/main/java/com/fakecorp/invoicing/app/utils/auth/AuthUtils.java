package com.fakecorp.invoicing.app.utils.auth;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {
	
	public String getCurrentUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			return auth.getName();
		} else {
			return null;
		}
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
