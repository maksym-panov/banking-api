package com.panov.bankingapi.branch;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

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

    @Column(
        name = "region",
        nullable = false,
        length = 63
    )
    private String region;

    @Column(
        name = "city",
        nullable = false
    )
    private String city;

    @Column(
        name = "address",
        nullable = false
    )
    private String address;

    @Column(
        name = "postal_code",
        nullable = false,
        length = 5
    )
    private String postalCode;

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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
