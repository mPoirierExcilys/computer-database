package com.excilys.cdb.services;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

public interface CompanyDBService {
	List<Company> getAllCompanies();
	
	Company getCompany(Integer id);
	
	List<Company> getCompaniesByPage(Page page);
	
	Integer getCompaniesNbPages(Page page);
}
