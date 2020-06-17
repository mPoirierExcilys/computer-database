package com.excilys.cdb.ui;

import java.util.List;
import java.util.Scanner;

import com.excilys.cdb.model.Company;

public class CompanyPage {
	
	private List<Company> allCompanies;
	
	private Scanner sc;
	
	private static final int NB_ELEMENTS_BY_PAGE = 10;
	
	private Integer offset;
	
	public CompanyPage(List<Company> allCompanies) {
		this.allCompanies = allCompanies;
		this.offset = 0;
		this.sc = new Scanner(System.in);
	}
	
	public void print() {
		int size = allCompanies.size()+1;
		if(size < offset+NB_ELEMENTS_BY_PAGE) {
			for(Company company: allCompanies) {
				System.out.println(company);
			}
		}
		else {
			String input = "";
			List<Company> subList;
			boolean stop = true;
			while(offset+NB_ELEMENTS_BY_PAGE <= size && stop) {
				subList = allCompanies.subList(offset, offset+NB_ELEMENTS_BY_PAGE-1);
				offset+=NB_ELEMENTS_BY_PAGE;
				for(Company company: subList) {
					System.out.println(company);
				}
				System.out.println("Press enter to continue or q for break");
				input = sc.nextLine();
				if(input.equals("q")) {
					stop = false;
				}
			}
			if(offset < size && stop) {
				subList = allCompanies.subList(offset, size-1);
				for(Company company: subList) {
					System.out.println(company);
				}
			}
			
		}
		
	}

}
