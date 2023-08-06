package com.gl.ems2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ems2.model.Employee;
import com.gl.ems2.service.MyService;

@RestController
@RequestMapping("/api")
public class MyController {
	@Autowired
	private MyService svc;

	// Save or Create Operation
	@PostMapping("/employee")
	@ResponseBody
	public Employee roleAddSave(@Validated @RequestBody Employee employee) {
		return svc.saveEmployee(employee);

	}

	// Read Operations
	@GetMapping("/employee")
	@ResponseBody
	public List<Employee> userRead() {

		return svc.fetchEmployeeList();

	}

	// Update Operation
	@PutMapping("/employee/{Id}")
	@ResponseBody
	public Employee updateEmployee(@RequestBody Employee employee, @PathVariable("Id") Integer Id) {
		return svc.updateEmployee(employee, Id);

	}

	// Search Employee by ID Operation
	@GetMapping("/employee/{Id}")
	@ResponseBody
	public Employee getEmployeeById(@PathVariable("Id") Integer Id) {

		return svc.fetchEmployeeById(Id);

	}

	// Delete Operation
	@DeleteMapping("/employee/{Id}")
	@ResponseBody
	public String deleteEmployeeById(@PathVariable("Id") Integer Id) {

		svc.deleteEmployeeById(Id);
		return "Deleted employee id - " + Id;

	}

	// Search By First Name Operations
	@GetMapping("/employee/search/{FirstName}")
	@ResponseBody
	public List<Employee> serachByFristName(@PathVariable("FirstName") String FirstName) {

		return svc.fetchEmployeeListByFirstName(FirstName);

	}

	// Sort By First Name Operations
	@GetMapping("/employee/sort")
	@ResponseBody
	public List<Employee> sortByFristName(@RequestParam String order) {

		return svc.fetchEmployeeListSorted(order);

	}
}
