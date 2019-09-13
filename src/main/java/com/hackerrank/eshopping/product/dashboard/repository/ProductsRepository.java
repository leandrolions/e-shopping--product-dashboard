package com.hackerrank.eshopping.product.dashboard.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hackerrank.eshopping.product.dashboard.model.Product;
/**
 * 
 * @author lleao 13092019
 *
 */ 

@Repository
public interface ProductsRepository extends PagingAndSortingRepository<Product, Long>{

	List<Product> findByCategoryIgnoreCase(String category,Sort page);
	
}
