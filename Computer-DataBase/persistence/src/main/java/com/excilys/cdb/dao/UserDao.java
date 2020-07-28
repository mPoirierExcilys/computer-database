package com.excilys.cdb.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.User;

@Repository
public class UserDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public Optional<User> findByName(String name){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		Predicate idPredicate = cb.equal(root.get("name"), name);
		criteriaQuery.where(idPredicate);
		
		TypedQuery<User> query = em.createQuery(criteriaQuery);
		return Optional.ofNullable(query.getSingleResult());
	}
}
