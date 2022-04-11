package com.wubq.reactive.web.config;

import com.wubq.reactive.web.entity.MyEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;

/**
 * @author wubq
 * @since 2022/3/25 14:05
 */
@Configuration
public class Config {
    @Bean
    public CommandLineRunner initData(ReactiveMongoOperations mongo) {
        return (String... args) -> {
            mongo.dropCollection(MyEvent.class).block();
            mongo.createCollection(MyEvent.class, CollectionOptions.empty().maxDocuments(200).size(100000).capped())
                .block();
        };
    }
}
