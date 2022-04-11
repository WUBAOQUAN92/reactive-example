package com.wubq.reactive.service.reactor;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author wubq
 * @since 2022/3/20 23:40
 */
@Service
public class UserService {
    void getFavorites(Long userId, Callback<List<String>> callback) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(List.of("1", "2", "3", "4", "5"));
            }
        });
        thread.start();
    }

    Flux<String> getFavorites(Long userId) {
        return Flux.just("1", "2", "3", "4", "5");
    }
}
