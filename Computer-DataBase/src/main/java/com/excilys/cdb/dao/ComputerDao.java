package com.excilys.cdb.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.excilys.cdb.dao.mappers.ComputerRowMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@Repository
public class ComputerDao extends AbstractDao<Computer>{
	
	private static final String INSERT_SQL = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES(:name,:introduced,:discontinued,:company_id)";
	
	private static final String UPDATE_SQL = "UPDATE computer SET name = :name, introduced = :introduced, discontinued = :discontinued, company_id = :company_id WHERE id = :id";
	
	private static final String FIND_ALL_SQL = "SELECT computer.id,computer.name,introduced,discontinued,company_id,cp.name as companyName FROM computer LEFT JOIN company as cp on computer.company_id = cp.id ";
	
	private static final String FIND_SQL = "SELECT computer.id,computer.name,introduced,discontinued,company_id,cp.name as companyName FROM computer LEFT JOIN company cp on computer.company_id = cp.id WHERE computer.id = :computer.id ";
	
	private static final String DELETE_SQL = "DELETE FROM computer WHERE id = :id";
	
	private static final String COUNT_SQL = "SELECT COUNT(id) FROM computer";
	
	private static final String COUNT_SEARCH_SQL = "SELECT COUNT(computer.id) FROM computer LEFT JOIN company as cp on computer.company_id = cp.id WHERE computer.name like :search OR cp.name like :search";
	
	private String limitOrderSql(String order, String ascending) {
		return FIND_ALL_SQL+ "Order By "+order+" IS NULL, "+order+ " "+ascending+" LIMIT :offset,:nb";
	}
	
	private String limitSearchOrderSql(String order, String ascending) {
		return FIND_ALL_SQL + "WHERE computer.name like :search OR cp.name like :search Order By "+order+" IS NULL, "+order + " " +ascending+" LIMIT :offset,:nb";
	}
	
	//private Connector connector;
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	public ComputerDao(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**public ComputerDao(int h2) {
		this.connector = new ConnectionH2();
	}**/
	
	@Override
	public Computer create(Computer obj) {
		KeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("name", obj.getName())
				.addValue("introduced",obj.getIntroduced()==null?null:Date.valueOf(obj.getIntroduced()))
				.addValue("discontinued",obj.getDiscontinued()==null?null:Date.valueOf(obj.getDiscontinued()))
				.addValue("company_id",obj.getCompany()==null?null:obj.getCompany().getIdCompany());
		jdbcTemplate.update(INSERT_SQL, parameters, holder);
		obj.setIdComputer(holder.getKey().intValue());
		return obj;
	}

	@Override
	public Computer find(Integer id) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("computer.id", id);
		Computer computer = jdbcTemplate.queryForObject(FIND_SQL, parameters, new ComputerRowMapper());
		return computer;
	}

	@Override
	public List<Computer> findAll() {
		List<Computer> allComputer = new ArrayList<>();
		allComputer = jdbcTemplate.query(FIND_ALL_SQL, new ComputerRowMapper());
		return allComputer;
	}

	@Override
	public Computer update(Computer obj) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("name", obj.getName())
				.addValue("introduced",obj.getIntroduced()==null?null:Date.valueOf(obj.getIntroduced()))
				.addValue("discontinued",obj.getDiscontinued()==null?null:Date.valueOf(obj.getDiscontinued()))
				.addValue("company_id",obj.getCompany()==null?null:obj.getCompany().getIdCompany())
				.addValue("id", obj.getIdComputer());
		jdbcTemplate.update(UPDATE_SQL, parameters);
		return obj;
	}

	@Override
	public void delete(Integer id) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id", id);
		jdbcTemplate.update(DELETE_SQL, parameters);
	}


	@Override
	public List<Computer> findBetween(Integer offset, Page page) {
		List<Computer> allComputer = new ArrayList<>();
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("offset", offset)
				.addValue("nb", page.getItemsByPage());
		allComputer = jdbcTemplate.query(limitOrderSql(page.getOrder(), page.getAscending()), parameters, new ComputerRowMapper());
		return allComputer;
	}

	
	@Override
	public Integer count() {
		SqlParameterSource parameters = new MapSqlParameterSource();
		return jdbcTemplate.queryForObject(COUNT_SQL,parameters, Integer.class);
	}
	
	public List<Computer> findBetweenWithSearch(Integer offset, Page page, String search){
		List<Computer> allComputer = new ArrayList<>();
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("search", "%"+search+"%")
				.addValue("offset", offset)
				.addValue("nb", page.getItemsByPage());
		allComputer = jdbcTemplate.query(limitSearchOrderSql(page.getOrder(),page.getAscending()), parameters, new ComputerRowMapper());
		return allComputer;
	}
	
	public Integer countSearch(String search) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("search", "%"+search+"%");
		return jdbcTemplate.queryForObject(COUNT_SEARCH_SQL, parameters, Integer.class);
	}

}
