package com.excilys.cdb.dto.mappers;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerDtoMapperTest {
	
	private static final Integer idComputer = 10;
    private static final String nameComputer = "Computer";
    private static final LocalDate introduced = LocalDate.of(2019, 8, 8);
    private static final LocalDate discontinued = LocalDate.of(2020, 8, 8);
    private static final Integer idCompany = 20;
    private static final String companyName = "Company";
    
    
    @Test
	public void computerDtoToComputer() {
		Computer computerExpected = new Computer(idComputer, nameComputer,introduced,discontinued,new Company(idCompany, companyName));
		ComputerDto computerDto = new ComputerDto(idComputer,nameComputer,introduced.toString(),discontinued.toString(),new CompanyDto(idCompany, companyName));
		Computer computer = ComputerDtoMapper.computerDtoToComputer(computerDto);
		assertEquals(computerExpected,computer);
	}
	
	@Test
	public void computerToComputerDto() {
		ComputerDto computerDtoExpected = new ComputerDto(idComputer,nameComputer,introduced.toString(),discontinued.toString(),new CompanyDto(idCompany, companyName));
		Computer computer = new Computer(idComputer, nameComputer,introduced,discontinued,new Company(idCompany, companyName));
		ComputerDto computerDto = ComputerDtoMapper.computerToComputerDto(computer);
		assertEquals(computerDtoExpected, computerDto);
	}

}
