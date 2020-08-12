package com.excilys.cdb.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
	
	private String name;
	
	private List<String> roles;
	
	public UserDto() {
		this.roles = new ArrayList<>();
	}
	
	public UserDto(String name, List<String> roles) {
		this.name = name;
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
}
