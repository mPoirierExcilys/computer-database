package com.excilys.cdb.controllers.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.CompanyDBService;
import com.excilys.cdb.services.CompanyDBServiceImpl;
import com.excilys.cdb.services.ComputerDBService;
import com.excilys.cdb.services.ComputerDBServiceImpl;

/**
 * Servlet implementation class DashBoardServlet
 */
@WebServlet(name="DashBoardServlet",urlPatterns="/dashboard")
public class DashBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ComputerDBService computerService;
	private CompanyDBService companyService;
	private Integer currentPage;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashBoardServlet() {
        super();
        computerService = new ComputerDBServiceImpl();
        companyService = new CompanyDBServiceImpl();
        currentPage=1;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ComputerDto> computersDto = new ArrayList<>();
		List<Computer> computers = new ArrayList<>();
		if(request.getParameter("page") != null) {
			String page = request.getParameter("page");
			currentPage = Integer.parseInt(page);
		}
		computers = computerService.getComputersByPage(currentPage);
		for(Computer computer: computers) {
			ComputerDto computerDto = ComputerMapper.computerToComputerDto(computer, companyService.getCompanyFromComputer(computer));
			computersDto.add(computerDto);
		}
		request.setAttribute("nbComputers", computerService.getNbComputers());
		request.setAttribute("computers", computersDto);
		request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
