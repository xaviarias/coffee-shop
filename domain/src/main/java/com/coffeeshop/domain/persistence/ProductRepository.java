package com.coffeeshop.domain.persistence;

import com.coffeeshop.domain.model.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Product findByName(String name);
}
