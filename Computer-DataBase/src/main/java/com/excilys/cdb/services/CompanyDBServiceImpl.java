package com.excilys.cdb.services;

import java.util.List;

import com.excilys.cdb.dao.CompanyDao;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class CompanyDBServiceImpl implements CompanyDBService {
	
	private final CompanyDao companyDao;
	
	private static final int NB_ELEMENTS_BY_PAGE = 10;
	
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
	public List<Company> getCompaniesByPage(Integer page) {
		Integer offset = (page-1)*NB_ELEMENTS_BY_PAGE;
		return companyDao.findBetween(offset, NB_ELEMENTS_BY_PAGE);
	}

	@Override
	public Integer getCompaniesNbPages() {
		Integer nbEntries = companyDao.count();
		Integer nbPages = nbEntries/NB_ELEMENTS_BY_PAGE;
		return nbEntries%NB_ELEMENTS_BY_PAGE == 0?nbPages:nbPages+1;
	}

}
