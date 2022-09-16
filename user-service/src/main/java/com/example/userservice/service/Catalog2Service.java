package com.example.userservice.service;

import com.example.userservice.dtos.Product;
import com.example.userservice.dtos.ProductList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class Catalog2Service {

    private static final String CATALOG_SERVICE_2 = "http://localhost:8081/product";

    @Autowired
    private RestTemplate restTemplate;

    public List<Product> getAllFallbackMethod() {
        ProductList productList = restTemplate.getForObject(CATALOG_SERVICE_2 + "/getAll", ProductList.class);
        return productList.getProductList();
    }

    public List<Product> getAllOnlineMethod() {
        List<Product> idList = new ArrayList<>();
        idList.add(new Product("Book", "novel", "270"));
        idList.add(new Product("Shirt", "white", "899"));
        idList.add(new Product("Mobile", "smartphone", "24599"));
        idList.add(new Product("Fan", "tableFan", "2400"));
        idList.add(new Product("Pen", "ball pen", "20"));
        return idList;
    }
}
