package com.excilys.cdb.controllers;

import java.util.Scanner;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.services.CompanyDBService;
import com.excilys.cdb.services.CompanyDBServiceImpl;
import com.excilys.cdb.services.ComputerDBService;
import com.excilys.cdb.services.ComputerDBServiceImpl;
import com.excilys.cdb.ui.CliUi;

public class ControllerCli {
	
	private final ComputerDBService computerService;
	private final CompanyDBService companyService;
	private final CliUi cliUi;
	private Scanner sc;
	private Page page;
	private final String order="computer.id";
	private final String ascending="ASC";
	
	public ControllerCli() {
		this.computerService = new ComputerDBServiceImpl();
		this.companyService = new CompanyDBServiceImpl();
		this.cliUi = new CliUi();
		this.sc = new Scanner(System.in);
		this.page = new Page(1,10);
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
				do {
					cliUi.listAllComputers(computerService.getComputersByPage(page, order, ascending),page.getCurrentPage(),page.getNbPage());
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
				computerService.deleteComputer(computerService.getComputer(computerId2));
				break;
			case "7":
				run = false;
				break;
			default:
				cliUi.wrongInstruction();		
			}
		}
		cliUi.goodbye();
	}

	public static void main(String[] args) {
		ControllerCli controller = new ControllerCli();
		controller.CdbLogical();
		
	}

}
