package com.panov.bankingapi.product_type;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductTypeRepositoryImpl implements ProductTypeRepository{
    @Override
    public Optional<ProductType> findByCode(String code) {
        return Optional.empty();
    }

    @Override
    public List<ProductType> findAll() {
        return null;
    }

    @Override
    public boolean productTypeWithNameExists(String name) {
        return false;
    }

    @Override
    public String save(ProductType type) {
        return null;
    }

    @Override
    public String update(ProductType type) {
        return null;
    }

    @Override
    public void delete(String code) {

    }
}
