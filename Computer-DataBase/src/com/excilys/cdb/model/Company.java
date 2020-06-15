package com.excilys.cdb.model;

public class Company {
	
	private Integer idCompany;
	private String name;
	
	
	public Company() {
	}
	
	public Company(Integer idCompany, String name) {
		this.idCompany = idCompany;
		this.name = name;
	}

	public Integer getIdCompany() {
		return idCompany;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name +"\n--------------------------------------------------------\n";
	}

}
