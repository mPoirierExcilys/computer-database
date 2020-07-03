package com.excilys.cdb.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerDaoTest {
	
	private ComputerDao computerDao;

	@Before
	public void setUp() throws Exception {
		computerDao = new ComputerDao(1);
	}

	@Test
	public void findAll() {
		assertTrue(computerDao.findAll().size() == 50);
	}
	
	@Test
	public void findBetween() {
		assertTrue(computerDao.findBetween(0, 10, "computer.id","ASC").size() == 10);
	}
	
	@Test
	public void find() {
		Computer computer = new Computer(1,"MacBook Pro 15.4 inch");
		computer.setCompany(new Company(1,"Apple Inc."));
		assertEquals(computerDao.find(1), computer);
	}
	
	@Test
	public void count() {
		assertTrue(computerDao.count() == 50);
	}
	
	@Test
	public void create() {
		Computer computer = new Computer();
		computer.setName("test");
		Computer newComp = computerDao.create(computer);
		computer.setIdComputer(51);
		assertEquals(computer,newComp);
	}
	
	@Test
	public void delete() {
		computerDao.delete(2);
		assertTrue(computerDao.count() == 49);
	}
	
	@Test
	public void update() {
		Computer computer = new Computer(1,"MacBook Pro 15.4 inch");
		computer.setCompany(new Company(2,"Thinking Machines"));
		computer = computerDao.update(computer);
		assertTrue(computer.getCompany().getIdCompany() == 2);
	}
	
	@Test
	public void countSearch() {
		assertTrue(computerDao.countSearch("Mac") == 25);
	}
	
	@Test
	public void findBetweenWithSearch() {
		assertTrue(computerDao.findBetweenWithSearch(0, 10, "Dragon", "computer.id","ASC").size() == 1);
	}

}
