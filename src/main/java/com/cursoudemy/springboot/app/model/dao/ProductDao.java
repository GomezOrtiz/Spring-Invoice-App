package com.cursoudemy.springboot.app.model.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cursoudemy.springboot.app.model.entity.Product;

public interface ProductDao extends CrudRepository<Product, Long> {
	
	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1% AND p.discontinued = false")
	public List<Product> findByName(String query);

	public Page<Product> findAll(Pageable pageRequested);
	
	public Page<Product> findAllByNameContainsIgnoreCase(String name, Pageable pageRequested);
}
