package com.excilys.cdb.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerMapper{

	public static Computer resultToObject(ResultSet result) throws SQLException {
		Computer computer = null;
			computer = new Computer(result.getInt("id"), result.getString("name"));
			if(result.getDate("introduced") != null) {
				computer.setIntroduced(result.getDate("introduced").toLocalDate());
			}
			
			if(result.getDate("discontinued") != null) {
				computer.setDiscontinued(result.getDate("discontinued").toLocalDate());
			}
			
			if(result.getInt("company_id") != 0) {
				Company company = new Company(result.getInt("company_id"),result.getString("companyName"));
				computer.setCompany(company);
			}
		return computer;
	}
	
	public static ComputerDto computerToComputerDto(Computer computer) {
		ComputerDto computerDto = new ComputerDto();
		computerDto.setIdComputer(computer.getIdComputer());
		computerDto.setName(computer.getName());
		if(computer.getIntroduced()!=null) {
			computerDto.setIntroduced(computer.getIntroduced().toString());
		}
		if(computer.getDiscontinued() != null) {
			computerDto.setDiscontinued(computer.getDiscontinued().toString());
		}
		if(computer.getCompany() != null) {
			computerDto.setCompany(CompanyMapper.companyToCompanyDto(computer.getCompany()));
		}
		return computerDto;
	}
	
	public static Computer computerDtoToComputer(ComputerDto computerDto) {
		Computer computer = new Computer();
		if(computerDto.getIdComputer() != null) {
			computer.setIdComputer(computerDto.getIdComputer());
		}
		computer.setName(computerDto.getName());
		if(computerDto.getIntroduced() != null) {
			computer.setIntroduced(LocalDate.parse(computerDto.getIntroduced()));	
		}
		if(computerDto.getDiscontinued() != null) {
			computer.setDiscontinued(LocalDate.parse(computerDto.getDiscontinued()));
		}
		if(computerDto.getCompany() != null && computerDto.getCompany().getIdCompany() != null) {
			computer.setCompany(CompanyMapper.companyDtoToCompany(computerDto.getCompany()));	
		}
		return computer;
	}

}
