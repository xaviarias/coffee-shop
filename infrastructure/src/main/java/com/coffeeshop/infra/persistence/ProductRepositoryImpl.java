package com.coffeeshop.infra.persistence;

import com.coffeeshop.domain.model.Product;
import com.coffeeshop.domain.persistence.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll(Sort.by("name")).stream()
                .map(ProductEntity::toProduct)
                .collect(Collectors.toList());
    }

    @Override
    public Product findByName(String name) {
        return productJpaRepository.findByName(name).toProduct();
    }
}
