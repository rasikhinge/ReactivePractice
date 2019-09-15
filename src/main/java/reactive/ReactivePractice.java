package reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactivePractice {
    public static void main(String[] args) {
        /*Flux.range(1, 10)
                .flatMap(i -> {
                    System.out.print("2 X " + i + " = ");
                    return Mono.just(2 * i);
                })
                .subscribe(System.out::println);

        Flux.generate(() -> 0,
                (state, sink) -> {
                    sink.next("3 X " + state + " = " + 3 * state);
                    if (state == 10) sink.complete();
                    return state + 1;
                }).subscribe(System.out::println);
*/
/*
        Flux<Integer> integerFlux = Flux.range(1, 10).map(i -> {
            System.out.println("Mapping: " + i);
            if (i == 7)
                throw new RuntimeException("Error");
            return i;
        });

        Flux<Integer> errorResumeFlux = Flux.range(0, 6);

        integerFlux
                .onErrorResume((e) -> errorResumeFlux)
                .flatMap(i -> {
                    System.out.println("subscribe :" + i);
                    return Mono.just(i);
                });*/

        Mono<Integer> mono = Mono.empty();
        Mono<Integer> switchIfEmpty = mono.switchIfEmpty(Mono.just(0));
        System.out.println("mono : "+mono.block());
        System.out.println("alternative : "+switchIfEmpty.block());
        Mono<Integer> defaultIfEmpty = mono.defaultIfEmpty(100);
        System.out.println("default If Empty : "+defaultIfEmpty.block());

    }

}
