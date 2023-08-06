package com.greatlearning.service;

import java.util.List;

import com.greatlearning.entity.Employee;

public interface EmployeeService {

	public Employee save(Employee employee);

	public List<Employee> findAllEmployees();

	public Employee findEmployeeById(int id);

	public String deleteEmployeeById(int id);

	public Employee updateEmployee(Employee employee);

	public List<Employee> searchEmployeeByFirstName(String firstName);

	public List<Employee> sortEmployeeByFirstName(String order);
}
