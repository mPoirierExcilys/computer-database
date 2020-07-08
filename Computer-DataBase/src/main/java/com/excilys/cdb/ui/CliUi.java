package com.excilys.cdb.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Component
public class CliUi {
	
	private Scanner sc;
	
	public CliUi() {
		this.sc = new Scanner(System.in);
	}
	
	public void start() {
		System.out.println("****************Welcome to the Computer DataBase****************");
		System.out.println("In this interface you could find all companies and computers");
		System.out.println("You can show details of a computer");
		System.out.println("And you can create, update or delete a computer");
		System.out.println("****************************************************************");
	}
	
	public void instructions() {
		System.out.println("1. To list all Companies in the DB press 1");
		System.out.println("2. To list all Computers in the DB press 2");
		System.out.println("3. To show details from a computer press 3");
		System.out.println("4. To create a new Computer press 4");
		System.out.println("5. To update a computer press 5");
		System.out.println("6. To delete a computer press 6");
		System.out.println("7. To delete a company press 7");
		System.out.println("8. To exit Computer Database press 8");
	}
	
	public void wrongInstruction() {
		System.out.println("Sorry maybe you missclicked please press 1, 2, 3, 4, 5, 6 or 7");
	}
	
	public void goodbye() {
		System.out.println("Goodbye, Thanks For using Computer DataBase");
		System.out.println("*********************************************");
	}
	
	public void enterComputerId() {
		System.out.println("Please Enter the Id (Integer) from the computer you want to show detail : ");
	}
	
	public Computer createComputer() {
		Computer computer = new Computer();
		System.out.println("Please Enter the name : ");
		String name = sc.nextLine();
		computer.setName(name);
		System.out.println("Would you like to insert companyId ? (yes or anything for no)");
		String nextInput = sc.nextLine();
		if(nextInput.equals("yes")) {
			System.out.println("Enter the idCompany (Integer) : ");
			int idCompany = Integer.parseInt(sc.nextLine());
			computer.setCompany(new Company());
			computer.getCompany().setIdCompany(idCompany);
		}
		System.out.println("Would you like to insert introduced Date ? (yes or anything for no)");
		nextInput = sc.nextLine();
		if(nextInput.equals("yes")) {
			System.out.println("Enter the year (Integer) : ");
			int year = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the month (Integer) : ");
			int month = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the day (Integer) : ");
			int day = Integer.parseInt(sc.nextLine());
			computer.setIntroduced(LocalDate.of(year,month,day));
		}
		System.out.println("Would you like to insert discontinued ? (yes or anything for no)");
		nextInput = sc.nextLine();
		if(nextInput.equals("yes")) {
			System.out.println("Enter the year (Integer) : ");
			int year = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the month (Integer) : ");
			int month = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the day (Integer) : ");
			int day = Integer.parseInt(sc.nextLine());
			computer.setDiscontinued(LocalDate.of(year, month, day));
		}
		return computer;
	}
	
	public void listAllCompanies(List<Company> allCompanies, Integer currentPage, Integer totalPage) {
		for(Company company: allCompanies) {
			System.out.println(company);
		}
		System.out.println("Page "+ currentPage+" of "+ totalPage);
		System.out.println("Press q for exit, anything to go to the next page or a page you want to go ");
	}
	
	public void listAllComputers(List<Computer> allComputers, Integer currentPage, Integer totalPage) {
		for(Computer computer: allComputers) {
			System.out.println(computer);
		}
		System.out.println("Page "+ currentPage+" of "+ totalPage);
		System.out.println("Press q for exit, anything to go to the next page or a page you want to go ");
	}
	
	public void computerDetails(Computer computer) {
		System.out.println(computer);
	}
	
	public void deleteComputer() {
		System.out.println("Enter the id of the computer you want to delete : ");
	}
	
	public void deleteCompany() {
		System.out.println("Enter the id of the company you want to delete : ");
	}
	
	public void idComputerToUpdate() {
		System.out.println("Enter the id of the computer you want to update : ");
	}
	
	public Computer updateComputer(Computer computer) {
		System.out.println("Would you like to change the name ? (yes or anything for no)");
		String nextInput = sc.nextLine();
		if(nextInput.equals("yes")) {
			System.out.println("Please Enter the name : ");
			String name = sc.nextLine();
			computer.setName(name);
		}
		System.out.println("Would you like to change the companyID ? (yes or anything for no)");
		nextInput = sc.nextLine();
		if(nextInput.equals("yes")) {
			System.out.println("Enter the idCompany (Integer) : ");
			int idCompany = Integer.parseInt(sc.nextLine());
			computer.setCompany(new Company());
			computer.getCompany().setIdCompany(idCompany);
		}
		System.out.println("Would you like to change the introduced Date ? (yes or anything for no)");
		nextInput = sc.nextLine();
		if(nextInput.equals("yes")) {
			System.out.println("Enter the year (Integer) : ");
			int year = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the month (Integer) : ");
			int month = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the day (Integer) : ");
			int day = Integer.parseInt(sc.nextLine());
			computer.setIntroduced(LocalDate.of(year,month,day));
		}
		System.out.println("Would you like to change the discontinued Date ? (yes or anything for no)");
		nextInput = sc.nextLine();
		if(nextInput.equals("yes")) {
			System.out.println("Enter the year (Integer) : ");
			int year = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the month (Integer) : ");
			int month = Integer.parseInt(sc.nextLine());
			System.out.println("Enter the day (Integer) : ");
			int day = Integer.parseInt(sc.nextLine());
			computer.setDiscontinued(LocalDate.of(year, month, day));
		}
		return computer;
	}

}
