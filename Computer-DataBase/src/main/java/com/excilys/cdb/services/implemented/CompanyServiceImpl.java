package com.excilys.cdb.services.implemented;

import java.util.List;

import com.excilys.cdb.dao.CompanyDao;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.services.CompanyService;

public class CompanyServiceImpl implements CompanyService {
	
	private final CompanyDao companyDao;
	
	public CompanyServiceImpl() {
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
	public List<Company> getCompaniesByPage(Page page) {
		Integer offset = (page.getCurrentPage()-1)*page.getItemsByPage();
		return companyDao.findBetween(offset, page.getItemsByPage(),"company.id","ASC");
	}

	@Override
	public Integer getCompaniesNbPages(Page page) {
		Integer nbEntries = companyDao.count();
		Integer nbPages = nbEntries/page.getItemsByPage();
		return nbEntries%page.getItemsByPage() == 0?nbPages:nbPages+1;
	}

	@Override
	public void deleteCompany(Integer id) {
		companyDao.delete(id);
		
	}

}
