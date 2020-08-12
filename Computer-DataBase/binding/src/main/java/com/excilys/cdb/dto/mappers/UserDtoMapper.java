package com.excilys.cdb.dto.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.excilys.cdb.dto.UserDto;
import com.excilys.cdb.model.User;

public class UserDtoMapper {
	
	private UserDtoMapper() {
	}
	
	public static UserDto userToUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setName(user.getName());
		List<String> roles = user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList());
		userDto.setRoles(roles);
		return userDto;
	}
}
