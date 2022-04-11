package com.wubq.reactive.web.repository;

import com.wubq.reactive.web.entity.MyEvent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

/**
 * @author wubq
 * @since 2022/3/25 11:22
 */
public interface MyEventRepository extends ReactiveMongoRepository<MyEvent, Long> { // 1
    @Tailable
        //@Tailable注解的作用类似于linux的tail命令，被注解的方法将发送无限流，需要注解在返回值为Flux这样的多个元素的Publisher的方法上；
    Flux<MyEvent> findBy();
}
