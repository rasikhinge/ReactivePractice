package com.practice.ReactiveFeatures;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ReactivePractice1 {

    @Test
    void flux_just() {
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6);

        StepVerifier.create(flux, 6)
                .expectNext(1, 2, 3, 4, 5, 6)
                .expectComplete()
                .verify();
    }

    @Test
    void flux_empty() {
        Flux<Integer> flux = Flux.empty();

        StepVerifier.create(flux)
                .expectComplete()
                .verify();
    }

    @Test
    void flux_fromList() {
        Flux<Integer> flux = Flux.fromIterable(Arrays.asList(1, 2, 3));

        StepVerifier.create(flux)
                .expectNext(1, 2, 3)
                .expectComplete()
                .verify();
    }

    @Test
    void flux_fromObjects() {
        Flux<User> flux = Flux.fromIterable(Arrays.asList(new User("abc"), new User("abcd")));

        StepVerifier.create(flux)
                .expectNextMatches(u -> u.getName().equals("abc"))
                .consumeNextWith(user -> assertThat(user.getName()).isEqualTo("abcd"))
                .expectComplete()
                .verify();
    }
}

@Data
@AllArgsConstructor
class User {
    private String name;
}