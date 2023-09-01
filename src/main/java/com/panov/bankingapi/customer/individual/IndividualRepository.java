package com.panov.bankingapi.customer.individual;

import com.panov.bankingapi.customer.CustomerRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Represents data access layer for {@link Individual} domain entities.
 *
 * @author Maksym Panov
 * @version 1.0
 */
public interface IndividualRepository extends CustomerRepository<Individual> {
    /**
     * Tries to find {@link Individual} entity with provided Taxpayer Identification
     * Number (TIN) in the persistence layer.
     *
      * @param tin taxpayer id of sought entity.
     * @return found entity, wrapped in {@link Optional} or an empty
     * {@link Optional} if there is no such entity.
     */
    Optional<Individual> findByTaxpayerId(String tin);

    /**
     * Persists provided {@link Individual} object in the persistence layer.
     *
     * @param individual object to persist.
     * @throws IllegalArgumentException if one of required properties of
     * provided object is null or invalid.
     * @return id of persisted entity.
     */
    UUID save(Individual individual);

    /**
     * Changes all properties of existing persistent {@link Individual} entity
     * using data from provided object.
     *
     * @param individual object with new data and id of entity to change.
     * @throws IllegalArgumentException if one of required properties of
     * provided object is null or invalid.
     * @return id of updated entity.
     */
    UUID updateAll(Individual individual);

    /**
     * In corresponding persistent entity changes only properties that
     * are present in provided {@link Individual} object.
     *
     * @param individual object with new data and id of entity to change.
     * @throws IllegalArgumentException if id property of provided object
     * is null.
     * @return id of updated entity.
     */
    UUID updateNotNull(Individual individual);

    /**
     * Permanently removes {@link Individual} entity with provided id
     * from the persistence layer.
     *
     * @param id identity of entity to remove.
     */
    void delete(UUID id);
}
