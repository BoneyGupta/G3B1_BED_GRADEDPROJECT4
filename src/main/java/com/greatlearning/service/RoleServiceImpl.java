package com.greatlearning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatlearning.entity.Role;
import com.greatlearning.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;

	@Override
	public String saveRole(Role role) throws Exception {
		Role savedRole = roleRepository.findByName(role.getName());
		if (savedRole == null) {
			roleRepository.saveAndFlush(role);
			return "Role Saved Successfully";
		} else {
			throw new Exception("Role [ " + role.getName() + " ] is already available");
		}
	}
}
