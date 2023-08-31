package com.panov.bankingapi.branch;

import com.panov.bankingapi.employee.Employee;
import com.panov.bankingapi.share.AddressInfo;
import com.panov.bankingapi.share.LocalDateDescendingComparator;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.SortNatural;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "branch")
public class Branch {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "BranchIdSequence"
    )
    @SequenceGenerator(
        name = "BranchIdSequence",
        sequenceName = "branch_id_seq",
        allocationSize = 1
    )
    @Column(
        name = "branch_id",
        nullable = false
    )
    private Long id;

    @NaturalId
    @Column(
        name = "`name`",
        nullable = false
    )
    private String name;

    @Embedded
    private AddressInfo addressInfo;

    @OneToMany(
        mappedBy = "branch",
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
        employee.setBranch(this);
        employeesRegistry.put(employee.getStartDate(), employee);
    }

    public void removeEmployee(Employee employee) {
        LocalDate date = employee.getStartDate();

        employee.setBranch(null);
        employeesRegistry.get(date).setBranch(null);
        employeesRegistry.remove(employee.getStartDate());
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

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branch branch = (Branch) o;
        return name.equals(branch.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
