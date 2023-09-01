package com.panov.bankingapi.officer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.panov.bankingapi.customer.business.Business;

/**
 * Represents data access layer for {@link Officer} domain entities.
 *
 * @author Maksym Panov
 * @version 1.0
 */
public interface OfficerRepository {
    /**
     * Consumes id and tries to find corresponding {@link Officer} entity
     * in the persistence layer.
     *
     * @param id identity of object to find.
     * @return {@link Optional} of found object or empty {@link Optional}
     * if there is no such object.
     */
    Optional<Officer> findById(Long id);

    /**
     * Retrieves all {@link Officer} objects by id of {@link Business}
     * entity they associated with.
     *
     * @param businessId identity of {@link Business} entity.
     * @return list of found entities.
     */
    List<Officer> findByBusinessId(UUID businessId);

    /**
     * Persists provided {@link Officer} entity in the persistence layer.
     *
     * @param officer entity to persist.
     * @return identity of persisted entity.
     */
    Long save(Officer officer);

    /**
     * Changes data of existing entity in the persistence layer.
     *
     * @param officer object that contains new data for existing entity.
     * @throws IllegalArgumentException if id of provided object is not positive or null.
     * @return id of updated entity.
     */
    Long update(Officer officer);

    /**
     * Permanently removes entity with provided id from the persistence layer.
     *
     * @param id identity of entity that should be removed.
     */
    void delete(Long id);
}
