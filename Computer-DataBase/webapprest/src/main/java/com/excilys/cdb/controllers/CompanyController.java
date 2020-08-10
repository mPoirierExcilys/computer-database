package com.excilys.cdb.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.mappers.CompanyDtoMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.services.CompanyService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/companies")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Collection<CompanyDto> getAllCompanies(){
		return getAllCompaniesDto();
	}
	
	private List<CompanyDto> getAllCompaniesDto(){
		List<CompanyDto> companiesDto = new ArrayList<>();
		List<Company> companies = new ArrayList<>();
		companies = companyService.getAllCompanies();
		companiesDto = companies.stream().map(company->CompanyDtoMapper.companyToCompanyDto(company)).collect(Collectors.toList());
		return companiesDto;
	}

}
