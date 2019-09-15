package com.practice.ReactiveFeatures.service;

import com.practice.ReactiveFeatures.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Flux<Employee> getEmployees();

    List<Employee> getAllEmployees();

    Optional<Employee> getEmpByName(String name);

    Mono<Optional<Employee>> getEmployeeByName(String name);
}
