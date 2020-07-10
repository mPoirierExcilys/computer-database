package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.dao.mappers.CompanyDaoMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.persistence.ConnectionH2;
import com.excilys.cdb.persistence.Connector;

@Repository
public class CompanyDao extends AbstractDao<Company> {
	
	private static final String findAllSql = "SELECT id,name FROM company";
	
	private static final String findSql = "SELECT id,name FROM company WHERE id = ?";
	
	private static final String limitSql = "SELECT id,name FROM company LIMIT ?, ?";
	
	private static final String countSql = "SELECT COUNT(id) FROM company";
	
	private static final String deleteComputerSql = "DELETE FROM computer WHERE company_id = ?";
	
	private static final String deleteCompanySql = "DELETE FROM company WHERE id = ?";
	
	private static final Logger logger = LoggerFactory.getLogger(CompanyDao.class);
	
	private Connector connector;
	
	@Autowired
	public CompanyDao(Connector connector) {
		this.connector = connector;
	}
	
	public CompanyDao(int h2) {
		this.connector = new ConnectionH2();
	}

	@Override
	public Company find(Integer id) {
		Company company = null;
		try(Connection connect = connector.getInstance();
			PreparedStatement prepare = connect.prepareStatement(findSql)){	
			prepare.setInt(1, id);
			ResultSet result = prepare.executeQuery();
			if(result.first()) {
				company = CompanyDaoMapper.resultToObject(result);
			}	
		}catch(SQLException eSQL) {
			logger.error("Error Getting campany",eSQL);
		}
		return company;
	}

	@Override
	public List<Company> findAll() {
		List<Company> allCompany = new ArrayList<>();
		try(Connection connect = connector.getInstance();
			ResultSet result = connect.createStatement().executeQuery(findAllSql)) {
			while(result.next()) {
				Company company = CompanyDaoMapper.resultToObject(result);
				allCompany.add(company);
			}
		}catch(SQLException eSQL) {
			logger.error("Error Getting campanies",eSQL);
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
	public void delete(Integer id) {
		try(Connection connect = connector.getInstance();
			PreparedStatement prepareComputers = connect.prepareStatement(deleteComputerSql);
			PreparedStatement prepare = connect.prepareStatement(deleteCompanySql)){
			connect.setAutoCommit(false);
			prepareComputers.setInt(1,id);
			prepareComputers.executeUpdate();
			prepare.setInt(1, id);
			prepare.executeUpdate();
			connect.commit();
		}catch(SQLException eSQL) {
			logger.error("Error deleting company with computers", eSQL);
		}
	}

	@Override
	public List<Company> findBetween(Integer offset, Page page) {
		List<Company> allCompanies = new ArrayList<>();
		try (Connection connect = connector.getInstance();
			PreparedStatement prepare = connect.prepareStatement(limitSql)){
			prepare.setInt(1, offset);
			prepare.setInt(2, page.getItemsByPage());
			ResultSet result = prepare.executeQuery();
			while(result.next()) {
				Company company = CompanyDaoMapper.resultToObject(result);
				allCompanies.add(company);
			}
		}catch(SQLException eSQL) {
			logger.error("Error getting companies between",eSQL);
		}
		return allCompanies;
	}

	@Override
	public Integer count() {
		Integer nb = 0;
		try(Connection connect = connector.getInstance();
			ResultSet result = connect.createStatement().executeQuery(countSql)) {
			if(result.next()) {
				nb =result.getInt(1);
			}
		}catch(SQLException eSQL) {
			logger.error("Error counting Computers",eSQL);
		}
		return nb;
	}

}
