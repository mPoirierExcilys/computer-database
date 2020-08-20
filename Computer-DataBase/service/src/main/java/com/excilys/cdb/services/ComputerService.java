package com.excilys.cdb.services;

import java.util.List;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public interface ComputerService {
	
	List<Computer> getAllComputers();
	
	Computer getComputer(Integer id);
	
	Computer updateComputer(Computer computer);
	
	Computer createComputer(Computer computer);
	
	void deleteComputer(Integer id);
	
	List<Computer> getComputersByPage(Page page);
	
	Integer getComputersNbPages(Page page);
	
	Integer getNbComputers();
	
	Integer getNbComputersPagesSearch(Page page, String search);
	
	List<Computer> getComputersByPagesSearch(Page page, String search);
	
	Integer getNbComputersSearch(String search);
}
