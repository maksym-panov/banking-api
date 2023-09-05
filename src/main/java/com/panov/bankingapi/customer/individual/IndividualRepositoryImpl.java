package com.panov.bankingapi.customer.individual;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class IndividualRepositoryImpl implements IndividualRepository {
    @Override
    public Optional<Individual> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Individual> find(Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public Optional<Individual> findByTaxpayerId(String tin) {
        return Optional.empty();
    }

    @Override
    public UUID save(Individual individual) {
        return null;
    }

    @Override
    public UUID updateAll(Individual individual) {
        return null;
    }

    @Override
    public UUID updateNotNull(Individual individual) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
