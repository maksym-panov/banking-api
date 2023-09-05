package com.panov.bankingapi.product;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public Optional<Product> findByCode(String code) {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public boolean productWithNameExists(String name) {
        return false;
    }

    @Override
    public String save(Product product) {
        return null;
    }

    @Override
    public String update(Product product) {
        return null;
    }

    @Override
    public void delete(String code) {

    }
}
