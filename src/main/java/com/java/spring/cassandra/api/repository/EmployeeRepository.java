package com.java.spring.cassandra.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.java.spring.cassandra.api.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, String> {

}
