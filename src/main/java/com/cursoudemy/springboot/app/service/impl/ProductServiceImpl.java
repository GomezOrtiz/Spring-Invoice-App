package com.cursoudemy.springboot.app.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursoudemy.springboot.app.model.dao.ProductDao;
import com.cursoudemy.springboot.app.model.entity.Product;
import com.cursoudemy.springboot.app.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao productDao;

	@Override
	@Transactional(readOnly=true)
	public List<Product> findAll() {
		return (List<Product>) productDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Product findById(Long id) {
		return productDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void create(Product product) {
		
		product.setCreatedAt(new Date());

		productDao.save(product);
	}
	
	@Override
	public void update(Product product) {
		
		product.setUpdatedAt(new Date());

		productDao.save(product);		
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		productDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Product> findByName(String name) {
		return productDao.findByName(name);
	}


}
