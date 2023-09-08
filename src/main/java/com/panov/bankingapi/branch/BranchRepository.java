package com.panov.bankingapi.branch;

import java.util.Optional;
import java.util.SortedSet;

/**
 * Represents data access layer for {@link Branch} domain entities.
 *
 * @author Maksym Panov
 * @version 1.0
 */
public interface BranchRepository {
    /**
     * Tries to find {@link Branch} entity with provided id
     * in the persistence layer.
     *
     * @param id identity of sought entity.
     * @return found entity, wrapped in {@link Optional} or an empty
     * {@link Optional} if there is no such entity.
     */
    Optional<Branch> findById(Long id);

    /**
     * Tries to find {@link Branch} entity with provided name
     * in the persistence layer.
     *
     * @param name name of sought entity.
     * @return found entity, wrapped in {@link Optional} or an empty
     * {@link Optional} if there is no such entity.
     */
    Optional<Branch> findByName(String name);

    /**
     * Retrieves all existing {@link Branch} entities from
     * the persistence layer. By default, retrieved entities
     * are sorted by their names in ascending order.
     *
      * @return sorted set of found {@link Branch}es.
     */
    SortedSet<Branch> findAll();

    /**
     * Persists provided {@link Branch} object to the persistence
     * layer.
     *
     * @param branch object to persist.
     * @throws IllegalArgumentException if one of required properties
     * of provided object is null or empty.
     * @return id of persisted entity.
     */
    Long save(Branch branch);

    /**
     * Changes data of existing {@link Branch} persisted entity,
     * using data from provided object.
     *
     * @param branch object with new data and id of entity to change.
     * @throws IllegalArgumentException if one of required properties
     * of provided object is null or empty.
     * @return id of updated entity.
     */
    Long update(Branch branch);

    /**
     * Permanently removes {@link Branch} entity from the persistence layer.
     *
     * @param id identity of entity to remove.
     */
    void delete(Long id);
}
