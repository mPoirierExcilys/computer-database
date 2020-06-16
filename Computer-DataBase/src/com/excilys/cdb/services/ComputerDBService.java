package com.excilys.cdb.services;

import java.util.Collection;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public interface ComputerDBService {
	
	Collection<Computer> getAllComputers();
	
	Collection<Company> getAllCompanies();
	
	Computer getComputer(Integer id);
	
	Company getCompany(Integer id);
	
	Computer updateComputer(Computer computer);
	
	Computer createComputer(Computer computer);
	
	void deleteComputer(Computer computer);
	
	Company getCompanyFromComputer(Computer computer);

}
