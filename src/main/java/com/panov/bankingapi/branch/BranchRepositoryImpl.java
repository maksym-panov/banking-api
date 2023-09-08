package com.panov.bankingapi.branch;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.SortedSet;

@Repository
public class BranchRepositoryImpl implements BranchRepository {
    @Override
    public Optional<Branch> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Branch> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public SortedSet<Branch> findAll() {
        return null;
    }

    @Override
    public Long save(Branch branch) {
        return null;
    }

    @Override
    public Long update(Branch branch) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
