package com.panov.bankingapi.department;

import com.panov.bankingapi.employee.Employee;
import com.panov.bankingapi.share.LocalDateDescendingComparator;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.SortNatural;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "DepartmentIdSequence"
    )
    @SequenceGenerator(
        name = "DepartmentIdSequence",
        sequenceName = "department_id_seq",
        allocationSize = 1
    )
    @Column(
        name = "dept_id",
        nullable = false
    )
    private Long id;

    @NaturalId
    @Column(
        name = "`name`",
        nullable = false
    )
    private String name;

    @OneToMany(
        mappedBy = "department",
        cascade = {
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.PERSIST
        }
    )
    @MapKey(name = "startDate")
    @SortNatural
    private Map<LocalDate, Employee> employeesRegistry =
            new TreeMap<>(new LocalDateDescendingComparator());

    public void addEmployee(Employee employee) {
        employee.setDepartment(this);
        employeesRegistry.put(employee.getStartDate(), employee);
    }

    public void removeEmployee(Employee employee) {
        LocalDate date = employee.getStartDate();

        employee.setDepartment(null);
        employeesRegistry.get(date).setDepartment(null);
        employeesRegistry.remove(date);
    }

    public Map<LocalDate, Employee> getEmployeesRegistry() {
        return employeesRegistry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
