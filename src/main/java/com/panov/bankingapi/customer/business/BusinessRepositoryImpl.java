package com.panov.bankingapi.customer.business;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BusinessRepositoryImpl implements BusinessRepository{
    @Override
    public Optional<Business> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Business> find(Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public Optional<Business> findByUsreou(String usreou) {
        return Optional.empty();
    }

    @Override
    public Optional<Business> getByOfficialName(String officialName) {
        return Optional.empty();
    }

    @Override
    public UUID save(Business business) {
        return null;
    }

    @Override
    public UUID updateAll(Business business) {
        return null;
    }

    @Override
    public UUID updateNotNull(Business business) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
