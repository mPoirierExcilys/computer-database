package com.excilys.cdb.services;

import java.time.LocalDate;
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
		
		Computer computer = computerDao.find(8);
		System.out.println(computer);
		//computer.setIntroduced(LocalDate.of(1980, 3, 12));
		//computer.setCompanyId(1);
		//computer = computerDao.update(computer);
		//System.out.println(computer);
		
		
		Computer computer1 = new Computer();
		computer1.setName("Asus Zenbook");
		computer1.setIntroduced(LocalDate.of(2015, 9, 20));
		computer1.setCompanyId(37);
		Computer computerCreated = computerDao.create(computer1);
		System.out.println(computerCreated);
		
		computerDao.delete(computerCreated);
		
	}

}
