package com.java.spring.cassandra.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.spring.cassandra.api.model.Employee;
import com.java.spring.cassandra.api.repository.EmployeeRepository;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeRepository repository;
	
	@GetMapping(value = "/healthcheck", produces = "application/json;charset=utf-8")
	public String getHealthCheck() {
		return "{ \"isWorking\" : true }";
	}
	
	@GetMapping("/employees")
	public List<Employee> getEmployees() {
		Iterable<Employee> result = repository.findAll();
		List<Employee> employeeList = new ArrayList<Employee>();
		result.forEach(employeeList::add);
		return employeeList;
	}
	
	@GetMapping("/employee/{id}")
	 public Optional<Employee> getEmployee(@PathVariable String id){
	  Optional<Employee> emp = repository.findById(id);
	  return emp;
	 }
	
	@PutMapping("/employee/{id}")
    public Optional<Employee> updateEmployee(@RequestBody Employee newEmployee,
    		@PathVariable String id) {
    	Optional<Employee> optionalEmp = repository.findById(id);
    	if (optionalEmp.isPresent()) {
    		Employee emp = optionalEmp.get();
    		emp.setFirstName(newEmployee.getFirstName());
    		emp.setLastName(newEmployee.getLastName());
    		emp.setEmail(newEmployee.getEmail());
    		repository.save(emp);
    	}
		return optionalEmp;
    }
	
	@DeleteMapping(value = "/employee/{id}", produces = "application/json; charset=utf-8")
	public String deleteEmployee(@PathVariable String id) {
		Boolean result = repository.existsById(id);
		repository.deleteById(id);
		return "{ \"success\" : "+ (result ? "true" : "false") +" }";
	}
	
}
