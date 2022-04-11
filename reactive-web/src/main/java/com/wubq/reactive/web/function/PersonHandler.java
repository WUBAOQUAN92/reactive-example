package com.wubq.reactive.web.function;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author wubq
 * @since 2022/3/23 14:14
 */
public class PersonHandler {

    public Mono<ServerResponse> listPeople(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(Flux.just(new Person(1, "wubq"), new Person(2, "wubq2")), Person.class);
    }

    public Mono<ServerResponse> createPerson(ServerRequest request) {
        Mono<Person> personMono = request.bodyToMono(Person.class);
        personMono.doOnEach(System.out::println);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(personMono, Person.class);
    }

    public Mono<ServerResponse> getPerson(ServerRequest request) {
        String id = request.pathVariable("id");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(new Person(Integer.valueOf(id), "wubq" + id)), Person.class);
    }
}
