package com.excilys.cdb.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.mappers.CompanyDtoMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.services.CompanyService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/companies")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<CompanyDto> getAllCompanies(){
		return getAllCompaniesDto();
	}
	
	@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
	@RequestMapping(value="/page", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<CompanyDto> getCompanies(@RequestParam(required = false) String currentPage,
			@RequestParam(required = false) String itemsByPage,
			@RequestParam(required = false)String order,
			@RequestParam(required = false)String ascending){
		List<Company> companies = new ArrayList<>();
		List<CompanyDto> companiesDto = new ArrayList<>();
		Page page = createPageFromParameters(currentPage, itemsByPage, order, ascending);
		companies = companyService.getCompaniesByPage(page);
		companiesDto = companies.stream().map(company -> CompanyDtoMapper.companyToCompanyDto(company)).collect(Collectors.toList());
		return companiesDto;
	}
	
	@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public CompanyDto getCompanyById(@PathVariable("id") Integer id) {
		return CompanyDtoMapper.companyToCompanyDto(companyService.getCompany(id));
	}
	
	@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCompany(@PathVariable("id") Integer id){
		companyService.deleteCompany(id);
		return ResponseEntity.ok("");
	}
	
	@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
	@RequestMapping(value = "/numbers", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Integer getNbCompanies() {
		return companyService.getNbCompanies();
	}
	
	@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
	@RequestMapping(value = "/nbPages", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Integer getNbPage(@RequestParam String itemsByPage) {
		Page page = new Page();
		if(itemsByPage != null && !itemsByPage.equals("")) {
			page.setItemsByPage(Integer.parseInt(itemsByPage));
		}
		return companyService.getCompaniesNbPages(page);
	}
	
	private List<CompanyDto> getAllCompaniesDto(){
		List<CompanyDto> companiesDto = new ArrayList<>();
		List<Company> companies = new ArrayList<>();
		companies = companyService.getAllCompanies();
		companiesDto = companies.stream().map(company->CompanyDtoMapper.companyToCompanyDto(company)).collect(Collectors.toList());
		return companiesDto;
	}
	
	private Page createPageFromParameters(String currentPage, String itemsByPage, String order, String ascending) {
		Page page = new Page();
		if(currentPage != null && !currentPage.equals("")) {
			page.setCurrentPage(Integer.parseInt(currentPage));
		}
		if(itemsByPage != null && !itemsByPage.equals("")) {
			page.setItemsByPage(Integer.parseInt(itemsByPage));
		}
		if(order != null && !order.equals("")) {
			page.setOrder(order);
		}
		if(ascending != null && !ascending.equals("")) {
			page.setAscending(ascending);
		}
		return page;
	}

}
