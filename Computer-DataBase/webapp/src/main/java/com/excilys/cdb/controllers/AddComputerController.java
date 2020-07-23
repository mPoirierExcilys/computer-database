package com.excilys.cdb.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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
	
	@Autowired
	@Qualifier("computerDtoValidator")
	private Validator validator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	@GetMapping
	public String showAddComputer(Model model) {
		List<CompanyDto> companiesDto = getAllCompaniesDto();
		model.addAttribute("companies", companiesDto);
		model.addAttribute("computerDto",new ComputerDto());
		return "addComputer";
	}
	
	@PostMapping
	public String addComputer(@ModelAttribute("computerDto") @Validated ComputerDto computerDto,
							BindingResult bindingResult,Model model) {
		List<CompanyDto> companiesDto = getAllCompaniesDto();
		model.addAttribute("companies", companiesDto);
		if(bindingResult.hasErrors()) {
			return "addComputer";
		}
		try {
			Computer newComputer = ComputerDtoMapper.computerDtoToComputer(computerDto);
			newComputer = computerService.createComputer(newComputer);
			String success = "Computer " + newComputer.getName() + " was successfully added";
			model.addAttribute("success", success);
			model.addAttribute("computerDto",new ComputerDto());
			return "addComputer";
			
		}catch(IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			return "addComputer";
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
