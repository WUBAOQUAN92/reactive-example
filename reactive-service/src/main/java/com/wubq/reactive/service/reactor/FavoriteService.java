package com.wubq.reactive.service.reactor;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author wubq
 * @since 2022/3/21 0:11
 */
@Service
public class FavoriteService {
    void getDetails(String favId, Callback<Favorite> callback) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(new Favorite(favId));
            }
        });
        thread.start();
    }

    Mono<Favorite> getDetails(String favId) {
        return Mono.just(new Favorite(favId));
    }
}
