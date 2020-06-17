package com.excilys.cdb.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Company;

public class CompanyMapper extends AbstractMapper<Company>{

	@Override
	public Company resultToObject(ResultSet result) throws SQLException {
		Company company = null;
		if(result.first()) {
			company = new Company(result.getInt("id"), result.getString("name"));
		}
		return company;
	}

	@Override
	public List<Company> resultToList(ResultSet result) throws SQLException {
		List<Company> allCompany = new ArrayList<>();
		while(result.next()) {
			Company company = new Company(result.getInt("id"), result.getString("name"));
			allCompany.add(company);
		}
		return allCompany;
	}

}
