package com.gl.ems2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gl.ems2.model.Employee;
import com.gl.ems2.service.MyService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class EmployeeManagementSystem2Application implements CommandLineRunner {

	@Autowired
	MyService svc;

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystem2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Persist an employee", svc.saveEmployee(
				Employee.builder().firstName("First Name 1").lastName("Last Name 1").email("Email 1").build()));
		log.info("Persist an employee", svc.saveEmployee(
				Employee.builder().firstName("First Name 2").lastName("Last Name 2").email("Email 2").build()));
		log.info("Persist an employee", svc.saveEmployee(
				Employee.builder().firstName("First Name 3").lastName("Last Name 3").email("Email 3").build()));
		log.info("Fetch all employees-> {}", svc.fetchEmployeeList());

	}

}
