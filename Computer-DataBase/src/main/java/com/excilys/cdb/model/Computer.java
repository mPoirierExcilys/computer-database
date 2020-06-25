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

	public void setIntroduced(LocalDate introduced){
		if(this.discontinued != null) {
			if(introduced.compareTo(this.discontinued) < 0) {
				this.introduced = introduced;
			}
			else {
				throw new IllegalArgumentException("Introduced date must be lesser than discontinued");
			}
		}
		else {
			this.introduced = introduced;
		}
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued){
		if(this.introduced != null) {
			if(this.introduced.compareTo(discontinued) < 0) {
				this.discontinued = discontinued;
			}
			else {
				throw new IllegalArgumentException("Discontinued date must be greater than introduced");
			}
		}
		else {
			this.discontinued = discontinued;
		}
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
	
	 @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((idComputer == null) ? 0 : idComputer.hashCode());
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
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
		Computer other = (Computer) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (idComputer == null) {
			if (other.idComputer != null)
				return false;
		} else if (!idComputer.equals(other.idComputer))
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	 

}
