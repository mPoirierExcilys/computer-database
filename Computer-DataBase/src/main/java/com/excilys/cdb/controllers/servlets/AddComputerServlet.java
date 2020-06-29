package com.excilys.cdb.controllers.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.mappers.CompanyMapper;
import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.CompanyDBService;
import com.excilys.cdb.services.CompanyDBServiceImpl;
import com.excilys.cdb.services.ComputerDBService;
import com.excilys.cdb.services.ComputerDBServiceImpl;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet(name="AddComputerServlet", urlPatterns="/addComputer")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CompanyDBService companyService;
	private ComputerDBService computerService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServlet() {
        super();
        this.companyService = new CompanyDBServiceImpl();
        this.computerService = new ComputerDBServiceImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<CompanyDto> companiesDto = new ArrayList<>();
		List<Company> companies = new ArrayList<>();
		companies = companyService.getAllCompanies();
		for(Company company: companies) {
			CompanyDto companyDto = CompanyMapper.companyToCompanyDto(company);
			companiesDto.add(companyDto);
		}
		request.setAttribute("companies", companiesDto);
		request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerDto computerDto = new ComputerDto();
		computerDto.setName(request.getParameter("computerName"));
		if(request.getParameter("introduced") != null && !request.getParameter("introduced").equals("")) {		
			computerDto.setIntroduced(request.getParameter("introduced"));
		}
		if(request.getParameter("discontinued") != null && !request.getParameter("discontinued").equals("")){
			computerDto.setDiscontinued(request.getParameter("discontinued"));
		}
		if(!request.getParameter("companyId").equals("0")) {
			CompanyDto companyDto = new CompanyDto();
			companyDto.setIdCompany(Integer.parseInt(request.getParameter("companyId")));
			computerDto.setCompany(companyDto);
		}
		try {
			Computer newComputer = ComputerMapper.computerDtoToComputer(computerDto);
			newComputer = computerService.createComputer(newComputer);
			String success = "Computer " + newComputer.getName() + " was successfully add";
			request.setAttribute("success", success);
			
		}catch(IllegalArgumentException e) {
			request.setAttribute("success", e.getMessage());
			request.setAttribute("newComputer", computerDto);
		}finally {
			doGet(request, response);
		}
	}

}
