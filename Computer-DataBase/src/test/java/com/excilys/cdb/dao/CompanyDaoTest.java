package com.excilys.cdb.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.model.Company;

public class CompanyDaoTest {
	
	private CompanyDao companyDao;

	@Before
	public void setUp() throws Exception {
		companyDao = new CompanyDao(1);
	}

	@Test
	public void findAll() {
		assertTrue(companyDao.findAll().size() == 20);
	}
	
	@Test
	public void find() {
		Company company = companyDao.find(1);
		Company companyExpected = new Company(1, "Apple Inc.");
		assertEquals(companyExpected, company);
	}
	
	@Test
	public void count() {
		assertTrue(companyDao.count() == 20);
	}
	
	@Test
	public void findBetween(){
		assertTrue(companyDao.findBetween(0, 10).size() == 10);
	}

}
