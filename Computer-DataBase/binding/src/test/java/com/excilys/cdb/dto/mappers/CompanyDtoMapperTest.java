package com.excilys.cdb.dto.mappers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.model.Company;

public class CompanyDtoMapperTest {
	
	private static final Integer idCompany = 10;
	private static final String companyName = "Company";
	
	
	@Test
	public void companyToCompanyDto() {
		CompanyDto companyDto = CompanyDtoMapper.companyToCompanyDto(new Company(idCompany, companyName));
		CompanyDto companyDtoExpected = new CompanyDto(idCompany, companyName);
		assertEquals(companyDtoExpected, companyDto);
	}
	
	@Test
	public void companyDtoToCompany() {
		Company company = CompanyDtoMapper.companyDtoToCompany(new CompanyDto(idCompany,companyName));
		Company companyExpected = new Company(idCompany, companyName);
		assertEquals(companyExpected, company);
	}

}
