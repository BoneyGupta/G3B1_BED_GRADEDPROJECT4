package com.greatlearning.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.entity.Employee;
import com.greatlearning.service.EmployeeServiceImpl;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	EmployeeServiceImpl employeeServiceImpl;

	@PostMapping
	public Employee saveEmployee(@RequestBody Employee employee) {
		return employeeServiceImpl.save(employee);
	}

	@GetMapping
	public List<Employee> findAllEmployees() {
		return employeeServiceImpl.findAllEmployees();
	}

	@GetMapping("/{id}")
	public Employee findEmployeeById(@PathVariable("id") int id) {
		return employeeServiceImpl.findEmployeeById(id);
	}

	@DeleteMapping("/{id}")
	public String deleteEmployeeById(@PathVariable("id") int id) {
		return employeeServiceImpl.deleteEmployeeById(id);
	}

	@PutMapping
	public Employee updateEmployee(@RequestBody Employee employee) {
		return employeeServiceImpl.updateEmployee(employee);
	}

	@GetMapping("/search/{name}")
	public List<Employee> searchEmployeeByFirstName(@PathVariable("name") String firstName) {
		return employeeServiceImpl.searchEmployeeByFirstName(firstName);
	}

	@GetMapping("/sort")
	public List<Employee> sortEmployeeByFirstName(
			@RequestParam(name = "order", required = false, defaultValue = "asc") String order) {
		return employeeServiceImpl.sortEmployeeByFirstName(order);
	}
}
