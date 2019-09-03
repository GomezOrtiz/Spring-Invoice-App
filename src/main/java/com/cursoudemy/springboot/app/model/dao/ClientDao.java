package com.cursoudemy.springboot.app.model.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cursoudemy.springboot.app.model.entity.Client;

public interface ClientDao extends PagingAndSortingRepository<Client, Long>{
    Page<Client> findAllByNameContainsIgnoreCase(String name, Pageable pageable);
}
