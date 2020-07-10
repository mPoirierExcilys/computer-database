package com.excilys.cdb.controllers;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.excilys.cdb.configuration.SpringConfigurationContext;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.ui.CliUi;

@Component
public class ControllerCli {
	
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CliUi cliUi;
	private Scanner sc;
	private Page page;
	private static final String ORDER_COMPUTER="computer.id";
	private static final String ORDER_COMPANY="company.id";
	private static final String ASCENDING="ASC";
	
	public ControllerCli() {
		this.sc = new Scanner(System.in);
		this.page = new Page();
		this.page.setAscending(ASCENDING);
	}
	
	public void CdbLogical() {
		boolean run = true;
		cliUi.start();
		while(run) {
			cliUi.instructions();
			String input = sc.next();
			switch(input) {
			case "1":
				page.setCurrentPage(1);
				page.setNbPage(companyService.getCompaniesNbPages(page));
				page.setOrder(ORDER_COMPANY);
				boolean test = true;
				do {
					cliUi.listAllCompanies(companyService.getCompaniesByPage(page),page.getCurrentPage(),page.getNbPage());
					String input1 = sc.next();
					try {
						Integer pageWish = Integer.parseInt(input1);
						page.setCurrentPage(pageWish);
					}catch(NumberFormatException e) {
						if(input1.equals("q")) {
							test = false;
						}
						page.incrementCurrentPage();
					}
				}while(test && page.getCurrentPage()<=page.getNbPage());
				break;
			case "2":
				boolean test1 = true;
				page.setCurrentPage(1);
				page.setNbPage(computerService.getComputersNbPages(page));
				page.setOrder(ORDER_COMPUTER);
				do {
					cliUi.listAllComputers(computerService.getComputersByPage(page),page.getCurrentPage(),page.getNbPage());
					String input1 = sc.next();
					try {
						Integer pageWish = Integer.parseInt(input1);
						page.setCurrentPage(pageWish);
					}catch(NumberFormatException e) {
						if(input1.equals("q")) {
							test1 = false;
						}
						page.incrementCurrentPage();;
					}
				}while(test1 && page.getCurrentPage()<=page.getNbPage());
				break;
			case "3":
				cliUi.enterComputerId();
				int computerId = Integer.parseInt(sc.next());
				cliUi.computerDetails(computerService.getComputer(computerId));
				break;
			case "4":
				Computer computer = cliUi.createComputer();
				computer = computerService.createComputer(computer);
				cliUi.computerDetails(computer);
				break;
			case "5":
				cliUi.idComputerToUpdate();
				int computerId1 = Integer.parseInt(sc.next());
				Computer computer1 = computerService.getComputer(computerId1);
				cliUi.computerDetails(computer1);
				computer1 = cliUi.updateComputer(computer1);
				computer1 = computerService.updateComputer(computer1);
				cliUi.computerDetails(computer1);
				break;
			case "6":
				cliUi.deleteComputer();
				int computerId2 = Integer.parseInt(sc.next());
				computerService.deleteComputer(computerId2);
				break;
			case "7":
				cliUi.deleteCompany();
				int companyId = Integer.parseInt(sc.next());
				companyService.deleteCompany(companyId);
				break;
			case "8":
				run = false;
				break;
			default:
				cliUi.wrongInstruction();		
			}
		}
		cliUi.goodbye();
	}

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfigurationContext.class);
		ControllerCli controller = context.getBean(ControllerCli.class);
		controller.CdbLogical();
		
	}

}
