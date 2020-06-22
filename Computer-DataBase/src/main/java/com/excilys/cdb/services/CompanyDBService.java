package com.excilys.cdb.services;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public interface CompanyDBService {
	List<Company> getAllCompanies();
	
	Company getCompany(Integer id);
	
	Company getCompanyFromComputer(Computer computer);
	
	List<Company> getCompaniesByPage(Integer page);
	
	Integer getCompaniesNbPages();
}
