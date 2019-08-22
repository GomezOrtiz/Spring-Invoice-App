package com.cursoudemy.springboot.app.service;

import java.util.List;

import com.cursoudemy.springboot.app.model.entity.Product;

public interface ProductService {
	
	public List<Product> findAll();
	
	public Product findById(Long id);
	
	public List<Product> findByName(String name);
	
	public void create(Product product);
	
	public void update(Product product);
	
	public void delete(Long id);
}
