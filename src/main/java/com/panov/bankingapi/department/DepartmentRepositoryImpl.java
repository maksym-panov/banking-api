package com.panov.bankingapi.department;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {
    @Override
    public Optional<Department> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Department> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Department> findAll() {
        return null;
    }

    @Override
    public Long save(Department department) {
        return null;
    }

    @Override
    public Long update(Department department) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
