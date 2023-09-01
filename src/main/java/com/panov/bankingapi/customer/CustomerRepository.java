package com.panov.bankingapi.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents common data access methods for all types of {@link Customer} entities.
 *
 * @author Maksym Panov
 * @version 1.0
 */
public interface CustomerRepository<T extends Customer> {
    /**
     * Tries to find {@link Customer} entity with specified id in
     * the persistence layer.
     *
     * @param id identity of sought entity.
     * @return found entity, wrapped in {@link Optional} or an empty
     * {@link Optional} if there is no such entity.
     */
    Optional<T> findById(UUID id);
    /**
     * Retrieves list of entities of {@link Customer} derivative types
     * found in the persistence layer.
     *
     * @param page number of entities page to retrieve.
     * @param pageSize number of {@link Customer} entities per pagination page.
     * @return list of found {@link Customer}s.
     */
    List<T> find(Integer page, Integer pageSize);
}
