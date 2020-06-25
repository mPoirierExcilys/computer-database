package com.excilys.cdb.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.model.Company;

public class CompanyMapper{

	public static Company resultToObject(ResultSet result) throws SQLException {
		Company company = new Company(result.getInt("id"), result.getString("name"));
		return company;
	}
	
	public static CompanyDto CompanyToCompanyDto(Company company) {
		CompanyDto companyDto = new CompanyDto();
		companyDto.setIdCompany(company.getIdCompany());
		companyDto.setName(company.getName());
		return companyDto;
	}
	
	public static Company CompanyDtoToCompany(CompanyDto companyDto) {
		Company company = new Company(companyDto.getIdCompany(),companyDto.getName());
		return company;
	}

}
