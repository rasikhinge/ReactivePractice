package com.practice.ReactiveFeatures.service;

import com.practice.ReactiveFeatures.model.Employee;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static List<Employee> employees = new ArrayList<>();

    static {
        employees.add(new Employee("Rasik", 28));
        employees.add(new Employee("Rohit", 29));
        employees.add(new Employee("Rutvij", 28));
        employees.add(new Employee("Nikhil", 28));
        employees.add(new Employee("Vedant", 20));
        employees.add(new Employee("Sunil", 30));
        employees.add(new Employee("Alok", 32));
        employees.add(new Employee("Jatin", 34));
        employees.add(new Employee("Vishal", 28));
        employees.add(new Employee("Dinesh", 27));
    }

    @Override
    public Flux<Employee> getEmployees() {
        return Flux.fromIterable(employees);
    }

    @Override
    public Mono<Optional<Employee>> getEmployeeByName(String name) {
        return Mono.just(employees.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst());
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employees;
    }

    @Override
    public Optional<Employee> getEmpByName(String name) {
        return employees.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst();
    }
}
