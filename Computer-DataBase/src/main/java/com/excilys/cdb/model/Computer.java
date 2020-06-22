package com.excilys.cdb.model;

import java.time.LocalDate;

public class Computer {
	
	private Integer idComputer;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Integer companyId;
	
	public Computer() {
	}
	
	public Computer(Integer idComputer,String name) {
		this.idComputer = idComputer;
		this.name = name;
	}
	
	public Computer(Integer idComputer, String name, LocalDate introduced, LocalDate discontinued, Integer companyId) {
		this.idComputer = idComputer;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getIdComputer() {
		return idComputer;
	}
	
	public void setIdComputer(Integer idComputer) {
		this.idComputer = idComputer;
	}
	
	@Override
	public String toString() {
		return name + " with id : "+ idComputer +" from Company id :" + companyId + " introduced " + introduced + " and discontinued " + discontinued + "\n" +
				"-------------------------------------------------- \n";
	}

}
