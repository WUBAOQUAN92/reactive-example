package com.wubq.reactive.web.controller;

import com.wubq.reactive.web.entity.Account;
import com.wubq.reactive.web.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/account")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountRepository repository;
    @Autowired
    private ReactiveRedisTemplate reactiveRedisTemplate;

    @GetMapping("/customer/{customer}")
    public Flux<Account> findByCustomer(@PathVariable("customer") String customerId) {
        LOGGER.info("findByCustomer: customerId={}", customerId);
        return repository.findByCustomerId(customerId);
    }

    @GetMapping
    public Flux<Account> findAll() {
        LOGGER.info("findAll");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Account> findById(@PathVariable("id") String id) {
        LOGGER.info("findById: id={}", id);
        return repository.findById(id).log();
    }

    @GetMapping("/cache/{id}")
    public Mono<Account> findByIdWithCache(@PathVariable("id") String id) {
        ReactiveValueOperations<String, Account> reactiveValueOperations = reactiveRedisTemplate.opsForValue();
        String key = "account:" + id;
        Mono<Account> accountMono = reactiveValueOperations.get(key);
        return accountMono.switchIfEmpty(
            this.findById(id).flatMap(ele -> reactiveValueOperations.set(key, ele).thenReturn(ele)));
    }

    @PostMapping
    public Mono<Account> create(@RequestBody Account account) {
        LOGGER.info("create: {}", account);
        return Mono.justOrEmpty(account.getId()).flatMap(id -> reactiveRedisTemplate.delete("account:" + id))
            .then(repository.save(account));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return repository.deleteById(id).then(reactiveRedisTemplate.delete("account:" + id));
    }
}
