package com.fakecorp.invoicing.app.model.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fakecorp.invoicing.app.model.entity.Client;

public interface ClientDao extends PagingAndSortingRepository<Client, Long>{
	
    public Page<Client> findAllByNameContainsIgnoreCase(String name, Pageable pageable);
    
    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.invoices i WHERE c.id = ?1")
    public Optional<Client> findById(Long id);
    
    @Query("SELECT c FROM Client c WHERE c.name LIKE %?1%")
	public List<Client> findByName(String query);
}
