package com.excilys.cdb.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.sql.Connection;

import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ConnectionMysql;

public class ComputerDao extends AbstractDao<Computer>{
	
	private static String insertSql = "INSERT INTO computer (name) VALUES(?)";
	
	private static String findAllSql = "SELECT id,name,introduced,discontinued,company_id FROM computer";
	
	private static String findSql = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id = ?";
	
	private static String deleteSql = "DELETE FROM computer WHERE id = ?";
	
	private static String limitSql = "SELECT id,name,introduced,discontinued,company_id FROM computer LIMIT ?, ?";
	
	private static String countSql = "SELECT COUNT(id) FROM computer";
	
	@Override
	public Computer create(Computer obj) {
		Computer comp = new Computer();
		try(Connection connect = ConnectionMysql.getInstance();
			PreparedStatement prepare = connect.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
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
		try(Connection connect = ConnectionMysql.getInstance();
			PreparedStatement prepare = connect.prepareStatement(findSql)) {
			prepare.setInt(1, id);
			ResultSet result = prepare.executeQuery();
			computer = ComputerMapper.resultToObject(result);
		}catch(SQLException eSQL) {
			System.out.println("Error Getting computer");
			eSQL.printStackTrace();
		}
		return computer;
	}

	@Override
	public List<Computer> findAll() {
		List<Computer> allComputer = null;
		try(Connection connect = ConnectionMysql.getInstance();
			ResultSet result = connect.createStatement().executeQuery(findAllSql)) {
			allComputer = ComputerMapper.resultToList(result);
		}catch(SQLException eSQL) {
			System.out.println("Error Getting computers");
			eSQL.printStackTrace();
		}
		return allComputer;
	}

	@Override
	public Computer update(Computer obj) {
		try(Connection connect = ConnectionMysql.getInstance()) {
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
			
			connect.createStatement().executeUpdate(sqlRequest);
			obj = this.find(obj.getIdComputer());
		}catch(SQLException eSQL) {
			System.out.println("Error Update Computer");
			eSQL.printStackTrace();
		}
		return obj;
	}

	@Override
	public void delete(Computer obj) {
		try(Connection connect = ConnectionMysql.getInstance();
			PreparedStatement prepare = connect.prepareStatement(deleteSql)){
			prepare.setInt(1, obj.getIdComputer());
			prepare.executeUpdate();
		}catch(SQLException eSQL) {
			System.out.println("Error Delete Computer");
			eSQL.printStackTrace();
		}
	}


	@Override
	public List<Computer> findBetween(Integer offset, Integer nb) {
		List<Computer> allComputer = null;
		try(Connection connect = ConnectionMysql.getInstance();
			PreparedStatement prepare = connect.prepareStatement(limitSql)) {
			prepare.setInt(1, offset);
			prepare.setInt(2, nb);
			ResultSet result = prepare.executeQuery();
			allComputer = ComputerMapper.resultToList(result);
		}catch(SQLException eSQL) {
			System.out.println("Error Getting Computers between");
			eSQL.printStackTrace();
		}
		return allComputer;
	}


	@Override
	public Integer count() {
		Integer nb = 0;
		try(Connection connect = ConnectionMysql.getInstance();
			ResultSet result =connect.createStatement().executeQuery(countSql)) {
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
