package com.hackerrank.eshopping.product.dashboard.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.eshopping.product.dashboard.exceptions.ProductConstraintViolationException;
import com.hackerrank.eshopping.product.dashboard.exceptions.ProductNotFoundException;
import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.model.ProductUpdate;
import com.hackerrank.eshopping.product.dashboard.services.ProductsServices;
import com.hackerrank.eshopping.product.dashboard.validators.ProductsPersistValidator;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {

	@Autowired
	ProductsServices productsServices;
	@Autowired
	ProductsPersistValidator productValidador;
	
	@PostMapping
	public ResponseEntity<?> addProducts(@RequestBody Product product,BindingResult bindingResult){
		productValidador.validate(product, bindingResult);
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}
		try {
			productsServices.saveProducts(product);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (ProductConstraintViolationException e) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PutMapping("/{product_id}")
	public ResponseEntity<?> updateProductsById(@PathVariable("product_id") Long productId,
															@RequestBody ProductUpdate product){
		try {
			productsServices.updateProduct(productId, product);
			return ResponseEntity.ok().build();
		} catch (ProductNotFoundException e) {
			return ResponseEntity.badRequest().build();
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("/{product_id}")
	public ResponseEntity<?> getProductById(@PathVariable("product_id") Long productId){
		try {
			return ResponseEntity.ok(productsServices.getProductsById(productId));
		}catch (ProductNotFoundException e) {
			return ResponseEntity.notFound().build();
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping
	public ResponseEntity<List<Product>> getProductBy(@RequestParam(name = "category",required = false)
					String category,@RequestParam(name = "availability",required = false)Boolean availability){
		try {
			if(Optional.ofNullable(availability).isPresent() && Optional.ofNullable(category).isPresent()) {
				return ResponseEntity.ok(productsServices.listProductsByCategoryAndAvailability(category,availability));
			}else if(!Optional.ofNullable(availability).isPresent() && !Optional.ofNullable(category).isPresent()) {
				return ResponseEntity.ok(productsServices.listAllProducts());
			}else if(!Optional.ofNullable(availability).isPresent() && Optional.ofNullable(category).isPresent()) {
				return ResponseEntity.ok(productsServices.listProductsByCategory(category));
			}else {
				return ResponseEntity.badRequest().build();
			}
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
