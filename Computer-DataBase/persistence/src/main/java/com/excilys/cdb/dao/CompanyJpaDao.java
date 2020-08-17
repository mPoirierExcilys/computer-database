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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@Repository(value="companyJpaDao")
public class CompanyJpaDao implements AbstractJpaDao<Company>{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Company create(Company obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Company find(Integer id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Company> criteriaQuery = cb.createQuery(Company.class);
		Root<Company> root = criteriaQuery.from(Company.class);
		
		Predicate idPredicate = cb.equal(root.get("idCompany"), id);
		criteriaQuery.where(idPredicate);
		
		TypedQuery<Company> query = em.createQuery(criteriaQuery);
		try {
			return query.getSingleResult();
		}catch(NoResultException e) {
			return new Company();
		}
	}

	@Override
	public List<Company> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Company> criteriaQuery = cb.createQuery(Company.class);
		Root<Company> root = criteriaQuery.from(Company.class);
		criteriaQuery.select(root);
		TypedQuery<Company> companies = em.createQuery(criteriaQuery);
		try {
			return companies.getResultList();
		}catch(NoResultException e) {
			return new ArrayList<Company>();
		}
	}

	@Override
	public Company update(Company obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional
	@Override
	public void delete(Integer id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<Computer> deleteQuery = cb.createCriteriaDelete(Computer.class);
		Root<Computer> root = deleteQuery.from(Computer.class);
		deleteQuery.where(cb.equal(root.get("company").get("id"), id));
		em.createQuery(deleteQuery).executeUpdate();
		
		CriteriaDelete<Company> deleteQueryCompany = cb.createCriteriaDelete(Company.class);
		Root<Company> rootCompany = deleteQueryCompany.from(Company.class);
		deleteQueryCompany.where(cb.equal(rootCompany.get("idCompany"), id));
		em.createQuery(deleteQueryCompany).executeUpdate();
	}

	@Override
	public List<Company> findBetween(Integer offset, Page page) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Company> criteriaQuery = cb.createQuery(Company.class);
		Root<Company> root = criteriaQuery.from(Company.class);
		criteriaQuery.select(root);
		if(page.getAscending().equals("DESC")) {
			criteriaQuery.orderBy(cb.desc(root.get(getAttribute(page))));
		}
		else {
			criteriaQuery.orderBy(cb.asc(root.get(getAttribute(page))));
		}
		TypedQuery<Company> companies = em.createQuery(criteriaQuery).setFirstResult(offset).setMaxResults(page.getItemsByPage());
		try {
			return companies.getResultList();
		}catch(NoResultException e) {
			return new ArrayList<Company>();
		}
	}

	@Override
	public Integer count() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
		Root<Company> root = criteriaQuery.from(Company.class);
		criteriaQuery.select(cb.count(root));
		try {
			return em.createQuery(criteriaQuery).getSingleResult().intValue();
		}catch(NoResultException e) {
			return 0;
		}
	}

	@Override
	public List<Company> findBetweenWithSearch(Integer offset, Page page, String search) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Company> criteriaQuery = cb.createQuery(Company.class);
		Root<Company> root = criteriaQuery.from(Company.class);
		Predicate companyName = cb.like(root.get("name"), "%"+search+"%");
		criteriaQuery.select(root).where(companyName);
		if(page.getAscending().equals("DESC")) {
			criteriaQuery.orderBy(cb.desc(root.get(getAttribute(page))));
		}
		else {
			criteriaQuery.orderBy(cb.asc(root.get(getAttribute(page))));
		}
		TypedQuery<Company> companies = em.createQuery(criteriaQuery).setFirstResult(offset).setMaxResults(page.getItemsByPage());
		try {
			return companies.getResultList();
		}catch(NoResultException e) {
			return new ArrayList<Company>();
		}
	}

	@Override
	public Integer countSearch(String search) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
		Root<Company> root = criteriaQuery.from(Company.class);
		Predicate companyName = cb.like(root.get("name"), "%"+search+"%");
		criteriaQuery.select(cb.count(root)).where(companyName);
		try {
			return em.createQuery(criteriaQuery).getSingleResult().intValue();
		}catch(NoResultException e) {
			return 0;
		}
	}
	
	private String getAttribute(Page page) {
		String order="";
		switch(page.getOrder()){
			case"cp.name":
				order="name";
				break;
			default:
				order="idCompany";
				break;
		}
		return order;
	}

}
