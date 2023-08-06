package com.greatlearning.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greatlearning.entity.Role;
import com.greatlearning.entity.User;
import com.greatlearning.repository.RoleRepository;
import com.greatlearning.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String saveUser(User user) throws Exception {
		String username = user.getUsername();
		Optional<User> existingUserOptional = userRepository.findByUsername(username);

		if (existingUserOptional.isPresent()) {
			throw new Exception("User already exists with username: " + username);
		}

		if (!user.getRoles().isEmpty()) {
			User newUser = new User();
			newUser.setUsername(username);
			newUser.setPassword(passwordEncoder.encode(user.getPassword()));

			for (Role role : user.getRoles()) {
				Role fetchedRole = roleRepository.findById(role.getId()).orElse(null);
				if (fetchedRole != null) {
					newUser.addRole(fetchedRole);
				} else {
					throw new Exception("Invalid role Id: " + role.getId());
				}
			}

			userRepository.save(newUser);
			return "User Added SUccessfully";
		} else {
			throw new Exception("Role is mandatory");
		}
	}
}
