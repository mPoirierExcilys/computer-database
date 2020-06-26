package com.excilys.cdb.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
		assertTrue(computerDao.findBetween(0, 10).size() == 10);
	}
	
	@Test
	public void find() {
		Computer computer = new Computer(1,"MacBook Pro 15.4 inch");
		computer.setCompanyId(1);
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
		Computer computer = new Computer(2,"MacBook Pro 15.4 inch");
		computerDao.delete(computer);
		assertTrue(computerDao.count() == 49);
	}
	
	@Test
	public void update() {
		Computer computer = new Computer(1,"MacBook Pro 15.4 inch");
		computer.setCompanyId(2);
		computer = computerDao.update(computer);
		assertTrue(computer.getCompanyId() == 2);
	}

}
