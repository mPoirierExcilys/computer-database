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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Company other = (Company) obj;
        if(idCompany == null) {
        	if(other.idCompany != null) {
        		return false;
        	}
        }
        else if(!idCompany.equals(other.idCompany)) {
        	return false;
        }
        if(name == null) {
        	if(other.name != null) {
        		return false;
        	}
        }
        else if(!name.contentEquals(other.name)) {
        	return false;
        }
        return true;
	}

}
