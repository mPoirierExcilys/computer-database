package com.excilys.cdb.services;

import java.util.List;
import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public class ComputerDBServiceImpl implements ComputerDBService{
	
	private final ComputerDao computerDao;
	
	public ComputerDBServiceImpl() {
		this.computerDao = new ComputerDao();
	}

	@Override
	public List<Computer> getAllComputers() {
		return computerDao.findAll();
	}

	@Override
	public Computer getComputer(Integer id) {
		return computerDao.find(id);
	}

	@Override
	public Computer updateComputer(Computer computer) {
		return computerDao.update(computer);
	}

	@Override
	public Computer createComputer(Computer computer) {
		return computerDao.create(computer);
	}

	@Override
	public void deleteComputer(Computer computer) {
		computerDao.delete(computer);
		
	}
	
	@Override
	public List<Computer> getComputersByPage(Page page) {
		Integer offset = (page.getCurrentPage()-1)*page.getItemsByPage();
		return computerDao.findBetween(offset, page.getItemsByPage());
	}

	@Override
	public Integer getComputersNbPages(Page page) {
		Integer nbEntries = computerDao.count();
		Integer nbPages = nbEntries/page.getItemsByPage();
		return nbEntries%page.getItemsByPage() == 0?nbPages:nbPages+1;
	}

	@Override
	public Integer getNbComputers() {
		return computerDao.count();
	}

}
