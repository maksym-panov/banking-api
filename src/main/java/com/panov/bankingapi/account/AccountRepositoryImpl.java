package com.panov.bankingapi.account;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AccountRepositoryImpl implements AccountRepository{
    @Override
    public Optional<Account> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Account> findByCustomer(UUID customerId) {
        return null;
    }

    @Override
    public List<Account> find(AccountStatus[] statuses, Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public Long save(Account account) {
        return null;
    }

    @Override
    public Long update(Account account) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
