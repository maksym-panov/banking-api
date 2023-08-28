package com.panov.bankingapi.customer.individual;

import com.panov.bankingapi.customer.Customer;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "individual")
@DiscriminatorValue("IN")
@PrimaryKeyJoinColumn(name = "cust_id")
public class Individual extends Customer {
    @Column(
        name = "tin",
        nullable = false,
        unique = true,
        length = 10
    )
    private String taxpayerId;

    @Column(
        name = "fname",
        nullable = false,
        length = 63
    )
    private String firstName;

    @Column(
        name = "lname",
        nullable = false,
        length = 63
    )
    private String lastName;

    @Column(
        name = "birth_day",
        nullable = false
    )
    private LocalDate birthday;

    public String getTaxpayerId() {
        return taxpayerId;
    }

    public void setTaxpayerId(String taxpayerId) {
        this.taxpayerId = taxpayerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Individual that = (Individual) o;
        return taxpayerId.equals(that.taxpayerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), taxpayerId);
    }

    @Override
    public String toString() {
        return "Individual{" +
                super.toString() +
                ", taxpayerId='" + taxpayerId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
