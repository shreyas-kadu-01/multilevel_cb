package com.example.catalogservice2.dtos;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class ProductList {
    public List<Product> productList;

    public ProductList() {
        productList = new ArrayList<>();
    }
}
