package com.fakecorp.invoicing.app.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fakecorp.invoicing.app.model.dao.InvoiceDao;
import com.fakecorp.invoicing.app.model.entity.Invoice;
import com.fakecorp.invoicing.app.model.entity.InvoiceItem;
import com.fakecorp.invoicing.app.model.entity.Product;
import com.fakecorp.invoicing.app.service.ClientService;
import com.fakecorp.invoicing.app.service.InvoiceService;
import com.fakecorp.invoicing.app.service.ProductService;

@Service
public class InvoiceServiceImpl implements InvoiceService {
	
	@Autowired
	private InvoiceDao invoiceDao;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ProductService productService;

	@Override
	@Transactional(readOnly=true)
	public List<Invoice> findAll() {
		return (List<Invoice>) invoiceDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Invoice findById(Long id) {
		return invoiceDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Invoice> findByClient(Long clientId) {
		if(null != clientService.findById(clientId)) {
			return invoiceDao.findByClientId(clientId);
		} else {
			return null;
		}
	}
	
	@Override
	@Transactional
	public void createWithItems(Invoice invoice, Long[] items, Integer[] amounts) {
		for (int i = 0; i < items.length; i++) {
			Product product = productService.findById(items[i]);
			InvoiceItem item = new InvoiceItem();
			item.setProduct(product);
			item.setAmount(amounts[i]);
			invoice.addItem(item);
		}
		create(invoice);
	}
	
	@Override
	@Transactional
	public void create(Invoice invoice) {
		invoice.setCreatedAt(new Date());
		invoiceDao.save(invoice);
	}

	@Override
	@Transactional
	public void update(Invoice invoice) {
		if(!invoiceDao.findById(invoice.getId()).isEmpty()) {
			invoiceDao.save(invoice);
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if(!invoiceDao.findById(id).isEmpty()) {
			invoiceDao.deleteById(id);
		}
	}
}
