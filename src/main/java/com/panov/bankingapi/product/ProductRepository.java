package com.panov.bankingapi.product;

import java.util.List;
import java.util.Optional;

/**
 * Represents data access layer for {@link Product} domain entities.
 *
 * @author Maksym Panov
 * @version 1.0
 */
public interface ProductRepository {
     /**
      * Tries to find {@link Product} entity in the persistence so that it
      * has the identity that is equal to provided value.
      *
      * @param code identity of sought persistent entity.
      * @return found object, wrapped in {@link Optional} or empty {@link Optional} otherwise.
      */
     Optional<Product> findByCode(String code);

     /**
      * Finds all existing {@link Product} instances in the persistence layer.
      *
      * @return list of all existing {@link Product}s.
      */
     List<Product> findAll();

     /**
      * Checks if there is already a persistent {@link Product} entity
      * with provided name.
      *
      * @param name product name to check.
      * @return {@code true} if product with such name already exists,
      * {@code false} - otherwise.
      */
     boolean productWithNameExists(String name);

     /**
      * Persists new {@link Product} object in the persistence layer.
      *
      * @param product object to persist.
      * @throws IllegalArgumentException if one of required fields of
      * provided object is null.
      * @return code of saved entity.
      */
     String save(Product product);

     /**
      * Changes data of provided object in the persistence.
      *
      * @param product object with new data and id of entity to change.
      * @throws IllegalArgumentException if identity field of provided
      * object if null.
      * @return code of updated entity.
      */
     String update(Product product);

     /**
      * Permanently removes entity with provided code from the persistence.
      *
      * @param code identity of entity that should be removed.
      */
      void delete(String code);
}
