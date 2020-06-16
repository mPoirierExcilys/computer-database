package com.excilys.cdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Company;

public class CompanyDao extends AbstractDao<Company> {

	@Override
	public Company find(Integer id) {
		Company company = new Company();
		try {
			ResultSet result = this.connect.createStatement().executeQuery(
										"SELECT * FROM company WHERE id = " + id);
			if(result.first()) {
				company = new Company(result.getInt("id"), result.getString("name"));
			}
		}catch(SQLException eSQL) {
			System.out.println("Error Getting campany");
			eSQL.printStackTrace();
		}
		return company;
	}

	@Override
	public List<Company> findAll() {
		List<Company> allCompany = new ArrayList<>();
		try {
			ResultSet result = this.connect.createStatement().executeQuery(
                    		"SELECT * FROM company");
			while(result.next()) {
				Company company = new Company(result.getInt("id"), result.getString("name"));
				allCompany.add(company);
			}
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
