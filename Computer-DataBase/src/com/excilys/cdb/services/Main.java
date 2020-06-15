package com.excilys.cdb.services;

import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.dao.CompanyDao;
import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class Main {

	public static void main(String[] args) {
		CompanyDao companyDao = new CompanyDao();
		ComputerDao computerDao = new ComputerDao();
		List<Company> allcomp = new ArrayList<>();
		allcomp = companyDao.findAll();
		for(Company company: allcomp) {
			System.out.println(company);
		}
		
		Company company = companyDao.find(43);
		System.out.println(company);
		
		List<Computer> allcomputer = new ArrayList<>();
		allcomputer = computerDao.findAll();
		for(Computer computer: allcomputer) {
			System.out.println(computer);
		}
		
		Computer computer = computerDao.find(574);
		System.out.println(computer);
	}

}
