package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mappers.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.ConnectionMysql;

public class CompanyDao extends AbstractDao<Company> {
	
	private static String findAllSql = "SELECT id,name FROM company";
	
	private static String findSql = "SELECT id,name FROM company WHERE id = ?";
	
	private static String limitSql = "SELECT id,name FROM company LIMIT ?, ?";
	
	private static String countSql = "SELECT COUNT(id) FROM company";
	
	private static Logger logger = LoggerFactory.getLogger(CompanyDao.class);

	@Override
	public Company find(Integer id) {
		Company company = null;
		try(Connection connect = ConnectionMysql.getInstance();
			PreparedStatement prepare = connect.prepareStatement(findSql)){	
			prepare.setInt(1, id);
			ResultSet result = prepare.executeQuery();
			if(result.first()) {
				company = CompanyMapper.resultToObject(result);
			}	
		}catch(SQLException eSQL) {
			logger.error("Error Getting campany");
			eSQL.printStackTrace();
		}
		return company;
	}

	@Override
	public List<Company> findAll() {
		List<Company> allCompany = new ArrayList<>();
		try(Connection connect = ConnectionMysql.getInstance();
			ResultSet result = connect.createStatement().executeQuery(findAllSql)) {
			while(result.next()) {
				Company company = CompanyMapper.resultToObject(result);
				allCompany.add(company);
			}
		}catch(SQLException eSQL) {
			logger.error("Error Getting campanies");
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
		List<Company> allCompanies = new ArrayList<>();
		try (Connection connect = ConnectionMysql.getInstance();
			PreparedStatement prepare = connect.prepareStatement(limitSql)){
			prepare.setInt(1, offset);
			prepare.setInt(2, nb);
			ResultSet result = prepare.executeQuery();
			while(result.next()) {
				Company company = CompanyMapper.resultToObject(result);
				allCompanies.add(company);
			}
		}catch(SQLException eSQL) {
			logger.error("Error getting companies between");
			eSQL.printStackTrace();
		}
		return allCompanies;
	}

	@Override
	public Integer count() {
		Integer nb = 0;
		try(Connection connect = ConnectionMysql.getInstance();
			ResultSet result = connect.createStatement().executeQuery(countSql)) {
			if(result.first()) {
				nb =result.getInt(1);
			}
		}catch(SQLException eSQL) {
			logger.error("Error counting Computers");
			eSQL.printStackTrace();
		}
		return nb;
	}

}
