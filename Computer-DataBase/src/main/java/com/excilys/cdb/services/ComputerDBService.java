package com.excilys.cdb.services;

import java.util.List;
import com.excilys.cdb.model.Computer;

public interface ComputerDBService {
	
	List<Computer> getAllComputers();
	
	Computer getComputer(Integer id);
	
	Computer updateComputer(Computer computer);
	
	Computer createComputer(Computer computer);
	
	void deleteComputer(Computer computer);
	
	List<Computer> getComputersByPage(Integer page);
	
	Integer getComputersNbPages();

}
