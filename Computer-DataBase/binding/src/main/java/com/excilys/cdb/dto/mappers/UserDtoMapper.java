package com.excilys.cdb.dto.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.excilys.cdb.dto.RoleDto;
import com.excilys.cdb.dto.UserDto;
import com.excilys.cdb.model.User;

public class UserDtoMapper {
		
	private UserDtoMapper() {
	}
	
	public static UserDto userToUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		List<RoleDto> roles = user.getRoles().stream().map(role -> RoleDtoMapper.roleToRoleDto(role)).collect(Collectors.toList());
		userDto.setRoles(roles);
		return userDto;
	}
	
	public static UserDto userToUserDtoWithPassword(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		List<RoleDto> roles = user.getRoles().stream().map(role -> RoleDtoMapper.roleToRoleDto(role)).collect(Collectors.toList());
		userDto.setRoles(roles);
		return userDto;
	}
	
	public static User userDtoToUser(UserDto userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		if(!userDto.getPassword().equals("byDefault")) {
			user.setPassword(userDto.getPassword());
		}
		user.setRoles(userDto.getRoles().stream().map(roleDto -> RoleDtoMapper.roleDtoToRole(roleDto)).collect(Collectors.toList()));
		return user;
	}
}
