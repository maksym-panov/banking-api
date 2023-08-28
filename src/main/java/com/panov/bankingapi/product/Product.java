package com.panov.bankingapi.product;

import com.panov.bankingapi.account.Account;
import com.panov.bankingapi.product_type.ProductType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(
        name = "product_cd",
        nullable = false,
        length = 10
    )
    private String code;

    @Column(
        name = "`name`",
        nullable = false
    )
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_cd")
    private ProductType type;

    @Column(
        name = "date_offered",
        nullable = false
    )
    private LocalDate offerDate;

    @Column(name = "date_retired")
    private LocalDate retireDate;

    @OneToMany(mappedBy = "product")
    @OrderBy("openDate desc")
    private List<Account> accounts = new ArrayList<>();

    public void addAccount(Account account) {
        account.setProduct(this);
        accounts.add(account);
    }

    public void removeAccount(Account account) {
        account.setProduct(null);
        accounts.remove(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public LocalDate getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(LocalDate offerDate) {
        this.offerDate = offerDate;
    }

    public LocalDate getRetireDate() {
        return retireDate;
    }

    public void setRetireDate(LocalDate retireDate) {
        this.retireDate = retireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
