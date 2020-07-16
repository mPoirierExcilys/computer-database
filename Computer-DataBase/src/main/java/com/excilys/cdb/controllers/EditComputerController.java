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
@RequestMapping("/editComputer")
public class EditComputerController {
	
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	@Qualifier("computerDtoValidator")
	private Validator validator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	@GetMapping
	public String showEditComputer(@RequestParam(required=false,name="id") String id, Model model) {
		List<CompanyDto> companiesDto = getAllCompaniesDto();
		model.addAttribute("companies", companiesDto);
		String error;
		if(id != null) {
			try {
				Integer idComputer = Integer.parseInt(id);
				Computer computer = computerService.getComputer(idComputer);
				ComputerDto computerDto = ComputerDtoMapper.computerToComputerDto(computer);
				model.addAttribute("computerDto", computerDto);
			}catch(NumberFormatException e) {
				error = "Id is not an Integer";
				model.addAttribute("error", error);
			}
		}
		return "editComputer";
	}
	
	@PostMapping
	public String editComputer(@ModelAttribute("computerDto") @Validated ComputerDto computerDto,
							BindingResult bindingResult,
							@RequestParam(name="id") String id,
							Model model) {
		List<CompanyDto> companiesDto = getAllCompaniesDto();
		model.addAttribute("companies", companiesDto);
		Integer idComputer = Integer.parseInt(id);
		Computer computer = computerService.getComputer(idComputer);
		ComputerDto computerDtoOld = ComputerDtoMapper.computerToComputerDto(computer);
		if(bindingResult.hasErrors()) {
			return "editComputer";
		}
		try {
			testComputerDtoAttributesNotSetToNull(computerDtoOld, computerDto);
			computerDto.setIdComputer(computerDtoOld.getIdComputer());
			Computer newComputer = ComputerDtoMapper.computerDtoToComputer(computerDto);
			newComputer = computerService.updateComputer(newComputer);
			String success = "Computer " + newComputer.getName() + " was successfully updated";
			model.addAttribute("success", success);
			model.addAttribute("computerDto", ComputerDtoMapper.computerToComputerDto(newComputer));
		}catch(IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("computerDto", computerDtoOld);
		}
		return "editComputer";
	}
	
	private void testComputerDtoAttributesNotSetToNull(ComputerDto computerDtoOld, ComputerDto computerDto){
		if(computerDto.getName().trim().equals("")) {
			throw new IllegalArgumentException("Computer Name must not be empty");
		}
		if(computerDto.getIntroduced().equals("") && computerDtoOld.getIntroduced() != null) {
			throw new IllegalArgumentException("Computer Introduced wasn't empty, you couldn't set it to null");
		}
		if( computerDto.getDiscontinued().equals("") && computerDtoOld.getDiscontinued() != null) {
			throw new IllegalArgumentException("Computer Introduced wasn't empty, you couldn't set it to null");
		}
		if(computerDto.getCompanyDto().getIdCompany() < 1 && computerDtoOld.getCompanyDto() != null) {
			throw new IllegalArgumentException("Computer had a Company, You couldn't set it to null");
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
