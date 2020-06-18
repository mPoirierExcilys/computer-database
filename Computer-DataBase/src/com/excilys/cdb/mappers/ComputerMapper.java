package com.excilys.cdb.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Computer;

public class ComputerMapper{

	public static Computer resultToObject(ResultSet result) throws SQLException {
		Computer computer = null;
		if(result.first()) {
			computer = new Computer(result.getInt("id"), result.getString("name"));
			if(result.getDate("introduced") != null) {
				computer.setIntroduced(result.getDate("introduced").toLocalDate());
			}
			
			if(result.getDate("discontinued") != null) {
				computer.setDiscontinued(result.getDate("discontinued").toLocalDate());
			}
			
			if(result.getInt("company_id") != 0) {
				computer.setCompanyId(result.getInt("company_id"));
			}
		}
		return computer;
	}

	public static List<Computer> resultToList(ResultSet result) throws SQLException {
		List<Computer> allComputer = new ArrayList<>();
		while(result.next()) {
			Computer computer = new Computer(result.getInt("id"), result.getString("name"));
			if(result.getDate("introduced") != null) {
				computer.setIntroduced(result.getDate("introduced").toLocalDate());
			}
			
			if(result.getDate("discontinued") != null) {
				computer.setDiscontinued(result.getDate("discontinued").toLocalDate());
			}
			
			if(result.getInt("company_id") != 0) {
				computer.setCompanyId(result.getInt("company_id"));
			}
			allComputer.add(computer);
		}
		return allComputer;
	}

}
