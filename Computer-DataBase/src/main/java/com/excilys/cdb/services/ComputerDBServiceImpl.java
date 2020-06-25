package com.excilys.cdb.services;

import java.util.List;
import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.model.Computer;

public class ComputerDBServiceImpl implements ComputerDBService{
	
	private final ComputerDao computerDao;
	
	private static final int NB_ELEMENTS_BY_PAGE = 10;
	
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
	public List<Computer> getComputersByPage(Integer page) {
		Integer offset = (page-1)*NB_ELEMENTS_BY_PAGE;
		return computerDao.findBetween(offset, NB_ELEMENTS_BY_PAGE);
	}

	@Override
	public Integer getComputersNbPages() {
		Integer nbEntries = computerDao.count();
		Integer nbPages = nbEntries/NB_ELEMENTS_BY_PAGE;
		return nbEntries%NB_ELEMENTS_BY_PAGE == 0?nbPages:nbPages+1;
	}

	@Override
	public Integer getNbComputers() {
		return computerDao.count();
	}

}
