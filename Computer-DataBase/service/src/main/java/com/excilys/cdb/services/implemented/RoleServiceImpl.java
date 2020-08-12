package com.excilys.cdb.services.implemented;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.RoleDao;
import com.excilys.cdb.model.Role;
import com.excilys.cdb.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleDao roleDao;

	@Override
	public List<Role> getAllRole() {
		return roleDao.findAll();
	}
}
