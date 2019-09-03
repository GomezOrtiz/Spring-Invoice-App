package com.cursoudemy.springboot.app.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursoudemy.springboot.app.model.dao.InvoiceDao;
import com.cursoudemy.springboot.app.model.entity.Invoice;
import com.cursoudemy.springboot.app.service.ClientService;
import com.cursoudemy.springboot.app.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {
	
	@Autowired
	private InvoiceDao invoiceDao;
	
	@Autowired
	private ClientService clientService;

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
			return invoiceDao.findByClient(clientService.findById(clientId));
		} else {
			return null;
		}
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
