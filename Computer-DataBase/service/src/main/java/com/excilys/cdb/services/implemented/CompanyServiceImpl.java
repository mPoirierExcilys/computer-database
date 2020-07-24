package com.excilys.cdb.services.implemented;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.AbstractJpaDao;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.services.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	@Qualifier(value="companyJpaDao")
	private AbstractJpaDao<Company> companyDao;

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
		page.setOrder("company.id");
		page.setAscending("ASC");
		return companyDao.findBetween(offset, page);
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
