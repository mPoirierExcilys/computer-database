package com.excilys.cdb.mappers;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.model.Company;

public class CompanyMapperTest {
	
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
		Company company = CompanyMapper.resultToObject(resultSet);
		Company companyExpected = new Company(idCompany,companyName);
		assertEquals(companyExpected,company);
	}
	
	@Test
	public void companyToCompanyDto() {
		CompanyDto companyDto = CompanyMapper.companyToCompanyDto(new Company(idCompany, companyName));
		CompanyDto companyDtoExpected = new CompanyDto(idCompany, companyName);
		assertEquals(companyDtoExpected, companyDto);
	}
	
	@Test
	public void companyDtoToCompany() {
		Company company = CompanyMapper.companyDtoToCompany(new CompanyDto(idCompany,companyName));
		Company companyExpected = new Company(idCompany, companyName);
		assertEquals(companyExpected, company);
	}

}
