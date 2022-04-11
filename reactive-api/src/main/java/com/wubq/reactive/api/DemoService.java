package com.wubq.reactive.api;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author wubq
 * @since 2022/3/19 21:30
 */
public interface DemoService {
    Mono<String> requestMonoWithMonoArg(Mono<String> m1, Mono<String> m2);

    Flux<String> requestFluxWithFluxArg(Flux<String> f1, Flux<String> f2);
}
