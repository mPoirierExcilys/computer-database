package com.excilys.cdb.dao;

import java.sql.Connection;
import java.util.List;

import com.excilys.cdb.persistence.ConnectionMysql;

public abstract class AbstractDao<T> {
	public  Connection connect = ConnectionMysql.getInstance();
	
	public abstract T create(T obj);
	
	public abstract T find(Integer id);
	
	public abstract List<T> findAll();
	
	public abstract T update(T obj);
	
	public abstract void delete(T obj);
	
	public abstract List<T> findBetween(Integer offset, Integer nb);
	
	public abstract Integer count();
}
