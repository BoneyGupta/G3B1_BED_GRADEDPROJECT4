package com.greatlearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.entity.Role;
import com.greatlearning.service.RoleServiceImpl;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

	@Autowired
	RoleServiceImpl roleServiceImpl;

	@PostMapping
	public String saveRole(@RequestBody Role role) throws Exception {
		return roleServiceImpl.saveRole(role);
	}

}
