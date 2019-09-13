package com.hackerrank.eshopping.product.dashboard.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.hackerrank.eshopping.product.dashboard.model.Product;
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
		Product product = (Product)target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"product.id","Produc Id is required");
		if(product.getId() != null && productRepository.existsById(product.getId())) {
			errors.rejectValue("product.id","Product Id already in use");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"product.name","Product Name is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"product.category","Product Category is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"product.retailPrice","Product Retail Price is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"product.availability","Product Availability is required");
		
	}
}
