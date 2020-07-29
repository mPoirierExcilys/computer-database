package com.excilys.cdb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.mappers.ComputerDtoMapper;
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
}
