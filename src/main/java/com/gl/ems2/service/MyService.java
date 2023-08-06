package com.gl.ems2.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gl.ems2.model.Employee;
import com.gl.ems2.repository.MyRepository;

@Service
public class MyService {

	@Autowired
	private MyRepository repo;

	// Save operation
	public Employee saveEmployee(Employee employee) {
		return repo.save(employee);
	}

	// Read operation
	public List<Employee> fetchEmployeeList() {
		return (List<Employee>) repo.findAll();
	}

	// Update operation
	public Employee updateEmployee(Employee employee, Integer Id) {
		Employee employeeDB = repo.findById(Id).get();
		if (Objects.nonNull(employee.getFirstName()) && !"".equalsIgnoreCase(employee.getLastName())) {
			employeeDB.setFirstName(employee.getFirstName());
			employeeDB.setLastName(employee.getLastName());
			employeeDB.setEmail(employee.getEmail());
		}
		return repo.save(employeeDB);
	}

	// Delete operation by ID

	public void deleteEmployeeById(Integer Id) {
		repo.deleteById(Id);

	}

	// Employee search by ID

	public Employee fetchEmployeeById(Integer Id) {

		return repo.findById(Id).get();
	}

	// Employee search by First Name

	public List<Employee> fetchEmployeeListByFirstName(String FirstName) {

		Employee emp = new Employee();
		emp.setFirstName(FirstName);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withMatcher("FirstName", ExampleMatcher.GenericPropertyMatchers.exact())
				.withIgnorePaths("id", "lastName", "email");
		Example<Employee> example = Example.of(emp, exampleMatcher);
		return repo.findAll(example);
	}

	// Employee Record Sorting by First Name

	public List<Employee> fetchEmployeeListSorted(String order) {
		if (order.equals("asc")) {
			return (List<Employee>) repo.findAll(Sort.by("firstName").ascending());
		}
		if (order.equals("desc")) {
			return (List<Employee>) repo.findAll(Sort.by("firstName").descending());
		}
		return null;
	}
}
