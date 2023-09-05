package com.panov.bankingapi.officer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OfficerRepositoryImpl implements OfficerRepository {
    @Override
    public Optional<Officer> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Officer> findByBusinessId(UUID businessId) {
        return null;
    }

    @Override
    public Long save(Officer officer) {
        return null;
    }

    @Override
    public Long update(Officer officer) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
