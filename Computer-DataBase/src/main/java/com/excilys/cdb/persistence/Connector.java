package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

public interface Connector {
	public abstract Connection getInstance() throws SQLException;
}
