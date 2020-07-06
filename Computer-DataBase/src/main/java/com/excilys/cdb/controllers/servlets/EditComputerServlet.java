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
import com.excilys.cdb.dto.mappers.CompanyDtoMapper;
import com.excilys.cdb.dto.mappers.ComputerDtoMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.services.implemented.CompanyServiceImpl;
import com.excilys.cdb.services.implemented.ComputerServiceImpl;

/**
 * Servlet implementation class EditComputerServlet
 */
@WebServlet(name="EditComputerServlet", urlPatterns="/editComputer")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ComputerService computerService;
	private CompanyService companyService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputerServlet() {
        super();
        computerService = new ComputerServiceImpl();
        companyService = new CompanyServiceImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<CompanyDto> companiesDto = new ArrayList<>();
		List<Company> companies = new ArrayList<>();
		companies = companyService.getAllCompanies();
		for(Company company: companies) {
			CompanyDto companyDto = CompanyDtoMapper.companyToCompanyDto(company);
			companiesDto.add(companyDto);
		}
		request.setAttribute("companies", companiesDto);
		String error;
		if(request.getParameter("id") != null) {
			try {
				Integer id = Integer.parseInt(request.getParameter("id"));
				Computer computer = computerService.getComputer(id);
				ComputerDto computerDto = ComputerDtoMapper.computerToComputerDto(computer);
				request.setAttribute("computer", computerDto);
			}catch(NumberFormatException e) {
				error = "Id is not an Integer";
				request.setAttribute("error", error);
			}finally {
				request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
			}
		}else {
			request.getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.parseInt(request.getParameter("id"));
		Computer computer = computerService.getComputer(id);
		ComputerDto computerDtoOld = ComputerDtoMapper.computerToComputerDto(computer);
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
			testComputerDtoAttributesNotSetToNull(computerDtoOld, computerDto);
			computerDto.setIdComputer(computerDtoOld.getIdComputer());
			Computer newComputer = ComputerDtoMapper.computerDtoToComputer(computerDto);
			newComputer = computerService.updateComputer(newComputer);
			String success = "Computer " + newComputer.getName() + " was successfully updated";
			request.setAttribute("success", success);
			request.setAttribute("computer", ComputerDtoMapper.computerToComputerDto(newComputer));
		}catch(IllegalArgumentException e) {
			request.setAttribute("error", e.getMessage());
			request.setAttribute("computer", computerDtoOld);
		}finally {
			doGet(request, response);
		}
	}
	
	private void testComputerDtoAttributesNotSetToNull(ComputerDto computerDtoOld, ComputerDto computerDto){
		if(computerDto.getName().trim().equals("")) {
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

}
