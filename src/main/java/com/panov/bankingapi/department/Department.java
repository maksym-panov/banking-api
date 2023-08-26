package com.panov.bankingapi.department;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

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
