package com.excilys.cdb.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.mappers.ComputerDtoMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.services.ComputerService;

@RestController
@RequestMapping("/computers")
public class ComputersController {
	
	@Autowired
	private ComputerService computerService;
	
	@RequestMapping(value = "/numbers", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Integer getNbComputers(@RequestParam(required = false) String search) {
		if(search != null && !search.equals("")) {
			return computerService.getNbComputersSearch(search);
		}
		return computerService.getNbComputers();
	}
	
	@RequestMapping(value = "/nbPages", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Integer getNbPages(@RequestParam(required = false) String search,
			@RequestParam(required = false) String itemsByPage) {
		Page page = new Page();
		page.setItemsByPage(Integer.parseInt(itemsByPage));
		if(search != null && !search.equals("")) {
			return computerService.getNbComputersPagesSearch(page, search);
		}
		return  computerService.getComputersNbPages(page);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<ComputerDto> getComputers(@RequestParam(required = false) String search,
			@RequestParam(required = false) String currentPage,
			@RequestParam(required = false) String itemsByPage,
			@RequestParam(required = false)String order,
			@RequestParam(required = false)String ascending){
		List<ComputerDto> computersDto = new ArrayList<>();
		List<Computer> computers = new ArrayList<>();
		Page page = createPageFromParameters(currentPage, itemsByPage, order, ascending);
		if(search != null && !search.equals("")) {
			computers = computerService.getComputersByPagesSearch(page, search);
			computersDto = computers.stream().map(computer->ComputerDtoMapper.computerToComputerDto(computer)).collect(Collectors.toList());
			return computersDto;
		}
		computers = computerService.getComputersByPage(page);
		computersDto = computers.stream().map(computer->ComputerDtoMapper.computerToComputerDto(computer)).collect(Collectors.toList());
		return computersDto;
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ComputerDto getComputerById(@PathVariable("id") Integer id) {
		return ComputerDtoMapper.computerToComputerDto(computerService.getComputer(id));
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> addComputer(@RequestBody ComputerDto computerDto){
		if(computerDto.getName() != null && !computerDto.getName().trim().equals("")) {
			try {
				Computer newComputer = ComputerDtoMapper.computerDtoToComputer(computerDto);
				newComputer = computerService.createComputer(newComputer);
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
						.buildAndExpand(newComputer.getIdComputer()).toUri();
				return ResponseEntity.created(location).body("");
			}
			catch(IllegalArgumentException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
		return ResponseEntity.badRequest().body("Computer name must not be empty");
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<String> updateComputer(@PathVariable("id") Integer id, @RequestBody ComputerDto computerDto){
		Computer computer = computerService.getComputer(id);
		ComputerDto computerDtoOld = ComputerDtoMapper.computerToComputerDto(computer);
		Computer newComputer;
		try {
			testComputerDtoAttributesNotSetToNull(computerDtoOld, computerDto);
			computerDto.setIdComputer(computerDtoOld.getIdComputer());
			newComputer = ComputerDtoMapper.computerDtoToComputer(computerDto);
			newComputer = computerService.updateComputer(newComputer);
		}
		catch(IllegalArgumentException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		String location = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
		return ResponseEntity.ok(location);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteComputer(@PathVariable("id") Integer id){
		computerService.deleteComputer(id);
		return ResponseEntity.ok("");
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
