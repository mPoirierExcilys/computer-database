package com.excilys.cdb.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
	
	private String name;
	
	private String password;
	
	private List<RoleDto> roles;
	
	public UserDto() {
		this.roles = new ArrayList<>();
	}
	
	public UserDto(String name, List<RoleDto> roles) {
		this.name = name;
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RoleDto> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDto [name=" + name + ", password=" + password + ", roles=" + roles + "]";
	}
	
}
