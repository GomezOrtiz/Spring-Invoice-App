package com.fakecorp.invoicing.app.utils.forms;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.fakecorp.invoicing.app.model.entity.Client;
import com.fakecorp.invoicing.app.model.entity.Product;

@Component
public class ProductFormValidator implements Validator {
	
	private static final String NAME = "name";
	private static final String PRICE = "price";

	@Override
	public void validate(Object target, Errors errors) {
		
		Product product = (Product) target;
		
		validateName(errors, product);	
		validatePrice(errors, product);		

	}
	
	private void validateName(Errors errors, Product product) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, NAME, "forms.product.errors.name.empty");

		if (product.getName().length() > 50) {
			errors.rejectValue(NAME, "forms.product.errors.name.invalid");
		}
	}
	
	private void validatePrice(Errors errors, Product product) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, PRICE, "forms.product.errors.price.empty");
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Client.class);
	}

}
