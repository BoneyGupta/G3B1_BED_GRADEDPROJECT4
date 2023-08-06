package com.greatlearning.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greatlearning.entity.Employee;
import com.greatlearning.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> findAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee findEmployeeById(int id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("No employee with id " + id));
	}

	@Override
	public String deleteEmployeeById(int id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee.isPresent()) {
			employeeRepository.deleteById(id);
			return "Deleted Employee id - " + id;
		} else {
			throw new NoSuchElementException("No employee with id " + id);
		}
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		boolean exist = employeeRepository.existsById(employee.getId());
		if (exist) {
			return employeeRepository.saveAndFlush(employee);
		} else {
			throw new RuntimeException("No employee with id " + employee.getId());
		}
	}

	@Override
	public List<Employee> searchEmployeeByFirstName(String firstName) {
		return employeeRepository.findByFirstName(firstName);
	}

	@Override
	public List<Employee> sortEmployeeByFirstName(String order) {
		Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		return employeeRepository.findAll(Sort.by(direction, "firstName"));
	}

}
