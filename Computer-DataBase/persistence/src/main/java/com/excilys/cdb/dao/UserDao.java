package com.excilys.cdb.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.model.User;

@Repository
public class UserDao {

	@PersistenceContext
	private EntityManager em;

	public Optional<User> findByName(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		Predicate idPredicate = cb.equal(root.get("name"), name);
		criteriaQuery.where(idPredicate);

		TypedQuery<User> query = em.createQuery(criteriaQuery);
		try {
			return Optional.ofNullable(query.getSingleResult());
		} catch (NoResultException e) {
			return Optional.ofNullable(query.getSingleResult());
		}
	}
	
	public Optional<User> findById(Integer id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		Predicate idPredicate = cb.equal(root.get("id"), id);
		criteriaQuery.where(idPredicate);

		TypedQuery<User> query = em.createQuery(criteriaQuery);
		try {
			return Optional.ofNullable(query.getSingleResult());
		} catch (NoResultException e) {
			return Optional.ofNullable(query.getSingleResult());
		}
	}
	
	public Optional<List<User>> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root);
		TypedQuery <User> Users = em.createQuery(criteriaQuery);
		try {
			return Optional.ofNullable(Users.getResultList());
		} catch(NoResultException e) {
			return Optional.ofNullable(null);
		}
	}

	@Transactional
	public User create(User user) {
		em.persist(user);
		return user;
	}

	@Transactional
	public User modify(User user, boolean isAdmin) {
		User preUser = this.findById(user.getId()).orElse(null);
		if (preUser == null || !isUserByNotIdAndByName(user.getId(), user.getName())) {
			return null;
		} else {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaUpdate<User> updateQuery = cb.createCriteriaUpdate(User.class);
			Root<User> root = updateQuery.from(User.class);
			if(isAdmin && user.getRoles().size() != 0) {
				updateQuery.set(root.get("roles"), user.getRoles());
			}
			if(user.getName() != null && !user.getName().equals("")) {
				updateQuery.set(root.get("name"), user.getName());
			}
			if (user.getPassword() != null) {
				updateQuery.set(root.get("password"), user.getPassword());
			}
			updateQuery.where(cb.equal(root.get("id"), preUser.getId()));
			em.createQuery(updateQuery).executeUpdate();
			return user;
		}
	}
	
	public boolean isUserByNotIdAndByName(Integer id, String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		Predicate namePredicate = cb.equal(root.get("name"), name);
		Predicate notIdPredicate = cb.equal(root.get("id"), id).not();
		criteriaQuery.where(namePredicate, notIdPredicate);

		TypedQuery<User> query = em.createQuery(criteriaQuery);
		int nombre = query.getResultList().size();
		System.out.println("Test result size :  " + nombre);
		return nombre == 0;
	}
}
