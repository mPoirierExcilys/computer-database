package com.excilys.cdb.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractMapper<T> {
	
	public abstract T resultToObject(ResultSet result) throws SQLException;
	
	public abstract List<T> resultToList(ResultSet result) throws SQLException;
}
