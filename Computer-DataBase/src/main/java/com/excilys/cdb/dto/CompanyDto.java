package com.excilys.cdb.dto;

public class CompanyDto {
	
	private Integer idCompany;
	private String name;
	
	
	public CompanyDto() {
	}
	
	public CompanyDto(Integer idCompany, String name) {
		this.idCompany = idCompany;
		this.name = name;
	}

	public Integer getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(Integer idCompany) {
		this.idCompany = idCompany;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
