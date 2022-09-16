package com.example.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private int productId;
    private String productName;
    private String productDesc;
    private String productPrice;

    public Product(String productName, String productDesc, String productPrice) {
        this.productName = productName;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
    }
}
