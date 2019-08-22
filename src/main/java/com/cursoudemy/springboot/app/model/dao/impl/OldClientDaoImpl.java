package com.cursoudemy.springboot.app.model.dao.impl;

//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Repository;
//
//import com.cursoudemy.springboot.app.dao.ClientDao;
//import com.cursoudemy.springboot.app.model.Client;

//@Repository
//public class ClientDaoImpl implements ClientDao {
//	
//	@PersistenceContext
//	private EntityManager em;
//	
//	@SuppressWarnings("unchecked")
//	@Override	
//	public List<Client> findAll() {
//		return em.createQuery("from Client").getResultList();
//	}
//	
//	@Override	
//	public List<Client> findAll(Pageable pageable) {
//		return em.createQuery("from Client").getResultList();
//	}
//	
//	@Override
//	public Client findById(Long id) {
//		return em.find(Client.class, id);
//	}
//
//	@Override
//	public void insert(Client client) {
//		em.persist(client);
//	}
//	
//	@Override
//	public void update(Client client) {
//		em.merge(client);
//	}
//
//	@Override
//	public void delete(Long id) {
//		em.remove(findById(id));
//	}
//
//}
