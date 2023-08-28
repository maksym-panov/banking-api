package com.panov.bankingapi.customer;

import com.panov.bankingapi.account.Account;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import org.hibernate.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "customer")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(
    name = "cust_type_descriptor",
    discriminatorType = DiscriminatorType.STRING,
    length = 2
)
public class Customer {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(
        name = "cust_id",
        nullable = false
    )
    private UUID id;

    @Column(
        name = "region",
        nullable = false,
        length = 63
    )
    private String region;

    @Column(
        name = "city",
        nullable = false,
        length = 63
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

    @OneToMany(
        mappedBy = "customer",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @OrderBy("openDate desc")
    private List<Account> accounts = new ArrayList<>();

    public void addAccount(Account account) {
        account.setCustomer(this);
        accounts.add(account);
    }

    public void removeAccount(Account account) {
        account.setCustomer(null);
        accounts.remove(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
