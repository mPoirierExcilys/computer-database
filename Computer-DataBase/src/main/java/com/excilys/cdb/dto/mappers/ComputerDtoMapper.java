package com.excilys.cdb.dto.mappers;

import java.time.LocalDate;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.model.Computer;

public class ComputerDtoMapper {
	
	private ComputerDtoMapper(){
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
			computerDto.setCompanyDto(CompanyDtoMapper.companyToCompanyDto(computer.getCompany()));
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
		if(computerDto.getCompanyDto() != null && computerDto.getCompanyDto().getIdCompany() != null) {
			computer.setCompany(CompanyDtoMapper.companyDtoToCompany(computerDto.getCompanyDto()));	
		}
		return computer;
	}

}
