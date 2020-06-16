package com.excilys.cdb.services;

import java.util.Collection;

import com.excilys.cdb.dao.CompanyDao;
import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerDBServiceImpl implements ComputerDBService{
	
	private final ComputerDao computerDao;
	private final CompanyDao companyDao;
	
	public ComputerDBServiceImpl() {
		this.computerDao = new ComputerDao();
		this.companyDao = new CompanyDao();
	}

	@Override
	public Collection<Computer> getAllComputers() {
		return computerDao.findAll();
	}

	@Override
	public Collection<Company> getAllCompanies() {
		return companyDao.findAll();
	}

	@Override
	public Computer getComputer(Integer id) {
		return computerDao.find(id);
	}

	@Override
	public Company getCompany(Integer id) {
		return companyDao.find(id);
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
	public Company getCompanyFromComputer(Computer computer) {
		if(computer.getCompanyId() != null) {
			return companyDao.find(computer.getCompanyId());
		}
		return null;
	}

}
