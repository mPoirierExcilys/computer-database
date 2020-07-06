package com.excilys.cdb.dao.mappers;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.excilys.cdb.model.Company;

public class CompanyDaoMapperTest {
	
	private static final String ATTRIBUT_ID_COMPANY = "id";
	private static final String ATTRIBUT_NAME_COMPANY = "name";
	
	private static final Integer idCompany = 10;
	private static final String companyName = "Company";
	
	private ResultSet resultSet = Mockito.mock(ResultSet.class);

	@Before
	public void setUp() throws Exception {
		Mockito.when(resultSet.getInt(ATTRIBUT_ID_COMPANY)).thenReturn(idCompany);
		Mockito.when(resultSet.getString(ATTRIBUT_NAME_COMPANY)).thenReturn(companyName);
	}

	@Test
	public void resultToObject() throws SQLException {
		Company company = CompanyDaoMapper.resultToObject(resultSet);
		Company companyExpected = new Company(idCompany,companyName);
		assertEquals(companyExpected,company);
	}

}
