package com.excilys.cdb.dto.mappers;

import com.excilys.cdb.dto.RoleDto;
import com.excilys.cdb.model.Role;

public class RoleDtoMapper {
	
	private RoleDtoMapper() {
	}
	
	public static RoleDto roleToRoleDto(Role role) {
		RoleDto roleDto = new RoleDto();
		roleDto.setId(role.getId());
		roleDto.setName(role.getName());
		return roleDto;
	}

}
