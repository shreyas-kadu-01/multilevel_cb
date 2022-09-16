package com.example.catalogservice.repository;

import com.example.catalogservice.dtos.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
