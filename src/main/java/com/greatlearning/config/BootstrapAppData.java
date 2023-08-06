package com.greatlearning.config;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.github.javafaker.Faker;
import com.greatlearning.entity.Employee;
import com.greatlearning.entity.Role;
import com.greatlearning.entity.User;
import com.greatlearning.repository.EmployeeRepository;
import com.greatlearning.repository.UserRepository;

@Configuration
public class BootstrapAppData {

	@Bean
	public RestTemplate resTemplate() {
		return new RestTemplate();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private Faker faker = new Faker();

	@EventListener(ApplicationReadyEvent.class)
	public void addEmployees(ApplicationReadyEvent event) {

		for (int i = 0; i < 5; i++) {
			Employee employee = new Employee();

			employee.setFirstName(faker.name().firstName());
			employee.setLastName(faker.name().lastName());
			employee.setEmail(faker.internet().emailAddress());

			this.employeeRepository.saveAndFlush(employee);
		}

	}

	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void addUsersAndRoles(ApplicationReadyEvent event) {

		User user1 = new User();
		User user2 = new User();

		user1.setUsername("admin");
		user1.setPassword(this.passwordEncoder.encode("ADMIN"));

		user2.setUsername("user");
		user2.setPassword(this.passwordEncoder.encode("USER"));

		Role userRole = new Role();
		Role adminRole = new Role();

		userRole.setName("ROLE_USER");
		adminRole.setName("ROLE_ADMIN");

		user1.addRole(adminRole);
		user1.addRole(userRole);

		user2.addRole(userRole);

		this.userRepository.save(user1);
		this.userRepository.save(user2);

	}
}
