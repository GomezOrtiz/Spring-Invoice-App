package com.cursoudemy.springboot.app.utils.forms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cursoudemy.springboot.app.model.entity.User;

@Component
public class UserFormValidator implements Validator {
	
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String ROLES = "roles";
	
	@Autowired
	private PasswordValidator passwordValidator;

	@Override
	public void validate(Object target, Errors errors) {
		
		User user = (User) target;
		
		validateNameAndRoles(errors, user);
				
		validatePassword(errors, user);
	}
	
	private void validateNameAndRoles(Errors errors, User user) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, USERNAME, "forms.user.errors.username.empty");

		if (user.getUsername().length() > 50) {
			errors.rejectValue(USERNAME, "forms.user.errors.username.invalid");
		}
		
		if(user.getRoles().size() == 0) {
				errors.rejectValue(ROLES, "forms.user.errors.roles.empty");
		}
	}
	
	private void validatePassword(Errors errors, User user) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, PASSWORD, "forms.user.errors.password.empty");

		if (!passwordValidator.validate(user.getPassword())) {
			errors.rejectValue(PASSWORD, "forms.user.errors.password.invalid");
		} 
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(User.class);
	}

}
