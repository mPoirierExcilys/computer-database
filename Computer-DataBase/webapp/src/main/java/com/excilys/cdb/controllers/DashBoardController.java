package com.excilys.cdb.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.mappers.ComputerDtoMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.services.ComputerService;

@Controller
@RequestMapping({"/","/dashboard"})
public class DashBoardController {
	
	@Autowired
	private ComputerService computerService;
	private Page page;
	
	@GetMapping
	public String showComputers(@RequestParam(required=false, name="nbByPage") String nbByPage,
			@RequestParam(required=false, name="page") String pageWish,
			@RequestParam(required=false, name="order") String order,
			@RequestParam(required=false, name="ascending") String ascending,
			@RequestParam(required=false, name="search") String searchRequest,
			Model model) {
		List<ComputerDto> computersDto = new ArrayList<>();
		List<Computer> computers = new ArrayList<>();
		page = instantiatePage(nbByPage,pageWish,order,ascending);
		String search = "";
		if(searchRequest != null && !searchRequest.equals("")) {
			search = searchRequest;
			computers = computerService.getComputersByPagesSearch(page, search);
			computersDto = computers.stream().map(computer->ComputerDtoMapper.computerToComputerDto(computer)).collect(Collectors.toList());
			model.addAttribute("nbPagesMax", computerService.getNbComputersPagesSearch(page, search));
			model.addAttribute("nbComputers", computerService.getNbComputersSearch(search));
		}
		else {
			computers = computerService.getComputersByPage(page);
			computersDto = computers.stream().map(computer->ComputerDtoMapper.computerToComputerDto(computer)).collect(Collectors.toList());
			model.addAttribute("nbPagesMax", computerService.getComputersNbPages(page));
			model.addAttribute("nbComputers", computerService.getNbComputers());
		}
		model.addAttribute("search", search);
		model.addAttribute("page", page);
		model.addAttribute("computers", computersDto);
		return "dashboard";
	}
	
	@PostMapping
	public String deleteComputers(@RequestParam(name="selection") String selection) {
		if(selection != null && !selection.equals("")) {
			String listInt = selection;
			List<Integer> ids = Stream.of(listInt.split(","))
	                .map(Integer::parseInt)
	                .collect(Collectors.toList());
			ids.stream().forEach(id->computerService.deleteComputer(id));
		}
		return "redirect:/dashboard";
	}
	
	private Page instantiatePage(String nbByPage, String pageWish, String order, String ascending) {
		Page page = new Page();
		page.setCurrentPage(1);
		page.setItemsByPage(10);
		page.setOrder("computer.id");
		page.setAscending("ASC");
		if(nbByPage != null && !nbByPage.equals("")) {
			page.setItemsByPage(Integer.parseInt(nbByPage));
		}
		if(pageWish != null && !pageWish.equals("")) {
			page.setCurrentPage(Integer.parseInt(pageWish));
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
