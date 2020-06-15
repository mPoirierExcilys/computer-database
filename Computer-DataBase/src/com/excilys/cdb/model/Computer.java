package com.excilys.cdb.model;

import java.util.Date;

public class Computer {
	
	private Integer idComputer;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Integer companyId;
	
	public Computer() {
	}
	
	public Computer(Integer idComputer,String name) {
		this.idComputer = idComputer;
		this.name = name;
	}
	
	public Computer(Integer idComputer, String name, Date introduced, Date discontinued, Integer companyId) {
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

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) {
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
	
	@Override
	public String toString() {
		return name + " from Company id :" + companyId + " introduced " + introduced + " and discontinued " + discontinued + "\n" +
				"-------------------------------------------------- \n";
	}

}
