package com.panov.bankingapi.customer.business;

import com.panov.bankingapi.customer.CustomerRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Represents data access layer for {@link Business} domain entities.
 *
 * @author Maksym Panov
 * @version 1.0
 */
public interface BusinessRepository extends CustomerRepository<Business> {
    /**
     * Tries to find {@link Business} entity with provided Unified State
     * Register of Enterprises and Organizations of Ukraine (USREOU)
     * in the persistence layer.
     *
     * @param usreou USREOU of sought entity.
     * @return found entity, wrapped in {@link Optional} or an empty
     * {@link Optional} if there is no such entity.
     */
    Optional<Business> findByUsreou(String usreou);

    /**
     * Tries to find {@link Business} entity with provided official name
     * in the persistence layer.
     *
     * @param officialName official name of sought entity.
     * @return found entity, wrapped in {@link Optional} or an empty
     * {@link Optional} if there is no such entity.
     */
    Optional<Business> getByOfficialName(String officialName);

    /**
     * Persists provided {@link Business} object in the persistence layer.
     *
     * @param business object to persist.
     * @throws IllegalArgumentException if one of required properties of
     * provided object is null or invalid.
     * @return id of persisted entity.
     */
    UUID save(Business business);

    /**
     * Changes all properties of existing persistent {@link Business} entity
     * using data from provided object.
     *
     * @param business object with new data and id of entity to change.
     * @throws IllegalArgumentException if one of required properties of
     * provided object is null or invalid.
     * @return id of updated entity.
     */
    UUID updateAll(Business business);

    /**
     * In corresponding persistent entity changes only properties that
     * are present in provided {@link Business} object.
     *
     * @param business object with new data and id of entity to change.
     * @throws IllegalArgumentException if id property of provided object
     * is null.
     * @return id of updated entity.
     */
    UUID updateNotNull(Business business);

    /**
     * Permanently removes {@link Business} entity with provided id
     * from the persistence layer.
     *
     * @param id identity of entity to remove.
     */
    void delete(UUID id);
}
