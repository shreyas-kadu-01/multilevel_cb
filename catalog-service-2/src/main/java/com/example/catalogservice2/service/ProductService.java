package com.example.catalogservice2.service;

import com.example.catalogservice2.dtos.Product;
import com.example.catalogservice2.dtos.ProductList;
import com.example.catalogservice2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product add(Product product) {
        return productRepository.save(product);
    }

    public Product getById(int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseGet(Product::new);
    }

    public List<Product> addAll() {
        List<Product> idList = new ArrayList<>();
        idList.add(add(new Product("Book", "novel", "270")));
        idList.add(add(new Product("Shirt", "white", "899")));
        idList.add(add(new Product("Mobile", "smartphone", "24599")));
        idList.add(add(new Product("Fan", "tableFan", "2400")));
        idList.add(add(new Product("Pen", "ball pen", "20")));
        return idList;
    }

    public ProductList getAll() {
        List<Product> list = productRepository.findAll();
        ProductList productList = new ProductList();
        productList.setProductList(list);
        return productList;
    }
}
