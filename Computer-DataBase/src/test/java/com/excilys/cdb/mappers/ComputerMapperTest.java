package com.excilys.cdb.mappers;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.excilys.cdb.model.Computer;

public class ComputerMapperTest {
	
	private static final String ATTRIBUT_ID_COMPUTER = "id";
    private static final String ATTRIBUT_NAME = "name";
    private static final String ATTRIBUT_INTRODUCED = "introduced";
    private static final String ATTRIBUT_DISCONTINUED = "discontinued";
    private static final String ATTRIBUT_COMPANY_ID = "company_id";
    
    private static final Integer idComputer = 10;
    private static final String nameComputer = "Computer";
    private static final LocalDate introduced = LocalDate.of(2019, 8, 8);
    private static final LocalDate discontinued = LocalDate.of(2020, 8, 8);
    private static final Integer idCompany = 20;
    
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

	@Before
	public void setUp() throws Exception {
		Mockito.when(resultSet.getInt(ATTRIBUT_ID_COMPUTER)).thenReturn(idComputer);
		Mockito.when(resultSet.getString(ATTRIBUT_NAME)).thenReturn(nameComputer);
	}

	@Test
	public void resultToObject() throws SQLException {
		Mockito.when(resultSet.getDate(ATTRIBUT_INTRODUCED)).thenReturn(Date.valueOf(introduced));
		Mockito.when(resultSet.getDate(ATTRIBUT_DISCONTINUED)).thenReturn(Date.valueOf(discontinued));
		Mockito.when(resultSet.getInt(ATTRIBUT_COMPANY_ID)).thenReturn(idCompany);
		Computer computer = ComputerMapper.resultToObject(resultSet);
		Computer computerExpected = new Computer(idComputer,nameComputer,introduced,discontinued,idCompany);
		assertEquals(computerExpected,computer);
	}
	
	@Test
	public void resultToObjectIdCompanyNull() throws SQLException {
		Mockito.when(resultSet.getDate(ATTRIBUT_INTRODUCED)).thenReturn(Date.valueOf(introduced));
		Mockito.when(resultSet.getDate(ATTRIBUT_DISCONTINUED)).thenReturn(Date.valueOf(discontinued));
		Mockito.when(resultSet.getInt(ATTRIBUT_COMPANY_ID)).thenReturn(0);
		Computer computer = ComputerMapper.resultToObject(resultSet);
		Computer computerExpected = new Computer(idComputer,nameComputer);
		computerExpected.setIntroduced(introduced);
		computerExpected.setDiscontinued(discontinued);
		assertEquals(computerExpected,computer);
	}
	
	@Test
	public void resultToObjectIntroducedNull() throws SQLException {
		Mockito.when(resultSet.getDate(ATTRIBUT_INTRODUCED)).thenReturn(null);
		Mockito.when(resultSet.getDate(ATTRIBUT_DISCONTINUED)).thenReturn(Date.valueOf(discontinued));
		Mockito.when(resultSet.getInt(ATTRIBUT_COMPANY_ID)).thenReturn(idCompany);
		Computer computer = ComputerMapper.resultToObject(resultSet);
		Computer computerExpected = new Computer(idComputer,nameComputer);
		computerExpected.setDiscontinued(discontinued);
		computerExpected.setCompanyId(idCompany);
		assertEquals(computerExpected,computer);	
	}
	
	@Test
	public void resultToObjectDiscontinuedNull() throws SQLException {
		Mockito.when(resultSet.getDate(ATTRIBUT_INTRODUCED)).thenReturn(Date.valueOf(introduced));
		Mockito.when(resultSet.getDate(ATTRIBUT_DISCONTINUED)).thenReturn(null);
		Mockito.when(resultSet.getInt(ATTRIBUT_COMPANY_ID)).thenReturn(idCompany);
		Computer computer = ComputerMapper.resultToObject(resultSet);
		Computer computerExpected = new Computer(idComputer,nameComputer);
		computerExpected.setCompanyId(idCompany);
		computerExpected.setIntroduced(introduced);
		assertEquals(computerExpected,computer);
		
	}

}
