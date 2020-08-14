package com.excilys.cdb.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.configuration.jwt.JwtTokenUtil;
import com.excilys.cdb.dto.RoleDto;
import com.excilys.cdb.dto.UserDto;
import com.excilys.cdb.dto.mappers.RoleDtoMapper;
import com.excilys.cdb.dto.mappers.UserDtoMapper;
import com.excilys.cdb.model.JwtRequest;
import com.excilys.cdb.model.JwtResponse;
import com.excilys.cdb.model.Role;
import com.excilys.cdb.model.User;
import com.excilys.cdb.services.RoleService;
import com.excilys.cdb.services.implemented.CustomUserDetailsService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class JwtAuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public UserDto getUserInfo(@RequestHeader(value="Authorization") String requestTokenHeader){
		String username = null;
		String jwtToken = null;
		jwtToken = requestTokenHeader.substring(7);
		username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		User user = this.userDetailsService.getUserByUsername(username);
		UserDto userDto = UserDtoMapper.userToUserDto(user);
		return userDto;
	}
	
	@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
	@RequestMapping(value = "/roles", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<RoleDto> getRoles(){
		List<Role> roles = new ArrayList<>();
		List<RoleDto> rolesDto = new ArrayList<>();
		roles = roleService.getAllRole();
		rolesDto = roles.stream().map(role -> RoleDtoMapper.roleToRoleDto(role)).collect(Collectors.toList());
		return rolesDto;
	}
	
	@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
	@RequestMapping(value="/register", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> addUser(@RequestBody @Valid UserDto userDto) throws URISyntaxException{
		User user = userDetailsService.saveUser(UserDtoMapper.userDtoToUser(userDto));
		return ResponseEntity.created(new URI("")).body(user.getName() + " created");
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
