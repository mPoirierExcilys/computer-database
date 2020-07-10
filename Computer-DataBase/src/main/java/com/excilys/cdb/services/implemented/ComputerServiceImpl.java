package com.excilys.cdb.services.implemented;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.services.ComputerService;

@Service
public class ComputerServiceImpl implements ComputerService{
	
	private ComputerDao computerDao;
	
	@Autowired
	public ComputerServiceImpl(ComputerDao computerDao) {
		this.computerDao = computerDao;
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
	public void deleteComputer(Integer id) {
		computerDao.delete(id);
		
	}
	
	@Override
	public List<Computer> getComputersByPage(Page page) {
		Integer offset = (page.getCurrentPage()-1)*page.getItemsByPage();
		return computerDao.findBetween(offset, page);
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

	@Override
	public Integer getNbComputersPagesSearch(Page page,String search) {
		Integer nbEntries = computerDao.countSearch(search);
		Integer nbPages = nbEntries/page.getItemsByPage();
		return nbEntries%page.getItemsByPage() == 0?nbPages:nbPages+1;
	}

	@Override
	public List<Computer> getComputersByPagesSearch(Page page, String search) {
		Integer offset = (page.getCurrentPage()-1)*page.getItemsByPage();
		return computerDao.findBetweenWithSearch(offset, page, search);
	}

	@Override
	public Integer getNbComputersSearch(String search) {
		return computerDao.countSearch(search);
	}

}
