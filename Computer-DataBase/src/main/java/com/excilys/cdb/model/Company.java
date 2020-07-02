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
	
	public void setIdCompany(Integer idCompany) {
		this.idCompany = idCompany;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name +"\n--------------------------------------------------------\n";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCompany == null) ? 0 : idCompany.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (idCompany == null) {
			if (other.idCompany != null)
				return false;
		} else if (!idCompany.equals(other.idCompany))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

}
