package com.excilys.cdb.controllers;

import java.util.Scanner;

import com.excilys.cdb.model.Computer;
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
	
	public ControllerCli() {
		this.computerService = new ComputerDBServiceImpl();
		this.companyService = new CompanyDBServiceImpl();
		this.cliUi = new CliUi();
		this.sc = new Scanner(System.in);
	}
	
	public void CdbLogical() {
		boolean run = true;
		cliUi.start();
		while(run) {
			cliUi.instructions();
			String input = sc.next();
			switch(input) {
			case "1":
				Integer currentPage = 1;
				Integer totalPage = companyService.getCompaniesNbPages();
				boolean test = true;
				do {
					cliUi.listAllCompanies(companyService.getCompaniesByPage(currentPage),currentPage,totalPage);
					String input1 = sc.next();
					try {
						Integer page = Integer.parseInt(input1);
						currentPage = page;
					}catch(NumberFormatException e) {
						if(input1.equals("q")) {
							test = false;
						}
						currentPage++;
					}
				}while(test && currentPage<=totalPage);
				break;
			case "2":
				Integer currentPage1 = 1;
				Integer totalPage1 = computerService.getComputersNbPages();
				boolean test1 = true;
				do {
					cliUi.listAllComputers(computerService.getComputersByPage(currentPage1),currentPage1,totalPage1);
					String input1 = sc.next();
					try {
						Integer page = Integer.parseInt(input1);
						currentPage1 = page;
					}catch(NumberFormatException e) {
						if(input1.equals("q")) {
							test1 = false;
						}
						currentPage1++;
					}
				}while(test1 && currentPage1<=totalPage1);
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
