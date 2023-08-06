package com.gl.ems2.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.ems2.model.Role;
import com.gl.ems2.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	// Save or Create operation
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	// Read operation
	public List<Role> fetchRoleList() {
		return (List<Role>) roleRepository.findAll();
	}

	// Update Operation
	public Role updateRole(Role role, Integer Id) {

		Role roleDB = roleRepository.findById(Id).get();

		if (Objects.nonNull(role.getName()) && !"".equalsIgnoreCase(role.getName())) {
			roleDB.setName(role.getName());
		}

		return roleRepository.save(roleDB);
	}

	// Delete Operation
	public void deleteRoleById(Integer Id) {

		roleRepository.deleteById(Id);

	}

}
