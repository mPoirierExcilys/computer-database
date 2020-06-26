package com.excilys.cdb.services;

import java.util.List;

import com.excilys.cdb.dao.CompanyDao;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public class CompanyDBServiceImpl implements CompanyDBService {
	
	private final CompanyDao companyDao;
	
	public CompanyDBServiceImpl() {
		this.companyDao = new CompanyDao();
	}

	@Override
	public List<Company> getAllCompanies() {
		return companyDao.findAll();
	}

	@Override
	public Company getCompany(Integer id) {
		return companyDao.find(id);
	}

	@Override
	public Company getCompanyFromComputer(Computer computer) {
		if(computer.getCompanyId() != null) {
			return companyDao.find(computer.getCompanyId());
		}
		return null;
	}

	@Override
	public List<Company> getCompaniesByPage(Page page) {
		Integer offset = (page.getCurrentPage()-1)*page.getItemsByPage();
		return companyDao.findBetween(offset, page.getItemsByPage());
	}

	@Override
	public Integer getCompaniesNbPages(Page page) {
		Integer nbEntries = companyDao.count();
		Integer nbPages = nbEntries/page.getItemsByPage();
		return nbEntries%page.getItemsByPage() == 0?nbPages:nbPages+1;
	}

}