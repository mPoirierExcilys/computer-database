package com.excilys.cdb.services.implemented;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.UserDao;
import com.excilys.cdb.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User with name : "+username+" not found"));
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), getAuthorities(user));
	}
	
	private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }
	
	public User getUserByUsername(String username) throws UsernameNotFoundException{
		User user = userDao.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User with name : "+username+" not found"));
		return user;
	}
	
	public User getUserById(Integer id) throws UsernameNotFoundException{
		User user = userDao.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with id : "+id+" not found"));
		return user;
	}
	
	public List<User> getUsers() {
		return userDao.findAll().orElse(null);
	}
	
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userDao.create(user);
	}

	public User modifyByAdmin(User user) {
		if(user.getPassword() != null && user.getPassword() != "") {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}		
		return userDao.modify(user, true);
	}
	
	public User modify(User user) {
		if(user.getPassword() != null && user.getPassword() != "") {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		return userDao.modify(user, false);
	}
	
	public boolean checkNoUserWithSameName(Integer id, String name) {
		return userDao.isUserByNotIdAndByName(id, name);
	}
}
