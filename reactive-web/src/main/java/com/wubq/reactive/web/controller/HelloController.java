package com.wubq.reactive.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author wubq
 * @since 2022/4/2 10:07
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public Mono<String> hello() {//返回类型为Mono<String>
        return Mono.just("Welcome to reactive world ~");//使用Mono.just生成响应式数据
    }

    @GetMapping("/hello/{latency}")
    public Mono<String> helloLatency(@PathVariable int latency) {
        return Mono.just("Welcome to reactive world ~").delayElement(Duration.ofMillis(latency)); // 1
    }
}
