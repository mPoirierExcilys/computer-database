package com.excilys.cdb.dao;

import java.util.List;

public abstract class AbstractDao<T> {
	
	public abstract T create(T obj);
	
	public abstract T find(Integer id);
	
	public abstract List<T> findAll();
	
	public abstract T update(T obj);
	
	public abstract void delete(Integer id);
	
	public abstract List<T> findBetween(Integer offset, Integer nb, String order, String ascending);
	
	public abstract Integer count();
}
