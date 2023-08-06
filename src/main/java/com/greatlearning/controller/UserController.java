package com.greatlearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.entity.User;
import com.greatlearning.repository.RoleRepository;
import com.greatlearning.service.UserServiceImpl;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	RoleRepository roleRepository;

	@PostMapping
	public String saveUser(@RequestBody User user) throws Exception {
		return userServiceImpl.saveUser(user);
	}

}
