package com.cursoudemy.springboot.app.model.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cursoudemy.springboot.app.model.entity.Client;
import com.cursoudemy.springboot.app.model.entity.Invoice;

public interface InvoiceDao extends CrudRepository<Invoice, Long>{
	
	@Query("SELECT i FROM Invoice i JOIN FETCH i.client c JOIN FETCH i.items it JOIN FETCH it.product WHERE c = ?1")
	public List<Invoice> findByClient(Client client);
	
	@Query("SELECT i FROM Invoice i JOIN FETCH i.client c JOIN FETCH i.items it JOIN FETCH it.product WHERE i.id=?1")
	public Optional<Invoice> findById(Long id);
}
