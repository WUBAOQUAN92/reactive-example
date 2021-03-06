package com.wubq.mvc.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author wubq
 * @since 2022/4/8 12:41
 */
@RestController
public class HelloController {
    @GetMapping("/hello/{latency}")
    public String hello(@PathVariable long latency) {
        try {
            TimeUnit.MILLISECONDS.sleep(latency);   // 1
        } catch (InterruptedException e) {
            return "Error during thread sleep";
        }
        return "Welcome to reactive world ~";
    }
}
