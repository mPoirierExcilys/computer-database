package com.excilys.cdb.dto.mappers;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.model.Company;

public class CompanyDtoMapper {
	
	private CompanyDtoMapper() {
	}
	
	public static CompanyDto companyToCompanyDto(Company company) {
		CompanyDto companyDto = new CompanyDto();
		companyDto.setIdCompany(company.getIdCompany());
		companyDto.setName(company.getName());
		return companyDto;
	}
	
	public static Company companyDtoToCompany(CompanyDto companyDto) {
		Company company = new Company(companyDto.getIdCompany(),companyDto.getName());
		return company;
	}

}
