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
@RequestMapping("/editComputer")
public class EditComputerController {
	
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	
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
				model.addAttribute("computer", computerDto);
			}catch(NumberFormatException e) {
				error = "Id is not an Integer";
				model.addAttribute("error", error);
			}
		}
		return "editComputer";
	}
	
	@PostMapping
	public String editComputer(@RequestParam(name="computerName") String computerName,
							@RequestParam(required=false, name="introduced") String introduced,
							@RequestParam(required=false, name="discontinued") String discontinued,
							@RequestParam(required=false, name="companyId") String companyId,
							@RequestParam(name="id") String id,
							Model model) {
		List<CompanyDto> companiesDto = getAllCompaniesDto();
		model.addAttribute("companies", companiesDto);
		Integer idComputer = Integer.parseInt(id);
		Computer computer = computerService.getComputer(idComputer);
		ComputerDto computerDtoOld = ComputerDtoMapper.computerToComputerDto(computer);
		ComputerDto computerDto = new ComputerDto();
		computerDto = buildComputerDto(computerName,introduced,discontinued,companyId);
		try {
			testComputerDtoAttributesNotSetToNull(computerDtoOld, computerDto);
			computerDto.setIdComputer(computerDtoOld.getIdComputer());
			Computer newComputer = ComputerDtoMapper.computerDtoToComputer(computerDto);
			newComputer = computerService.updateComputer(newComputer);
			String success = "Computer " + newComputer.getName() + " was successfully updated";
			model.addAttribute("success", success);
			model.addAttribute("computer", ComputerDtoMapper.computerToComputerDto(newComputer));
		}catch(IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("computer", computerDtoOld);
		}
		return "editComputer";
	}
	
	private void testComputerDtoAttributesNotSetToNull(ComputerDto computerDtoOld, ComputerDto computerDto){
		if(computerDto.getName() == null || computerDto.getName().trim().equals("")) {
			throw new IllegalArgumentException("Computer Name must not be empty");
		}
		if(computerDto.getIntroduced() == null && computerDtoOld.getIntroduced()!=null) {
			throw new IllegalArgumentException("Computer Introduced wasn't empty, you couldn't set it to null");
		}
		if(computerDto.getDiscontinued() == null && computerDtoOld.getDiscontinued() != null) {
			throw new IllegalArgumentException("Computer Introduced wasn't empty, you couldn't set it to null");
		}
		if(computerDto.getCompany() == null && computerDtoOld.getCompany() != null) {
			throw new IllegalArgumentException("Computer had a Company, You couldn't set it to null");
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
	
	private List<CompanyDto> getAllCompaniesDto(){
		List<CompanyDto> companiesDto = new ArrayList<>();
		List<Company> companies = new ArrayList<>();
		companies = companyService.getAllCompanies();
		companiesDto = companies.stream().map(company->CompanyDtoMapper.companyToCompanyDto(company)).collect(Collectors.toList());
		return companiesDto;
	}
}
