package com.example.catalogservice.controller;

import com.example.catalogservice.dtos.Product;
import com.example.catalogservice.dtos.ProductList;
import com.example.catalogservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("addAll")
    public List<Product> addAll() {
        return productService.addAll();
    }

    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        return productService.add(product);
    }

    @GetMapping("/get")
    public Product getProduct(@RequestParam(value = "productId") int id) {
        return productService.getById(id);
    }

    @GetMapping("/getAll")
    public ProductList getAllProduct() {
        return productService.getAll();
    }
}
