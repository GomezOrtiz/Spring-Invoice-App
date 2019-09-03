package com.cursoudemy.springboot.app.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cursoudemy.springboot.app.model.entity.InvoiceItem;

public interface InvoiceItemDao extends CrudRepository<InvoiceItem, Long>{
	public List<InvoiceItem> findByProductId(Long productId);
	public void delete(InvoiceItem item);
}
