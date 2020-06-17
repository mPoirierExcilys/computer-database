package com.excilys.cdb.ui;

import java.util.List;
import java.util.Scanner;

import com.excilys.cdb.model.Computer;

public class ComputerPage {
private List<Computer> allComputers;
	
	private Scanner sc;
	
	private static final int NB_ELEMENTS_BY_PAGE = 10;
	
	private Integer offset;
	
	public ComputerPage(List<Computer> allComputers) {
		this.allComputers = allComputers;
		this.offset = 0;
		this.sc = new Scanner(System.in);
	}
	
	public void print() {
		int size = allComputers.size()+1;
		if(size < offset+NB_ELEMENTS_BY_PAGE) {
			for(Computer computer: allComputers) {
				System.out.println(computer);
			}
		}
		else {
			String input = "";
			List<Computer> subList;
			boolean stop = true;
			while(offset+NB_ELEMENTS_BY_PAGE <= size && stop) {
				subList = allComputers.subList(offset, offset+NB_ELEMENTS_BY_PAGE-1);
				offset+=NB_ELEMENTS_BY_PAGE;
				for(Computer computer: subList) {
					System.out.println(computer);
				}
				System.out.println("Press enter to continue or q for break");
				input = sc.nextLine();
				if(input.equals("q")) {
					stop = false;
				}
			}
			if(offset < size && stop) {
				subList = allComputers.subList(offset, size-1);
				for(Computer computer: subList) {
					System.out.println(computer);
				}
			}
			
		}
		
	}
}
