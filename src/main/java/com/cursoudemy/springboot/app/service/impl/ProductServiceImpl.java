package com.cursoudemy.springboot.app.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursoudemy.springboot.app.model.dao.ProductDao;
import com.cursoudemy.springboot.app.model.entity.Product;
import com.cursoudemy.springboot.app.service.InvoiceService;
import com.cursoudemy.springboot.app.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private InvoiceService invoiceService;

	@Override
	@Transactional(readOnly=true)
	public List<Product> findAll() {
		return (List<Product>) productDao.findAll();
	}
	
	@Override
	public Page<Product> getProductsByPage(int numPage, int maxPages) {
		Pageable pageRequested = PageRequest.of(numPage, maxPages);
		return productDao.findAll(pageRequested);
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
	@Transactional
	public void create(List<Product> products) {
		for (Product product : products) {
			product.setCreatedAt(new Date());
		}
		productDao.saveAll(products);
	}
	
	@Override
	@Transactional
	public void update(Product product) {
		if(null != findById(product.getId())) {
			product.setUpdatedAt(new Date());
			productDao.save(product);	
		}
	}
	
	@Override
	@Transactional
	public void changeName(Long id, String name) {
		if(null != findById(id)) {
			Product foundProduct = findById(id);
			foundProduct.setName(name);
			foundProduct.setUpdatedAt(new Date());
			update(foundProduct);
		} else {
			throw new IllegalArgumentException("No existe ningún producto con esa ID");
		}
	}
	
	@Override
	@Transactional
	public void changePrice(Long id, Double price) {
		if(null != findById(id)) {
			Product foundProduct = findById(id);
			foundProduct.setPrice(price);
			foundProduct.setUpdatedAt(new Date());
			update(foundProduct);
		} else {
			throw new IllegalArgumentException("No existe ningún producto con esa ID");
		}
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		// NO ES BUENA IDEA USAR ESTE MÉTODO PORQUE AFECTA SERIAMENTE A LAS FACTURAS.
		// SE CREARÁ UN MÉTODO PARA DESACTIVAR PRODUCTOS EN LUGAR DE BORRARLOS Y ALGÚN
		// TIPO DE FILTRO PARA DISTINGUIRLOS EN LA LISTA.
		if(null != findById(id)) {
			productDao.deleteById(id);
			invoiceService.filterForDeletedProduct(id); // Borramos las líneas de factura correspondientes y las facturas que se queden sin líneas
		} else {
			throw new IllegalArgumentException("No existe ningún producto con esa ID");
		}
	}
	
	@Override
	@Transactional
	public void deleteAll() {
		productDao.deleteAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<Product> findByName(String name) {
		return productDao.findByName(name);
	}
}
