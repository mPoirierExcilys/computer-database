package com.excilys.cdb.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	@RequestMapping(value = "/nbPages", method = RequestMethod.GET, consumes = "application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Integer getNbPages(@RequestParam(required = false) String search, @RequestBody Page page) {
		if(search != null && !search.equals("")) {
			return computerService.getNbComputersPagesSearch(page, search);
		}
		return  computerService.getComputersNbPages(page);
	}
	
	@RequestMapping(method = RequestMethod.GET, consumes = "application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<ComputerDto> getComputers(@RequestParam(required = false) String search, @RequestBody Page page){
		List<ComputerDto> computersDto = new ArrayList<>();
		List<Computer> computers = new ArrayList<>();
		if(search != null && !search.equals("")) {
			computers = computerService.getComputersByPagesSearch(page, search);
			computersDto = computers.stream().map(computer->ComputerDtoMapper.computerToComputerDto(computer)).collect(Collectors.toList());
			return computersDto;
		}
		computers = computerService.getComputersByPage(page);
		computersDto = computers.stream().map(computer->ComputerDtoMapper.computerToComputerDto(computer)).collect(Collectors.toList());
		return computersDto;
	}
}
