package com.panov.bankingapi.customer;

import com.panov.bankingapi.account.Account;
import com.panov.bankingapi.share.AddressInfo;
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

    @Embedded
    private AddressInfo addressInfo;

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
        int index = accounts.indexOf(account);

        account.setCustomer(null);
        accounts.get(index).setCustomer(null);
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
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
