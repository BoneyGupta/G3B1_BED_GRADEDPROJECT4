package com.gl.ems2.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gl.ems2.model.User;
import com.gl.ems2.repository.UserRepository;
import com.gl.ems2.security.MyUserDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	// Verify user name present for authentication
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = new User();
		user.setUsername(username);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withMatcher("ticketContent", ExampleMatcher.GenericPropertyMatchers.exact())
				.withIgnorePaths("id", "password");
		Example<User> example = Example.of(user, exampleMatcher);
		Optional<User> user2 = userRepository.findOne(example);

		if (user2 == null) {
			throw new UsernameNotFoundException("Could not find user");
		}

		return new MyUserDetails(user);
	}

	// Save or Create operation
	public User saveUser(User user) {

		return userRepository.save(user);
	}

	// Read operation
	public List<User> fetchUserList() {

		return userRepository.findAll();
	}

	// Update Operation
	public User updateUser(User user, Integer userId) {

		User userDB = userRepository.findById(userId).get();

		if (Objects.nonNull(user.getUsername()) && !"".equalsIgnoreCase(user.getUsername())) {
			userDB.setUsername(user.getUsername());
		}

		return userRepository.save(userDB);
	}

	// Delete Operation
	public void deleteUserById(Integer userId) {

		userRepository.deleteById(userId);

	}

	// Find User by ID
	public Optional<User> getUser(Integer Id) {	
		Optional<User> user= userRepository.findById(Id);
		if(user==null) {
			return user;
		}
		return null;

	}

}
