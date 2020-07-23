package com.excilys.cdb.dao;

import java.util.List;

import com.excilys.cdb.model.Page;

public interface AbstractJpaDao<T> {
	
	public T create(T obj);
	
	public T find(Integer id);
	
	public List<T> findAll();
	
	public T update(T obj);
	
	public void delete(Integer id);
	
	public List<T> findBetween(Integer offset, Page page);
	
	public Integer count();
	
	public List<T> findBetweenWithSearch(Integer offset, Page page, String search);
	
	public Integer countSearch(String search);

}
