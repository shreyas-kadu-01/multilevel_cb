package com.example.userservice.service;

import com.example.userservice.dtos.Product;
import com.example.userservice.dtos.ProductList;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

@Service
public class CatalogService {

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
    CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("test3");

    private static final String CATALOG_SERVICE = "http://localhost:8083/product";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Catalog2Service catalog2Service;

    public List<Product> getAllOnlineMethod() {
        ProductList productList = restTemplate.getForObject(CATALOG_SERVICE + "/getAll", ProductList.class);
        return productList.getProductList();
    }

    public List<Product> getAllFallbackMethod() {
        Supplier<List<Product>> decoratedSupplier = CircuitBreaker.decorateSupplier(circuitBreaker, catalog2Service::getAllOnlineMethod);
        return Try.ofSupplier(decoratedSupplier).recover(throwable -> catalog2Service.getAllFallbackMethod()).get();
    }
}
