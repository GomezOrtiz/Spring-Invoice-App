package com.cursoudemy.springboot.app.service;

import java.util.List;

import com.cursoudemy.springboot.app.model.entity.Invoice;

public interface InvoiceService {
	
	public List<Invoice> findAll();
	
	public Invoice findById(Long id);
	
	public List<Invoice> findByClient(Long clientId);
	
	public void filterForDeletedProduct(Long productId);
	
	public void create(Invoice invoice);
	
	public void update(Invoice invoice);
	
	public void delete(Long id);

}
