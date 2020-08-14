package com.excilys.cdb.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class UserDto {
	
	@NotBlank(message = "Name cannot be null or empty")
	@Size(min = 4, message = "Name must contain at least 4 chars")
	private String name;
	
	@NotBlank(message = "Paswword cannot be null or empty")
	@Size(min = 8, message = "Paswword must contain at least 8 chars")
	private String password;
	
	@NotEmpty(message = "User must have at least one role")
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
