package com.wubq.reactive.web.controller;

import com.wubq.reactive.web.entity.User;
import com.wubq.reactive.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author wubq
 * @since 2022/3/23 16:32
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public Mono<User> save(@RequestBody User user) {
        return this.userService.save(user);
    }

    @DeleteMapping("/{username}")
    public Mono<Long> deleteByUsername(@PathVariable String username) {
        return this.userService.deleteByUsername(username);
    }

    @GetMapping("/{username}")
    public Mono<User> findByUsername(@PathVariable String username) {
        return this.userService.findByUsername(username);
    }

    @GetMapping("")
    public Flux<User> findAll() {
        System.out.println(Thread.currentThread().getName());
        return this.userService.findAll().log();
    }

    @GetMapping(value = "/findAllStream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<User> findAllStream() {
        return this.userService.findAll().delayElements(Duration.ofSeconds(2));
    }
}
