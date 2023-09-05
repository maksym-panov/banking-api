package com.panov.bankingapi.transaction;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
    @Override
    public Optional<Transaction> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Transaction> findByAccount(Long accountId, Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public Long save(Transaction transaction) {
        return null;
    }
}
