package com.cursoudemy.springboot.app.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cursoudemy.springboot.app.model.entity.Client;
import com.cursoudemy.springboot.app.model.entity.Invoice;

public interface InvoiceDao extends CrudRepository<Invoice, Long>{
	public List<Invoice> findByClient(Client client);
}
