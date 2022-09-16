package com.example.userservice.dtos;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductList {
    public List<Product> productList;

    public ProductList() {
        productList = new ArrayList<>();
    }
}
