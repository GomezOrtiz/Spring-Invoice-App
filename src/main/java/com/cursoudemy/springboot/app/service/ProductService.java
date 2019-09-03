package com.cursoudemy.springboot.app.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cursoudemy.springboot.app.model.entity.Product;

public interface ProductService {
	
	public List<Product> findAll();
	
	public Product findById(Long id);
	
	public List<Product> findByName(String name);
	
	public void create(Product product);
	
	public void create(List<Product> products);
	
	public void update(Product product);

	public void changeName(Long id, String name) throws Exception;

	public void changePrice(Long id, Double price) throws Exception;
	
	public void delete(Long id) throws Exception;

	public void deleteAll();

	public Page<Product> getProductsByPage(int page, int maxResultsPerPage);
}
