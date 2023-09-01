package com.panov.bankingapi.department;

import java.util.List;
import java.util.Optional;

/**
 * Represents data access layer for {@link Department} domain entities.
 *
 * @author Maksym Panov
 * @version 1.0
 */
public interface DepartmentRepository {
    /**
     * Tries to find {@link Department} entity that has provided id
     * in the persistence layer.
     *
     * @param id identity of sought {@link Department} entity.
     * @return found {@link Department} wrapped in {@link Optional} or
     * empty {@link Optional} if there is no such entity.
     */
    Optional<Department> findById(Long id);

    /**
     * Tries to find {@link Department} entity that has provided name
     * in the persistence layer.
     *
     * @param name name property of sought {@link Department} entity.
     * @return found {@link Department} wrapped in {@link Optional} or
     * empty {@link Optional} if there is not such entity.
     */
    Optional<Department> findByName(String name);

    /**
     * Retrieves all existing departments from the persistence layer.
     *
     * @return list of found {@link Department}s
     */
    List<Department> findAll();

    /**
     * Persists provided {@link Department} object in the persistence layer.
     *
     * @param department object to persist.
     * @throws IllegalArgumentException if one of required fields or provided
     * object is null or invalid.
     * @return id of persisted entity.
     */
    Long save(Department department);

    /**
     * Changes data of specific persistent {@link Department} entity using
     * data from provided object.
     *
     * @param department object with new data and id of entity to change.
     * @throws IllegalArgumentException if one of required properties in
     * provided object is null or invalid.
     * @return id of changed entity.
     */
    Long update(Department department);

    /**
     * Permanently removes {@link Department} entity with provided id from
     * the persistence layer.
     *
     * @param id identity of entity to remove.
     */
    void delete(Long id);
}
