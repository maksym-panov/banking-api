package com.panov.bankingapi.product;

import com.panov.bankingapi.product_type.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {
    @Test
    @Order(1)
    @DisplayName("Getters and setters perform correctly")
    void gettersAndSettersPerformCorrectly() {
        // given
        Product product = new Product();
        String code = "TEST_CODE";
        String name = "TEST PRODUCT NAME";
        ProductType type = new ProductType();
        LocalDate offerDate = LocalDate.of(2020, 1, 1);
        LocalDate retireDate = LocalDate.now();
        // when
        product.setCode(code);
        product.setName(name);
        product.setType(type);
        product.setOfferDate(offerDate);
        product.setRetireDate(retireDate);
        // then
        assertThat(product.getCode()).isSameAs(code);
        assertThat(product.getName()).isSameAs(name);
        assertThat(product.getType()).isSameAs(type);
        assertThat(product.getOfferDate()).isSameAs(offerDate);
        assertThat(product.getRetireDate()).isSameAs(retireDate);
    }

    @Test
    @Order(2)
    @DisplayName("Equals and hashCode perform correctly")
    void equalsAndHashCodePerformCorrectly() {
        // given
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Product product4 = new Product();
        // when
        product1.setName(new String("NAME 1"));
        product2.setName(new String("NAME 1"));
        product3.setName(new String("NAME 2"));
        product4.setName(new String("NAME 2"));
        // then
        assertThat(product1.getName()).isNotSameAs(product2.getName());
        assertThat(product1).isEqualTo(product2);
        assertThat(product1).hasSameHashCodeAs(product2);
        assertThat(product3).isEqualTo(product4);
        assertThat(product3).hasSameHashCodeAs(product4);
        assertThat(product1).isNotEqualTo(product3);
        assertThat(product1).doesNotHaveSameHashCodeAs(product3);
        assertThat(product2).isNotEqualTo(product4);
        assertThat(product2).doesNotHaveSameHashCodeAs(product4);
    }
}
