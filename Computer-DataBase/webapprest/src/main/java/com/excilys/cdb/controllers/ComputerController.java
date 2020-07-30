package com.excilys.cdb.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.mappers.ComputerDtoMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.ComputerService;

@RestController
@RequestMapping("/computer")
public class ComputerController {
	
	@Autowired
	private ComputerService computerService;
	
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
}
