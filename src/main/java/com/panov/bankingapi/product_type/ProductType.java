package com.panov.bankingapi.product_type;

import com.panov.bankingapi.product.Product;
import com.panov.bankingapi.product.ProductNameComparator;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.SortNatural;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "product_type")
public class ProductType {
    @Id
    @Column(
        name = "product_type_cd",
        nullable = false,
        length = 10
    )
    private String productTypeCode;

    @NaturalId
    @Column(
        name = "`name`",
        nullable = false
    )
    private String name;

    @OneToMany(mappedBy = "type")
    @SortNatural
    private SortedSet<Product> products =
            new TreeSet<>(new ProductNameComparator());

    public void addProduct(Product product) {
        product.setType(this);
        products.add(product);
    }

    public void removeProduct(Product product) {
        product.setType(null);
        products.stream()
                .filter(p -> Objects.equals(p.getName(), product.getName()))
                .findFirst()
                .orElse(product)
                .setType(null);
        products.remove(product);
    }

    public SortedSet<Product> getProducts() {
        return products;
    }

    public String getProductTypeCode() {
        return productTypeCode;
    }

    public void setProductTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode;
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
        ProductType that = (ProductType) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
