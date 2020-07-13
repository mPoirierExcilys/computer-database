package com.excilys.cdb.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.mappers.CompanyRowMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

@Repository
public class CompanyDao extends AbstractDao<Company> {
	
	private static final String FIND_ALL_SQL = "SELECT id,name FROM company";
	
	private static final String FIND_SQL = "SELECT id,name FROM company WHERE id = :id";
	
	private static final String LIMIT_SQL = "SELECT id,name FROM company LIMIT :offset, :nb";
	
	private static final String COUNT_SQL = "SELECT COUNT(id) FROM company";
	
	private static final String DELETE_COMPUTER_SQL = "DELETE FROM computer WHERE company_id = :company_id";
	
	private static final String DELETE_COMPANY_SQL = "DELETE FROM company WHERE id = :id";
	
	//private Connector connector;
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	public CompanyDao(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**public CompanyDao(int h2) {
		this.connector = new ConnectionH2();
	}**/

	@Override
	public Company find(Integer id) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", id);
		Company company = jdbcTemplate.queryForObject(FIND_SQL, parameters, new CompanyRowMapper());
		return company;
	}

	@Override
	public List<Company> findAll() {
		List<Company> allCompany = new ArrayList<>();
		SqlParameterSource parameters = new MapSqlParameterSource();
		allCompany = jdbcTemplate.query(FIND_ALL_SQL, parameters, new CompanyRowMapper());
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
	
	@Transactional
	@Override
	public void delete(Integer id) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("company_id", id);
		jdbcTemplate.update(DELETE_COMPUTER_SQL, parameters);
		SqlParameterSource parametersCompany = new MapSqlParameterSource()
				.addValue("id", id);
		jdbcTemplate.update(DELETE_COMPANY_SQL,parametersCompany);
	}

	@Override
	public List<Company> findBetween(Integer offset, Page page) {
		List<Company> allCompanies = new ArrayList<>();
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("offset", offset)
				.addValue("nb", page.getItemsByPage());
		allCompanies = jdbcTemplate.query(LIMIT_SQL, parameters, new CompanyRowMapper());
		return allCompanies;
	}

	@Override
	public Integer count() {
		SqlParameterSource parameters = new MapSqlParameterSource();
		return jdbcTemplate.queryForObject(COUNT_SQL,parameters, Integer.class);
	}

}
