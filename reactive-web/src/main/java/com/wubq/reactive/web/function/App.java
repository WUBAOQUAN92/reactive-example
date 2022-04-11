package com.wubq.reactive.web.function;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author wubq
 * @since 2022/3/23 14:15
 */
public class App {
    public static void main(String[] args) throws IOException {
        PersonHandler handler = new PersonHandler();
        RouterFunction<ServerResponse> route = route().GET("/person/{id}", accept(APPLICATION_JSON), handler::getPerson)
            .GET("/person", accept(APPLICATION_JSON), handler::listPeople).POST("/person", handler::createPerson)
            .build();

        HttpHandler httpHandler = RouterFunctions.toHttpHandler(route);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer.create().host("localhost").port(8888).handle(adapter).bind().block();
        System.in.read();
    }
}
