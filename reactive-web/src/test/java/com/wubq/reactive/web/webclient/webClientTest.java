package com.wubq.reactive.web.webclient;

import com.wubq.reactive.web.entity.MyEvent;
import com.wubq.reactive.web.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

/**
 * @author wubq
 * @since 2022/3/25 10:38
 */
public class webClientTest {
    @Test
    public void webClientTest1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        WebClient webClient = WebClient.create("http://localhost:8080");
        Flux<User> userFlux = webClient.get().uri("/user").retrieve().bodyToFlux(User.class);
        userFlux.subscribe(e -> {
            System.out.println(e);
            countDownLatch.countDown();
        });
        countDownLatch.await();
    }

    @Test
    public void webClientTest2() throws InterruptedException {
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
        webClient.get().uri("/user/findAllStream").accept(MediaType.APPLICATION_NDJSON)
            .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(User.class)).doOnNext(System.out::println)
            .blockLast();
    }

    @Test
    public void webClientTest3() throws InterruptedException {
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
        webClient.get().uri("/times").accept(MediaType.TEXT_EVENT_STREAM).retrieve().bodyToFlux(String.class).log()
            .take(10).blockLast();
    }

    @Test
    public void webClientTest4() {
        Flux<MyEvent> myEventFlux =
            Flux.interval(Duration.ofSeconds(1)).map(l -> new MyEvent(System.currentTimeMillis(), "message-" + l)).log()
                .take(10);//声明速度为每秒一个MyEvent元素的数据流，不加take的话表示无限个元素的数据流；
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
        //声明请求体的数据格式为application/x-ndjson；
        webClient.post().uri("/events").contentType(MediaType.APPLICATION_NDJSON).body(myEventFlux, MyEvent.class)
            .retrieve().bodyToMono(Void.class)//body方法设置请求体的数据。
            .log().block();
    }

    @Test
    public void webClientTest5() {

        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
        webClient.get().uri("/events").accept(MediaType.APPLICATION_NDJSON).retrieve().bodyToFlux(MyEvent.class)
            .doOnNext(System.out::println).blockLast();
    }
}
