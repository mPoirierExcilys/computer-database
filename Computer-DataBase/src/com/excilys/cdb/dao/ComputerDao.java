package com.excilys.cdb.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.model.Computer;

public class ComputerDao extends AbstractDao<Computer>{
	
	private ComputerMapper computerMapper;
	
	public ComputerDao() {
		computerMapper = new ComputerMapper();
	}
	

	@Override
	public Computer create(Computer obj) {
		Computer comp = new Computer();
		try {
			PreparedStatement prepare = this.connect.prepareStatement("INSERT INTO computer (name) VALUES(?)"
					, Statement.RETURN_GENERATED_KEYS);
			prepare.setString(1, obj.getName());
			prepare.executeUpdate();
			ResultSet resultKeys = prepare.getGeneratedKeys();
			if(resultKeys.first()) {
				Integer computerId = resultKeys.getInt(1);
				obj.setIdComputer(computerId);
				this.update(obj);
				comp = this.find(computerId);
			}		
		}catch(SQLException eSQL) {
			System.out.println("Error Created Computer");
			System.out.println(eSQL.getMessage());
			eSQL.printStackTrace();
		}
		return comp;
	}

	@Override
	public Computer find(Integer id) {
		Computer computer = null;
		try {
			ResultSet result = this.connect.createStatement().executeQuery(
										"SELECT * FROM computer WHERE id = " + id);
			computer = computerMapper.resultToObject(result);
		}catch(SQLException eSQL) {
			System.out.println("Error Getting computer");
			eSQL.printStackTrace();
		}
		return computer;
	}

	@Override
	public List<Computer> findAll() {
		List<Computer> allComputer = null;
		try {
			ResultSet result = this.connect.createStatement().executeQuery(
                    		"SELECT * FROM computer");
			allComputer = computerMapper.resultToList(result);
		}catch(SQLException eSQL) {
			System.out.println("Error Getting computers");
			eSQL.printStackTrace();
		}
		return allComputer;
	}

	@Override
	public Computer update(Computer obj) {
		try {
			String sqlRequest = "UPDATE computer SET name = '" + obj.getName() + "'";
			if(obj.getIntroduced() != null) {
				sqlRequest += ", introduced = '" + Date.valueOf(obj.getIntroduced()) + "'";
			}			
			if(obj.getDiscontinued() != null) {
				sqlRequest += ", discontinued = '" + Date.valueOf(obj.getDiscontinued()) + "'";
			}		
			if(obj.getCompanyId() != null) {
				sqlRequest += ", company_id = '" +obj.getCompanyId() + "'"; 
			}		
			sqlRequest += " WHERE id = "+ obj.getIdComputer();
			
			this.connect.createStatement().executeUpdate(sqlRequest);
			obj = this.find(obj.getIdComputer());
		}catch(SQLException eSQL) {
			System.out.println("Error Update Computer");
			eSQL.printStackTrace();
		}
		return obj;
	}

	@Override
	public void delete(Computer obj) {
		try {
			this.connect.createStatement().executeUpdate("DELETE FROM computer WHERE id = "+obj.getIdComputer());
		}catch(SQLException eSQL) {
			System.out.println("Error Delete Computer");
			eSQL.printStackTrace();
		}
	}


	@Override
	public List<Computer> findBetween(Integer offset, Integer nb) {
		List<Computer> allComputer = null;
		try {
			ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM computer LIMIT "+offset+", "+nb);
			allComputer = computerMapper.resultToList(result);
		}catch(SQLException eSQL) {
			System.out.println("Error Getting Computers between");
			eSQL.printStackTrace();
		}
		return allComputer;
	}


	@Override
	public Integer count() {
		Integer nb = 0;
		try {
			ResultSet result = this.connect.createStatement().executeQuery("SELECT COUNT(*) FROM computer");
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
