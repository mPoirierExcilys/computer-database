package com.excilys.cdb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@Repository(value="computerJpaDao")
public class ComputerJpaDao implements AbstractJpaDao<Computer>{
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	@Override
	public Computer create(Computer obj) {
		em.persist(obj);
		return obj;
	}

	@Override
	public Computer find(Integer id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Computer> criteriaQuery = cb.createQuery(Computer.class);
		Root<Computer> root = criteriaQuery.from(Computer.class);
		
		Predicate idPredicate = cb.equal(root.get("idComputer"), id);
		criteriaQuery.where(idPredicate);
		
		TypedQuery<Computer> query = em.createQuery(criteriaQuery);
		try {
			return query.getSingleResult();
		}catch(NoResultException e) {
			return new Computer();
		}	
	}

	@Override
	public List<Computer> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Computer> criteriaQuery = cb.createQuery(Computer.class);
		Root<Computer> root = criteriaQuery.from(Computer.class);
		criteriaQuery.select(root);
		TypedQuery <Computer> computers = em.createQuery(criteriaQuery);
		try {
			return computers.getResultList();
		}catch(NoResultException e) {
			return new ArrayList<Computer>();
		}
		
	}
	
	@Transactional
	@Override
	public Computer update(Computer obj) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<Computer> updateQuery = cb.createCriteriaUpdate(Computer.class);
		Root<Computer> root = updateQuery.from(Computer.class);
		updateQuery.set(root.get("name"), obj.getName());
		if(obj.getIntroduced() != null) {
			updateQuery.set(root.get("introduced"),obj.getIntroduced());
		}
		if(obj.getDiscontinued() != null) {
			updateQuery.set(root.get("discontinued"),obj.getDiscontinued());
		}
		if(obj.getCompany() != null && obj.getCompany().getIdCompany() != null) {
			updateQuery.set(root.get("company"), obj.getCompany());
		}
		updateQuery.where(cb.equal(root.get("idComputer"), obj.getIdComputer()));
		em.createQuery(updateQuery).executeUpdate();
		return obj;
	}
	
	@Transactional
	@Override
	public void delete(Integer id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<Computer> deleteQuery = cb.createCriteriaDelete(Computer.class);
		Root<Computer> root = deleteQuery.from(Computer.class);
		deleteQuery.where(cb.equal(root.get("idComputer"), id));
		em.createQuery(deleteQuery).executeUpdate();
	}

	@Override
	public List<Computer> findBetween(Integer offset, Page page) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Computer> criteriaQuery = cb.createQuery(Computer.class);
		Root<Computer> root = criteriaQuery.from(Computer.class);
		criteriaQuery.select(root);
		if(page.getAscending().equals("DESC")) {
			if(getAttribute(page).equals("company")) {
				criteriaQuery.orderBy(cb.desc(root.get(getAttribute(page)).get("name")));
			}
			else {
				criteriaQuery.orderBy(cb.desc(root.get(getAttribute(page))));
			}
		}
		else {
			if(getAttribute(page).equals("company")) {
				criteriaQuery.orderBy(cb.asc(root.get(getAttribute(page)).get("name")));
			}
			else {
				criteriaQuery.orderBy(cb.asc(root.get(getAttribute(page))));
			}
		}
		TypedQuery <Computer> computers = em.createQuery(criteriaQuery).setFirstResult(offset).setMaxResults(page.getItemsByPage());
		try {
			return computers.getResultList();
		}catch(NoResultException e) {
			return new ArrayList<Computer>();
		}		
	}

	@Override
	public Integer count() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
		Root<Computer> root = criteriaQuery.from(Computer.class);
		criteriaQuery.select(cb.count(root));	
		try {
			return em.createQuery(criteriaQuery).getSingleResult().intValue();
		}catch(NoResultException e) {
			return 0;
		}
	}

	@Override
	public List<Computer> findBetweenWithSearch(Integer offset, Page page, String search) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Computer> criteriaQuery = cb.createQuery(Computer.class);
		Root<Computer> root = criteriaQuery.from(Computer.class);
		Join<Computer,Company> leftJoin = root.join("company",JoinType.LEFT);
		Predicate computerName = cb.like(root.get("name"), "%"+search+"%");
		Predicate companyName = cb.like(leftJoin.get("name"), "%"+search+"%");
		criteriaQuery.select(root).where(cb.or(computerName,companyName));
		if(page.getAscending().equals("DESC")) {
			if(getAttribute(page).equals("company")) {
				criteriaQuery.orderBy(cb.desc(root.get(getAttribute(page)).get("name")));
			}
			else {
				criteriaQuery.orderBy(cb.desc(root.get(getAttribute(page))));
			}
		}
		else {
			if(getAttribute(page).equals("company")) {
				criteriaQuery.orderBy(cb.asc(root.get(getAttribute(page)).get("name")));
			}
			else {
				criteriaQuery.orderBy(cb.asc(root.get(getAttribute(page))));
			}
		}
		TypedQuery <Computer> computers = em.createQuery(criteriaQuery).setFirstResult(offset).setMaxResults(page.getItemsByPage());
		try {
			return computers.getResultList();
		}catch(NoResultException e) {
			return new ArrayList<Computer>();
		}
	}

	@Override
	public Integer countSearch(String search) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
		Root<Computer> root = criteriaQuery.from(Computer.class);
		Join<Computer,Company> leftJoin = root.join("company",JoinType.LEFT);
		Predicate computerName = cb.like(root.get("name"), "%"+search+"%");
		Predicate companyName = cb.like(leftJoin.get("name"), "%"+search+"%");
		criteriaQuery.select(cb.count(root)).where(cb.or(computerName,companyName));
		try {
			return em.createQuery(criteriaQuery).getSingleResult().intValue();
		}catch(NoResultException e) {
			return 0;
		}
	}

	private String getAttribute(Page page) {
		String order="";
		switch(page.getOrder()){
			case"computer.name":
				order="name";
				break;
			case"computer.introduced":
				order="introduced";
				break;
			case"computer.discontinued":
				order="discontinued";
				break;
			case"cp.name":
				order="company";
				break;
			default:
				order="idComputer";
				break;
		}
		return order;
	}

}
