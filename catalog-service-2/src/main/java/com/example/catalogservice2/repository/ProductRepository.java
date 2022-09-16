package com.example.catalogservice2.repository;

import com.example.catalogservice2.dtos.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
