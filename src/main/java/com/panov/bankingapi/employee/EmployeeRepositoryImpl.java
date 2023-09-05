package com.panov.bankingapi.employee;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Employee> find(Long branchId, Long departmentId, Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public Long save(Employee employee) {
        return null;
    }

    @Override
    public Long updateAll(Employee employee) {
        return null;
    }

    @Override
    public Long updateNotNull(Employee employee) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
