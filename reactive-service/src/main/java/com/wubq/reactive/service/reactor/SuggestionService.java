package com.wubq.reactive.service.reactor;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author wubq
 * @since 2022/3/20 23:49
 */
@Service
public class SuggestionService {
    void getSuggestions(Callback<List<Favorite>> callback) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(List.of(new Favorite("6"), new Favorite("7"), new Favorite("8"), new Favorite("9"),
                    new Favorite("10")));
            }
        });
        thread.start();
    }

    Flux<Favorite> getSuggestions() {
        return Flux.just(new Favorite("6"), new Favorite("7"), new Favorite("8"), new Favorite("9"),
            new Favorite("10"));
    }
}
