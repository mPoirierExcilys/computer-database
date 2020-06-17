package com.excilys.cdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.mappers.CompanyMapper;
import com.excilys.cdb.model.Company;

public class CompanyDao extends AbstractDao<Company> {
	
	private CompanyMapper companyMapper;
	
	public CompanyDao() {
		this.companyMapper = new CompanyMapper();
	}

	@Override
	public Company find(Integer id) {
		Company company = null;
		try {
			ResultSet result = this.connect.createStatement().executeQuery(
										"SELECT * FROM company WHERE id = " + id);
			company = companyMapper.resultToObject(result);
		}catch(SQLException eSQL) {
			System.out.println("Error Getting campany");
			eSQL.printStackTrace();
		}
		return company;
	}

	@Override
	public List<Company> findAll() {
		List<Company> allCompany = null;
		try {
			ResultSet result = this.connect.createStatement().executeQuery(
                    		"SELECT * FROM company");
			allCompany = companyMapper.resultToList(result);
		}catch(SQLException eSQL) {
			System.out.println("Error Getting campanies");
			eSQL.printStackTrace();
		}
		return allCompany;
	}

	@Override
	public Company create(Company obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Company update(Company obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Company obj) {
		// TODO Auto-generated method stub
		
	}

}
