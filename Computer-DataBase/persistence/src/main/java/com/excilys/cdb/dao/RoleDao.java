package com.excilys.cdb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Role;

@Repository
public class RoleDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Role> findAll(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Role> criteriaQuery = cb.createQuery(Role.class);
		Root<Role> root = criteriaQuery.from(Role.class);
		criteriaQuery.select(root);
		TypedQuery<Role> roles = em.createQuery(criteriaQuery);
		try {
			return roles.getResultList();
		}catch(NoResultException e) {
			return new ArrayList<Role>();
		}
	}
}
