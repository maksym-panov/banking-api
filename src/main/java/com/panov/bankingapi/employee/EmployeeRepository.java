package com.panov.bankingapi.employee;

import java.util.List;
import java.util.Optional;
import com.panov.bankingapi.branch.Branch;
import com.panov.bankingapi.department.Department;

/**
 * Represents data access layer for {@link Employee} domain entity.
 *
 * @author Maksym Panov
 * @version 1.0
 */
public interface EmployeeRepository {
    /**
     * Tries to find {@link Employee} persistent entity with provided
     * id value.
     *
     * @param id identity of sought entity.
     * @return found entity object, wrapped in {@link Optional}, or
     * an empty {@link Optional} if there is no such entity.
     */
    Optional<Employee> findById(Long id);

    /**
     * Retrieves a list of {@link Employee} entities which are associated
     * with {@link Branch} and {@link Department} that have provided ids.
     * Returned {@link Employee}s are by default ordered by lastname and then
     * by firstname in ascending order.
     *
     * @param branchId id of {@link Branch} entity which is associated with
     * sought {@link Employee}s. If this parameter is null or illegal, then
     * it should be ignored during entity search.
     * @param departmentId id of {@link Department} entity which is associated
     * with sought {@link Employee}. If this parameter is null or illegal, then
     * it should be ignored during entity search.
     * @param page number of page to retrieve (defaults to 1 if provided argument is
     * null or invalid).
     * @param pageSize number of {@link Employee} entities on one pagination page
     * (defaults to 20 if provided argument is null or invalid).
     * @return list of found entities.
     */
    List<Employee> find(
            Long branchId, Long departmentId,
            Integer page, Integer pageSize
    );

    /**
     * Persists provided {@link Employee} object in the persistence level.
     *
     * @param employee object to persist.
     * @throws IllegalArgumentException if one of required fields is null or invalid.
     * @return id of persisted object.
     */
    Long save(Employee employee);

    /**
     * Changes all properties of specific entity in the persistence level using
     * data from provided {@link Employee} object.
     *
     * @param employee object with new data and id of entity to change.
     * @throws IllegalArgumentException if one of required fields is null or invalid.
     * @return id of updated entity.
     */
    Long updateAll(Employee employee);

    /**
     * In corresponding persistent entity changes only properties that
     * are present in provided {@link Employee} object.
     *
     * @param employee object with new data and id of entity to change.
     * @return id of updated entity.
     */
    Long updateNotNull(Employee employee);

    /**
     * Permanently removes {@link Employee} entity with provided id from the
     * persistence layer.
     *
     * @param id identity of entity to remove.
     */
    void delete(Long id);
}
