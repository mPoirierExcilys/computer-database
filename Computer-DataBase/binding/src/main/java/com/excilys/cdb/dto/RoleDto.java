package com.excilys.cdb.dto;

import javax.validation.constraints.NotNull;

public class RoleDto {
	
	@NotNull(message = "Role must have an id")
	private Integer id;
	
	private String name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "RoleDto [id=" + id + ", name=" + name + "]";
	}
	
	

}
