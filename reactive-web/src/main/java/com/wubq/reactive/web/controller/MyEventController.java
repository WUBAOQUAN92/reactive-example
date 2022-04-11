package com.wubq.reactive.web.controller;

import com.wubq.reactive.web.entity.MyEvent;
import com.wubq.reactive.web.repository.MyEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author wubq
 * @since 2022/3/25 11:22
 */
@RestController
@RequestMapping("/events")
public class MyEventController {
    @Autowired
    private MyEventRepository myEventRepository;

    /**
     * 指定传入的数据是application/x-ndjson，与getEvents方法的区别在于这个方法是consume这个数据流
     *
     * @param events
     * @return
     */
    @PostMapping(path = "", consumes = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<Void> loadEvents(@RequestBody Flux<MyEvent> events) {
        return myEventRepository.insert(events).then();
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<MyEvent> getEvents() {
        return myEventRepository.findBy();
    }
}

