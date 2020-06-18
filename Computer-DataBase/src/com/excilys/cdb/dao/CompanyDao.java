package com.excilys.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.mappers.CompanyMapper;
import com.excilys.cdb.model.Company;

public class CompanyDao extends AbstractDao<Company> {
	
	private static String findAllSql = "SELECT id,name FROM company";
	
	private static String findSql = "SELECT id,name FROM company WHERE id = ?";
	
	private static String limitSql = "SELECT id,name FROM company LIMIT ?, ?";
	
	private static String countSql = "SELECT COUNT(id) FROM company";

	@Override
	public Company find(Integer id) {
		Company company = null;
		try (PreparedStatement prepare = this.connect.prepareStatement(findSql)){	
			prepare.setInt(1, id);
			ResultSet result = prepare.executeQuery();
			//ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM company WHERE id = " + id);
			company = CompanyMapper.resultToObject(result);
		}catch(SQLException eSQL) {
			System.out.println("Error Getting campany");
			eSQL.printStackTrace();
		}
		return company;
	}

	@Override
	public List<Company> findAll() {
		List<Company> allCompany = null;
		try(ResultSet result = this.connect.createStatement().executeQuery(findAllSql)) {
			allCompany = CompanyMapper.resultToList(result);
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

	@Override
	public List<Company> findBetween(Integer offset, Integer nb) {
		List<Company> allCompanies = null;
		try (PreparedStatement prepare = this.connect.prepareStatement(limitSql)){
			prepare.setInt(1, offset);
			prepare.setInt(2, nb);
			ResultSet result = prepare.executeQuery();
			//ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM company LIMIT "+offset+", "+nb);
			allCompanies = CompanyMapper.resultToList(result);
		}catch(SQLException eSQL) {
			System.out.println("Error getting companies between");
			eSQL.printStackTrace();
		}
		return allCompanies;
	}

	@Override
	public Integer count() {
		Integer nb = 0;
		try(ResultSet result = this.connect.createStatement().executeQuery(countSql)) {
			if(result.first()) {
				nb =result.getInt(1);
			}
		}catch(SQLException eSQL) {
			System.out.println("Error counting Computers");
			eSQL.printStackTrace();
		}
		return nb;
	}

}
