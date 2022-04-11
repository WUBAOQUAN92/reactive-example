package com.wubq.reactive.web.repository;

import com.wubq.reactive.web.entity.Account;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface AccountRepository extends R2dbcRepository<Account, String> {

    Flux<Account> findByCustomerId(String customerId);

}
