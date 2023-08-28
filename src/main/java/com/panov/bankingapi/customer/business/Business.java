package com.panov.bankingapi.customer.business;

import com.panov.bankingapi.customer.Customer;
import com.panov.bankingapi.officer.Officer;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "business")
@DiscriminatorValue("BU")
@PrimaryKeyJoinColumn(name = "cust_id")
public class Business extends Customer {
    @Column(
        name = "usreou",
        nullable = false,
        unique = true,
        length = 8
    )
    private String usreou;
    @Column(
        name = "`name`",
        nullable = false
    )
    private String officialName;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_id")
    private BusinessState state;

    @Column(name = "incorp_date")
    private LocalDate incorpDate;

    @OneToMany(
        mappedBy = "business",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @OrderBy("startDate desc")
    private List<Officer> officers = new ArrayList<>();

    public void addOfficer(Officer officer) {
        officer.setBusiness(this);
        officers.add(officer);
    }

    public void removeOfficer(Officer officer) {
        officer.setBusiness(null);
        officers.remove(officer);
    }

    public List<Officer> getOfficers() {
        return officers;
    }

    public String getUsreou() {
        return usreou;
    }

    public void setUsreou(String usreou) {
        this.usreou = usreou;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public BusinessState getState() {
        return state;
    }

    public void setState(BusinessState state) {
        this.state = state;
    }

    public LocalDate getIncorpDate() {
        return incorpDate;
    }

    public void setIncorpDate(LocalDate incorpDate) {
        this.incorpDate = incorpDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Business business = (Business) o;
        return usreou.equals(business.usreou);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), usreou);
    }

    @Override
    public String toString() {
        return "Business{" +
                super.toString() +
                ", usreou='" + usreou + '\'' +
                ", officialName='" + officialName + '\'' +
                ", state=" + state +
                ", incorpDate=" + incorpDate +
                '}';
    }
}
