package com.panov.bankingapi.transaction;

import java.util.List;
import java.util.Optional;

/**
 * Represents data access layer for {@link Transaction} domain entities.
 *
 * @author Maksym Panov
 * @version 1.0
 */
public interface TransactionRepository {
    /**
     * Tries to find a {@link Transaction} entity with provided id
     * in the persistence layer.
     *
     * @param id identity of sought entity.
     * @return found entity object wrapped in {@link Optional} or
     * empty {@link Optional} if there is no such entity.
     */
    Optional<Transaction> findById(Long id);

    /**
     * Retrieves all {@link Transaction} entity objects that are
     * associated with specific account with provided id.
     * Should always use descending ordering by transaction time.
     *
     * @param accountId identity of account, that is associated
     * with sought transactions.
     * @param page number of page to retrieve (defaults to 1 if
     * provided argument is null or illegal).
     * @param pageSize number of {@link Transaction} entities on one
     * pagination page (defaults to 20 if provided argument is null
     * or illegal).
     * @return list of found transactions.
     */
    List<Transaction> findByAccount(Long accountId, Integer page, Integer pageSize);

    /**
     * Persists new {@link Transaction} object in the persistence layer.
     *
     * @param transaction entity to persist.
     * @throws IllegalArgumentException if one of required fields of
     * provided object is null or invalid.
     * @return id of new entity.
     */
    Long save(Transaction transaction);
}
