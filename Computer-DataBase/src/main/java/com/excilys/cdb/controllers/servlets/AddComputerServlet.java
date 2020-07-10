package com.excilys.cdb.controllers.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.mappers.CompanyDtoMapper;
import com.excilys.cdb.dto.mappers.ComputerDtoMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet(name="AddComputerServlet", urlPatterns="/addComputer")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 2L;
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;
	
	
       
    @Override
	public void init(ServletConfig config) throws ServletException {
    	super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,config.getServletContext());
	}

	/**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServlet() {
    	super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<CompanyDto> companiesDto = new ArrayList<>();
		List<Company> companies = new ArrayList<>();
		companies = companyService.getAllCompanies();
		companiesDto = companies.stream().map(company->CompanyDtoMapper.companyToCompanyDto(company)).collect(Collectors.toList());
		request.setAttribute("companies", companiesDto);
		request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerDto computerDto = new ComputerDto();
		computerDto = buildComputerDto(request);
		try {
			testComputerDtoName(computerDto);
			Computer newComputer = ComputerDtoMapper.computerDtoToComputer(computerDto);
			newComputer = computerService.createComputer(newComputer);
			String success = "Computer " + newComputer.getName() + " was successfully added";
			request.setAttribute("success", success);
			
		}catch(IllegalArgumentException e) {
			request.setAttribute("error", e.getMessage());
			request.setAttribute("newComputer", computerDto);
		}finally {
			doGet(request, response);
		}
	}
	
	private ComputerDto buildComputerDto(HttpServletRequest request) {
		ComputerDto computerDto = new ComputerDto();
		if(request.getParameter("computerName") != null && !request.getParameter("computerName").equals("")) {
			computerDto.setName(request.getParameter("computerName"));
		}
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
		return computerDto;
	}
	
	private void testComputerDtoName(ComputerDto computerDto) {
		if(computerDto.getName() == null || computerDto.getName().trim().equals("")) {
			throw new IllegalArgumentException("Computer Name must not be empty");
		}
	}

}
