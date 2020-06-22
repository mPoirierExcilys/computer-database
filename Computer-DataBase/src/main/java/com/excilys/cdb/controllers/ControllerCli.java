package com.excilys.cdb.controllers;

import java.util.Scanner;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.ComputerDBService;
import com.excilys.cdb.services.ComputerDBServiceImpl;
import com.excilys.cdb.ui.CliUi;

public class ControllerCli {
	
	private final ComputerDBService service;
	private final CliUi cliUi;
	private Scanner sc;
	
	public ControllerCli() {
		this.service = new ComputerDBServiceImpl();
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
				Integer totalPage = service.getCompaniesNbPages();
				boolean test = true;
				do {
					cliUi.listAllCompanies(service.getCompaniesByPage(currentPage),currentPage,totalPage);
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
				Integer totalPage1 = service.getComputersNbPages();
				boolean test1 = true;
				do {
					cliUi.listAllComputers(service.getComputersByPage(currentPage1),currentPage1,totalPage1);
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
				cliUi.computerDetails(service.getComputer(computerId));
				break;
			case "4":
				Computer computer = cliUi.createComputer();
				computer = service.createComputer(computer);
				cliUi.computerDetails(computer);
				break;
			case "5":
				cliUi.idComputerToUpdate();
				int computerId1 = Integer.parseInt(sc.next());
				Computer computer1 = service.getComputer(computerId1);
				cliUi.computerDetails(computer1);
				computer1 = cliUi.updateComputer(computer1);
				computer1 = service.updateComputer(computer1);
				cliUi.computerDetails(computer1);
				break;
			case "6":
				cliUi.deleteComputer();
				int computerId2 = Integer.parseInt(sc.next());
				service.deleteComputer(service.getComputer(computerId2));
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
