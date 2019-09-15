package com.practice.ReactiveFeatures.controller;

import com.practice.ReactiveFeatures.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@RestController
@Slf4j
public class FrontEndController {
    private WebClient webClient = WebClient.create("http://localhost:5000?delay=1");
    private static RestTemplate restTemplate = new RestTemplate();

    static {
        String baseUrl = "http://localhost:5000?delay=5";
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));
    }

    @GetMapping("/rest/employees")
    public List<Employee> getEmployees() {
        long start = System.currentTimeMillis();
        List<Employee> employees = Stream.of(
                "Rasik", "Rhoit", "Sunil", "Rutvij", "Alok", "Jatin", "Vishal", "Vedant")
                .map(name ->
                        restTemplate.getForObject("/backend/employees/{name}", Employee.class, name)
                ).collect(toList());
        log.info("Time elapsed - {} ", (System.currentTimeMillis() - start));
        return employees;
    }

    @GetMapping("/rest/reactive/employees")
    public Flux<Employee> getEmployeesReactive() {
        long start = System.currentTimeMillis();
        List<Mono<Employee>> collect = Stream.of(
                "Rasik", "Rhoit", "Sunil", "Rutvij", "Alok", "Jatin", "Vishal", "Vedant")
                .map(name ->
                        webClient.get().uri("/backend/reactive/employees/{name}", name)
                                .retrieve().bodyToMono(Employee.class)

                ).collect(toList());
        Flux<Mono<Employee>> monoFlux = Flux.fromIterable(collect);
        Flux<Employee> employeeFlux = monoFlux.flatMap(e -> e.flux());

        log.info("Time elapsed - {}", (System.currentTimeMillis() - start));
        return employeeFlux.doOnRequest(i -> System.out.println("Requesting - " + i)).limitRate(5);
    }

    @GetMapping("/rest/reactive/all")
    public Flux<Employee> getAllEmployeesReactive() {
        long start = System.currentTimeMillis();
        Flux<Employee> employeeFlux = Flux.empty();
        log.info("Rasik Frontend called..........Time elapsed - {}", (System.currentTimeMillis() - start));
//        for (int i = 0; i < 4; i++) {
        return  webClient.get().uri("/backend/reactive/employees/stream")
                .retrieve().bodyToFlux(Employee.class)
                .take(2);
        // .subscribe(System.out::println);

//        employeeFlux
//                .subscribe(System.out::println);
//        }
    /*.subscribe(new BaseSubscriber<Employee>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        log.info("Rasik On Subscribe ");
                        request(1);
                        super.hookOnSubscribe(subscription);
                    }

                    @Override
                    protected void hookOnNext(Employee value) {
                        log.info("Rasik OnNext");
                        request(20);
                        super.hookOnNext(value);
                    }

                    @Override
                    protected void hookOnComplete() {
                        super.hookOnComplete();
                    }

                    @Override
                    protected void hookOnError(Throwable throwable) {
                        super.hookOnError(throwable);
                    }
                });*/
//        return Flux.empty();
    }


    @GetMapping("/rest/reactive/all-emp")
    public Flux<Employee> getAllEmployeesFluxReactive() {
        long start = System.currentTimeMillis();
        List<String> names = Arrays.asList("Rasik", "Rohit", "Sunil", "Rutvij", "Alok", "Jatin", "Vishal", "Vedant");
        Flux<Employee> employeeFlux = Flux.fromIterable(names).flatMap(name -> {
            return webClient.get().uri("/backend/reactive/employees/{name}", name)
                    .retrieve().bodyToMono(Employee.class);
        }).limitRate(2);
        log.info("Rasik Frontend called..........Time elapsed - {}", (System.currentTimeMillis() - start));
        employeeFlux
                .subscribe(new BaseSubscriber<Employee>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        log.info("Rasik On Subscribe ");
                        request(20);
                        super.hookOnSubscribe(subscription);
                    }

                    @Override
                    protected void hookOnNext(Employee value) {
                        log.info("Rasik OnNext");
                        request(20);
                        super.hookOnNext(value);
                    }

                    @Override
                    protected void hookOnComplete() {
                        super.hookOnComplete();
                    }

                    @Override
                    protected void hookOnError(Throwable throwable) {
                        super.hookOnError(throwable);
                    }
                });
        return Flux.empty();
    }


    @GetMapping("/rest/employees1")
    public List<Employee> getEmployeesReactive1() {
        long start = System.currentTimeMillis();
        List<Employee> employees = new ArrayList<>();
        List<String> names = Arrays.asList("Rasik", "Rhoit", "Sunil", "Rutvij", "Alok", "Jatin", "Vishal", "Vedant");
        for (String name : names) {
            Mono<Employee> employeeMono =
                    webClient.get().uri("/backend/reactive/employees/{name}", name)
                            .retrieve()
                            .bodyToMono(Employee.class);
            employeeMono.subscribe(System.out::println);

        }
        log.info("Time elapsed - {}", (System.currentTimeMillis() - start));
        return employees;
    }
}
