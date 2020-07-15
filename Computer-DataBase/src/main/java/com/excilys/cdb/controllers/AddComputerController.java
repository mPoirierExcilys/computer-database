package com.excilys.cdb.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		return "addComputer";
	}
	
	@PostMapping
	public String addComputer(@RequestParam(name="computerName") String computerName,
							@RequestParam(required=false, name="introduced") String introduced,
							@RequestParam(required=false, name="discontinued") String discontinued,
							@RequestParam(required=false, name="companyId") String companyId,
							Model model) {
		List<CompanyDto> companiesDto = getAllCompaniesDto();
		model.addAttribute("companies", companiesDto);
		ComputerDto computerDto = new ComputerDto();
		computerDto = buildComputerDto(computerName,introduced,discontinued,companyId);
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
	
	private ComputerDto buildComputerDto(String computerName, String introduced, String discontinued, String companyId) {
		ComputerDto computerDto = new ComputerDto();
		if(computerName != null && !computerName.equals("")) {
			computerDto.setName(computerName);
		}
		if(introduced != null && !introduced.equals("")) {		
			computerDto.setIntroduced(introduced);
		}
		if(discontinued != null && !discontinued.equals("")){
			computerDto.setDiscontinued(discontinued);
		}
		if(!companyId.equals("0")) {
			CompanyDto companyDto = new CompanyDto();
			companyDto.setIdCompany(Integer.parseInt(companyId));
			computerDto.setCompany(companyDto);
		}
		return computerDto;
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
