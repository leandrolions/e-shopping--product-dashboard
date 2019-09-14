package com.hackerrank.eshopping.product.dashboard.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.hackerrank.eshopping.product.dashboard.repository.ProductsRepository;

@Component
public class ProductsPersistValidator implements Validator{
	
	@Autowired
	ProductsRepository productRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return ProductsPersistValidator.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors,"id","NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name","NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"category","NotEmpty");
		ValidationUtils.rejectIfEmpty(errors,"retailPrice","NotEmpty");
		ValidationUtils.rejectIfEmpty(errors,"availability","NotEmpty");
		
	}
}
