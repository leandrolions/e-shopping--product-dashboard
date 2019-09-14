package com.hackerrank.eshopping.product.dashboard.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hackerrank.eshopping.product.dashboard.exceptions.ProductConstraintViolationException;
import com.hackerrank.eshopping.product.dashboard.exceptions.ProductNotFoundException;
import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.repository.ProductsRepository;
/**
 * 
 * @author lleao 13092019
 *
 */
@Service
public class ProductsServices {
	
	@Autowired
	ProductsRepository productRepository;
	
//	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public void saveProducts(Product product) throws ProductConstraintViolationException {
		if(productRepository.existsById(product.getId()))
			throw new ProductConstraintViolationException();
		productRepository.save(product);
	}
	
	public void updateProduct(Long productId,Product product)throws ProductNotFoundException{
		Optional<Product> productOptinal = productRepository.findById(productId);
		Product productPresent = productOptinal.orElseThrow(ProductNotFoundException::new);
		productPresent.mergeProduct(product);
		productRepository.save(productPresent);
	}
	
	public Product getProductsById(Long productId) throws ProductNotFoundException {
		Optional<Product> productOptinal = productRepository.findById(productId);
		return productOptinal.orElseThrow(ProductNotFoundException::new);
	}
	
	public List<Product> listProductsByCategory(String category) throws ProductNotFoundException{
		Sort sorted = Sort.by("availability").descending().and(Sort.by("discountedPrice").ascending().and(Sort.by("id").ascending()));
		List<Product> productsList = productRepository.findByCategoryIgnoreCase(category, sorted);
		if(productsList.isEmpty())
			throw new ProductNotFoundException();
		return productsList;
	}
	
	public List<Product> listProductsByCategoryAndAvailability(String category,Boolean availability) throws ProductNotFoundException{
		Sort sorted = Sort.by("discountPercentage").descending().and(Sort.by("discountedPrice").ascending().and(Sort.by("id").ascending()));
		List<Product> productsList = productRepository.findByCategoryIgnoreCaseAndAvailability(category, availability,sorted);
		if(productsList.isEmpty())
			throw new ProductNotFoundException();
		return productsList;
	}
	public List<Product> listAllProducts(){
		return StreamSupport.stream(
				productRepository.findAll(Sort.by("id").ascending())
				.spliterator(),false)
				.collect(Collectors.toList());
	}
}
