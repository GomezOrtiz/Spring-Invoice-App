package com.fakecorp.invoicing.app.service;

import java.util.List;

import com.fakecorp.invoicing.app.model.entity.Invoice;

public interface InvoiceService {
	
	public List<Invoice> findAll();
	
	public Invoice findById(Long id);
	
	public List<Invoice> findByClient(Long clientId);
	
	void createWithItems(Invoice invoice, Long[] items, Integer[] amounts);

	public void create(Invoice invoice);
	
	public void update(Invoice invoice);
	
	public void delete(Long id);
}
