package com.excilys.cdb.controllers.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.mappers.ComputerDtoMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.services.implemented.ComputerServiceImpl;

/**
 * Servlet implementation class DashBoardServlet
 */
@WebServlet(name="DashBoardServlet",urlPatterns="/dashboard")
public class DashBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ComputerService computerService;
	private Page page;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashBoardServlet() {
        super();
        computerService = new ComputerServiceImpl();
        page = new Page();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ComputerDto> computersDto = new ArrayList<>();
		List<Computer> computers = new ArrayList<>();
		String search = "";
		String order = "computer.id";
		String ascending = "ASC";
		page.setCurrentPage(1);
		page.setItemsByPage(10);
		if(request.getParameter("nbByPage") != null) {
			String nbByPage = request.getParameter("nbByPage");
			page.setItemsByPage(Integer.parseInt(nbByPage));
		}
		if(request.getParameter("page") != null) {
			String pageWish = request.getParameter("page");
			page.setCurrentPage(Integer.parseInt(pageWish));
			if(page.getCurrentPage() < 1) {
				page.setCurrentPage(1);
			}
		}
		if(request.getParameter("order") != null && !request.getParameter("order").equals("")) {
			order = request.getParameter("order");
			request.setAttribute("order", order);
		}
		if(request.getParameter("ascending") != null && !request.getParameter("ascending").equals("")) {
			ascending = request.getParameter("ascending");
			request.setAttribute("ascending", ascending);
		}
		if(request.getParameter("search") != null && !request.getParameter("search").equals("")) {
			search = request.getParameter("search");
			computers = computerService.getComputersByPagesSearch(page, search, order,ascending);
			for(Computer computer: computers) {
				ComputerDto computerDto = ComputerDtoMapper.computerToComputerDto(computer);
				computersDto.add(computerDto);
			}
			request.setAttribute("nbPagesMax", computerService.getNbComputersPagesSearch(page, search));
			request.setAttribute("nbComputers", computerService.getNbComputersSearch(search));
		}else {
			computers = computerService.getComputersByPage(page, order, ascending);
			for(Computer computer: computers) {
				ComputerDto computerDto = ComputerDtoMapper.computerToComputerDto(computer);
				computersDto.add(computerDto);
			}
			request.setAttribute("nbPagesMax", computerService.getComputersNbPages(page));
			request.setAttribute("nbComputers", computerService.getNbComputers());
		}
		request.setAttribute("nbByPage", page.getItemsByPage());
		request.setAttribute("search", search);
		request.setAttribute("page", page);
		request.setAttribute("computers", computersDto);
		request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("selection") != null && !request.getParameter("selection").equals("")) {
			String listInt = request.getParameter("selection");
			List<Integer> ids = Stream.of(listInt.split(","))
	                .map(Integer::parseInt)
	                .collect(Collectors.toList());
			for(Integer id: ids) {
				computerService.deleteComputer(id);
			}
		}
		doGet(request, response);
	}

}
