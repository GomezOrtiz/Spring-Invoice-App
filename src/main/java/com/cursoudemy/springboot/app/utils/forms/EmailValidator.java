package com.cursoudemy.springboot.app.utils.forms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class EmailValidator {

	private static final String EMAIL_PATTERN = "^([_A-Za-z0-9]+[-]?)*[_A-Za-z0-9]+((\\.[_A-Za-z0-9]+)+([-]?[_A-Za-z0-9]+)*)*@"
			+ "([A-Za-z0-9]+[-]?[A-Za-z0-9]+)+(\\.[A-Za-z0-9]+[-]?[A-Za-z0-9])*(\\.[A-Za-z]{2,})$";

	public boolean validate(String email) {

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);

		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

}