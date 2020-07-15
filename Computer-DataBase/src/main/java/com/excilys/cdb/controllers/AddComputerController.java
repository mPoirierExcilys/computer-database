package com.excilys.cdb.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.mappers.CompanyDtoMapper;
import com.excilys.cdb.dto.mappers.ComputerDtoMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;

@Controller
@RequestMapping("/addComputer")
public class AddComputerController {
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;
	
	@GetMapping
	public String showAddComputer(Model model) {
		List<CompanyDto> companiesDto = getAllCompaniesDto();
		model.addAttribute("companies", companiesDto);
		model.addAttribute("computerDto",new ComputerDto());
		return "addComputer";
	}
	
	@PostMapping
	public String addComputer(@ModelAttribute("computerDto") ComputerDto computerDto,
							Model model) {
		List<CompanyDto> companiesDto = getAllCompaniesDto();
		model.addAttribute("companies", companiesDto);
		computerDto = buildComputerDto(computerDto);
		try {
			testComputerDtoName(computerDto);
			Computer newComputer = ComputerDtoMapper.computerDtoToComputer(computerDto);
			newComputer = computerService.createComputer(newComputer);
			String success = "Computer " + newComputer.getName() + " was successfully added";
			model.addAttribute("success", success);
			return "addComputer";
			
		}catch(IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("newComputer", computerDto);
			return "addComputer";
		}
	}
	
	private ComputerDto buildComputerDto(ComputerDto computerDto) {
		ComputerDto NewcomputerDto = new ComputerDto();
		if(computerDto.getName() != null && !computerDto.getName().equals("")) {
			NewcomputerDto.setName(computerDto.getName());
		}
		if(computerDto.getIntroduced() != null && !computerDto.getIntroduced().equals("")) {		
			NewcomputerDto.setIntroduced(computerDto.getIntroduced());
		}
		if(computerDto.getDiscontinued() != null && !computerDto.getDiscontinued().equals("")){
			NewcomputerDto.setDiscontinued(computerDto.getDiscontinued());
		}
		if(computerDto.getCompanyDto() != null && computerDto.getCompanyDto().getIdCompany() > 0) {
			CompanyDto companyDto = new CompanyDto();
			companyDto.setIdCompany(computerDto.getCompanyDto().getIdCompany());
			NewcomputerDto.setCompanyDto(companyDto);
		}
		return NewcomputerDto;
	}
	
	private void testComputerDtoName(ComputerDto computerDto) {
		if(computerDto.getName() == null || computerDto.getName().trim().equals("")) {
			throw new IllegalArgumentException("Computer Name must not be empty");
		}
	}
	
	private List<CompanyDto> getAllCompaniesDto(){
		List<CompanyDto> companiesDto = new ArrayList<>();
		List<Company> companies = new ArrayList<>();
		companies = companyService.getAllCompanies();
		companiesDto = companies.stream().map(company->CompanyDtoMapper.companyToCompanyDto(company)).collect(Collectors.toList());
		return companiesDto;
	}
	
}
