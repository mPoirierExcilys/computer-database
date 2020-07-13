package com.excilys.cdb.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerRowMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Computer computer = new Computer();
		computer.setIdComputer(rs.getInt("id"));
		computer.setName(rs.getString("name"));
		if(rs.getDate("introduced") != null) {
			computer.setIntroduced(rs.getDate("introduced").toLocalDate());
		}
		
		if(rs.getDate("discontinued") != null) {
			computer.setDiscontinued(rs.getDate("discontinued").toLocalDate());
		}
		
		if(rs.getInt("company_id") != 0) {
			Company company = new Company(rs.getInt("company_id"),rs.getString("companyName"));
			computer.setCompany(company);
		}
		return computer;
	}

}
