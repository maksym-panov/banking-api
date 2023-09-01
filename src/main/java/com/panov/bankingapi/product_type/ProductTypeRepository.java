package com.panov.bankingapi.product_type;

import java.util.List;
import java.util.Optional;

/**
 * Represents data access layer for {@link ProductType} domain entities.
 *
 * @author Maksym Panov
 * @version 1.0
 */
public interface ProductTypeRepository {
    /**
     * Consumes identity property of {@link ProductType}
     * and returns {@link Optional}, which contains found instance or null - if
     * there is no {@link ProductType} with provided code in the persistence.
     *
     * @param code identity property of the sought {@link ProductType}.
     * @return null safe object, retrieved from persistence.
     */
    Optional<ProductType> findByCode(String code);

    /**
     * Retrieves all the {@link ProductType} instances, that are present in
     * the persistence layer.
     *
     * @return list of found {@link ProductType} objects.
     */
    List<ProductType> findAll();

    /**
     * Checks if an entity with provided name is already present in the persistence.
     *
     * @param name to check.
     * @return {@code true} if there is already product type with provided name,
     * {@code false} - otherwise.
     */
    boolean productTypeWithNameExists(String name);

    /**
     * Saves provided instance of {@link ProductType} in the persistence layer.
     *
     * @param type object to persist.
     * @throws IllegalArgumentException if code or name of provided object is null.
     * @return code of saved entity.
     */
    String save(ProductType type);

    /**
     * Changes existing persistent entity with data from provided {@link ProductType} instance.
     *
     * @param type object with new data and id of entity to change.
     * @throws IllegalArgumentException if code of provided object is null.
     * @return code of updated entity.
     */
    String update(ProductType type);

    /**
     * Removes entity with provided code from the persistence.
     *
     * @param code code of entity, which should be removed.
     */
    void delete(String code);
}
