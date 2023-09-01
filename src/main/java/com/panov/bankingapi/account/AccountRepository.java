package com.panov.bankingapi.account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.panov.bankingapi.customer.Customer;

/**
 * Represents data access layer for {@link Account} domain entities.
 *
 * @author Maksym Panov
 * @version 1.0
 */
public interface AccountRepository {
    /**
     * Tries to find {@link Account} entity with provided id in the
     * persistence layer.
     *
     * @param id identity of sought entity.
     * @return found entity, wrapped in {@link Optional} or an empty
     * {@link Optional} if there is no such entity.
     */
    Optional<Account> findById(Long id);

    /**
     * Retrieves all {@link Account} entities that are associated with
     * {@link Customer} with provided id.
     *
     * @param customerId identity of {@link Customer} entity, that is
     * associated with sought {@link Account}s.
     * @return list of found entities.
     */
    List<Account> findByCustomer(UUID customerId);

    /**
     * Retrieves {@link Account} entities from the persistence layer.
     *
     * @param statuses array of allowed statuses of sought {@link Account}
     * entities.
     * @param page number of entities page to retrieve.
     * @param pageSize number of entities per pagination page.
     * @return list of found entities.
     */
    List<Account> find(AccountStatus[] statuses, Integer page, Integer pageSize);

    /**
     * Persists provided object in the persistence layer.
     *
      * @param account object to persist.
     * @throws IllegalArgumentException if one of required properties
     * of provided object is null or invalid.
     * @return id of persisted entity.
     */
    Long save(Account account);

    /**
     * Changes existing {@link Account} persistent entity with data
     * from provided object.
     *
     * @param account object with new data and id of entity to change.
     * @throws IllegalArgumentException if one of required properties
     * of provided object is null or invalid.
     * @return id of updated entity.
     */
    Long update(Account account);

    /**
     * Permanently removes {@link Account} entity from the persistence layer.
     *
     * @param id identity of entity to remove.
     */
    void delete(Long id);
}
