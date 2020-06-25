package com.excilys.cdb.dto;


public class ComputerDto {
	
	private Integer idComputer;
	private String name;
	private String introduced;
	private String discontinued;
	private CompanyDto company;
	
	public ComputerDto() {
	}
	
	public ComputerDto(Integer idComputer, String name, String introduced, String discontinued, CompanyDto company) {
		this.idComputer = idComputer;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	public Integer getIdComputer() {
		return idComputer;
	}

	public void setIdComputer(Integer idComputer) {
		this.idComputer = idComputer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public CompanyDto getCompany() {
		return company;
	}

	public void setCompany(CompanyDto company) {
		this.company = company;
	}
	
	
}
