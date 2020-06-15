package com.excilys.cdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Computer;

public class ComputerDao extends AbstractDao<Computer>{

	@Override
	public Computer create(Computer obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Computer find(Integer id) {
		Computer computer = new Computer();
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
										ResultSet.CONCUR_UPDATABLE).executeQuery(
										"SELECT * FROM computer WHERE id = " + id);
			if(result.first()) {
				computer = new Computer(result.getInt("id"), result.getString("name"));
				if(result.getDate("introduced") != null) {
					computer.setIntroduced(result.getDate("introduced"));
				}
				
				if(result.getDate("discontinued") != null) {
					computer.setDiscontinued(result.getDate("discontinued"));
				}
				
				if(result.getInt("company_id") != 0) {
					computer.setCompanyId(result.getInt("company_id"));
				}
			}
		}catch(SQLException eSQL) {
			System.out.println("Error Getting computer");
			eSQL.printStackTrace();
		}
		return computer;
	}

	@Override
	public List<Computer> findAll() {
		List<Computer> allComputer = new ArrayList<>();
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
                    										ResultSet.CONCUR_UPDATABLE).executeQuery(
                    		"SELECT * FROM computer");
			while(result.next()) {
				Computer computer = new Computer(result.getInt("id"), result.getString("name"));
				if(result.getDate("introduced") != null) {
					computer.setIntroduced(result.getDate("introduced"));
				}
				
				if(result.getDate("discontinued") != null) {
					computer.setDiscontinued(result.getDate("discontinued"));
				}
				
				if(result.getInt("company_id") != 0) {
					computer.setCompanyId(result.getInt("company_id"));
				}
				allComputer.add(computer);
			}
		}catch(SQLException eSQL) {
			System.out.println("Error Getting computers");
			eSQL.printStackTrace();
		}
		return allComputer;
	}

	@Override
	public Computer update(Computer obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Computer obj) {
		// TODO Auto-generated method stub
		
	}

}
