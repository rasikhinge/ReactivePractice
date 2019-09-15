package com.practice.ReactiveFeatures.controller;

import com.practice.ReactiveFeatures.model.Employee;
import com.practice.ReactiveFeatures.model.RequestContext;
import com.practice.ReactiveFeatures.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class BackendController {

    private final EmployeeService service;

    public BackendController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/backend/reactive/employees")
    Flux<Employee> getEmployees(@RequestParam(name = "delay") int delay) throws InterruptedException {
        Thread.sleep(delay * 1000);
        RequestContext context = RequestContext.getInstance();
        log.info("Rasik Backend called......... /backend/reactive/employees ");
        return service.getEmployees().doOnRequest(i -> System.out.println("Rasik Backend Requested : " + i));
    }

    @GetMapping(value = "/backend/reactive/employees/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Employee> getEmployeesStream(@RequestParam(name = "delay") int delay) {
//        Thread.sleep(delay * 1000);
        RequestContext context = RequestContext.getInstance();
        log.info("Rasik Backend called......... /backend/reactive/employees ");
        return service.getEmployees().delayElements(Duration.ofSeconds(delay))
                .doOnRequest(i -> System.out.println("Rasik Backend Requested : " + i))
                ;
    }

    @GetMapping("/backend/employees")
    List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    @GetMapping("/backend/reactive/employees/{name}")
    Mono<Optional<Employee>> getEmployeeByName(@PathVariable(name = "name") String name,
                                               @RequestParam(name = "delay") int delay) throws InterruptedException {
        log.info("get Employee - {}", name);
        log.info("Rasik Backend called.........");
        Thread.sleep(delay * 1000);
        return service.getEmployeeByName("name");
    }

    @GetMapping("/backend/employees/{name}")
    Optional<Employee> getEmpByName(@PathVariable(name = "name") String name,
                                    @RequestParam(name = "delay") int delay) throws InterruptedException {
        Thread.sleep(delay * 1000);
        return service.getEmpByName(name);
    }

    @GetMapping("/backend/getInts")
    Flux<Integer> getInts() {
        RequestContext requestContext = RequestContext.getInstance();

        Flux<Integer> integerFlux = Flux.range(100, 102)
                .map(i -> {
                    if (i == 7) {
                        throw new RuntimeException("Invalid Option");
                    }
                    log.info("Request Id - {} : Mapping : {}", requestContext.getRequestId(), i);
                    return i;
                });

        return integerFlux;
    }
}
