package com.cursoudemy.springboot.app.utils.forms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cursoudemy.springboot.app.model.Client;

@Component
public class ClientFormValidator implements Validator {
	
	private static final String NAME = "name";
	private static final String SURNAME = "surname";
	private static final String EMAIL = "email";
	
	@Autowired
	private EmailValidator emailValidator;

	@Override
	public void validate(Object target, Errors errors) {
		
		Client client = (Client) target;
		
		validateNameAndSurname(errors, client);
		
		validateEmail(errors, client);
	}
	
	private void validateNameAndSurname(Errors errors, Client client) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, NAME, "forms.errors.client.name.empty");

		if (client.getName().length() > 50) {
			errors.rejectValue(NAME, "forms.errors.client.name.invalid");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, SURNAME, "forms.errors.client.surname.empty");

		if (client.getSurname().length() > 50) {
			errors.rejectValue(SURNAME, "forms.errors.client.surname.invalid");
		}
	}
	
	private void validateEmail(Errors errors, Client client) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, EMAIL, "forms.errors.client.email.empty");

		if (!emailValidator.validate(client.getEmail())) {
			errors.rejectValue(EMAIL, "forms.errors.client.email.invalid");
		} 
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Client.class);
	}

}
