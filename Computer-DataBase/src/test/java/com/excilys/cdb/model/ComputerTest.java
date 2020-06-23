package com.excilys.cdb.model;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class ComputerTest {
	
	private Computer computer;

	@Before
	public void setUp() throws Exception {
		this.computer = new Computer();
	}
	
	@Test
	public void setIntroduced() {
		computer.setDiscontinued(LocalDate.of(2020, 8, 8));
		computer.setIntroduced(LocalDate.of(2019, 8, 8));
		assertEquals(LocalDate.of(2019, 8, 8),computer.getIntroduced());
	}
	
	@Test
	public void setIntroducedAndDicontinuedNull() {
		computer.setIntroduced(LocalDate.of(2019, 8, 8));
		assertEquals(LocalDate.of(2019, 8, 8),computer.getIntroduced());
	}

	@Test(expected=IllegalArgumentException.class)
	public void setIntroducedIllegalArgumentException() {
		computer.setDiscontinued(LocalDate.of(2019,8,8));
		computer.setIntroduced(LocalDate.of(2020, 8, 8));
		assertEquals(LocalDate.of(2020, 8, 8),computer.getIntroduced());
	}
	
	@Test
	public void setDiscontinued(){
		computer.setIntroduced(LocalDate.of(2019, 8, 8));
		computer.setDiscontinued(LocalDate.of(2020, 8, 8));
		assertEquals(LocalDate.of(2020, 8, 8),computer.getDiscontinued());
	}
	
	@Test 
	public void setDiscontinuedAndIntroducedNull() {
		computer.setDiscontinued(LocalDate.of(2020, 8, 8));
		assertEquals(LocalDate.of(2020, 8, 8), computer.getDiscontinued());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setDiscontinuedIllegalArgumentException() {
		computer.setIntroduced(LocalDate.of(2020, 8, 8));
		computer.setDiscontinued(LocalDate.of(2019, 8, 8));
		assertEquals(LocalDate.of(2019, 8, 8),computer.getDiscontinued());
	}
}
