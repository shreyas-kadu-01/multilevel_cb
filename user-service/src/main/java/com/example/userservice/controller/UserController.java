package com.example.userservice.controller;

import com.example.userservice.dtos.Product;
import com.example.userservice.service.CatalogService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

@RestController
@RequestMapping("/user")
public class UserController {

    CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
            .failureRateThreshold(50)
            .waitDurationInOpenState(Duration.ofMillis(1000))
            .permittedNumberOfCallsInHalfOpenState(3)
            .slidingWindowSize(10)
            .enableAutomaticTransitionFromOpenToHalfOpen()
            .minimumNumberOfCalls(5)
            .recordExceptions(IOException.class, TimeoutException.class)
            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
            .build();
    CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
    CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("test23");

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/getAllProducts")
    public List<Product> getAll() {
        Supplier<List<Product>> decoratedSupplier = CircuitBreaker.decorateSupplier(circuitBreaker, catalogService::getAllOnlineMethod);
        return Try.ofSupplier(decoratedSupplier).recover(throwable -> catalogService.getAllFallbackMethod()).get();
    }

}
