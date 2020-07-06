package com.excilys.cdb.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerDaoMapper {
	
	private ComputerDaoMapper() {
	}
	
	public static Computer resultToObject(ResultSet result) throws SQLException {
		Computer computer = null;
			computer = new Computer(result.getInt("id"), result.getString("name"));
			if(result.getDate("introduced") != null) {
				computer.setIntroduced(result.getDate("introduced").toLocalDate());
			}
			
			if(result.getDate("discontinued") != null) {
				computer.setDiscontinued(result.getDate("discontinued").toLocalDate());
			}
			
			if(result.getInt("company_id") != 0) {
				Company company = new Company(result.getInt("company_id"),result.getString("companyName"));
				computer.setCompany(company);
			}
		return computer;
	}

}
