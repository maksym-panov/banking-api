package com.panov.bankingapi.product_type;

import com.panov.bankingapi.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.SortedSet;

import static org.assertj.core.api.Assertions.*;

public class ProductTypeTest {
    @Test
    @Order(1)
    @DisplayName("Product entity bidirectional association is being managed correctly")
    void productAssociationIsBeingManagedCorrectly() {
        // given
        String product1_name = "c";
        String product2_name = "A";
        String product3_name = "B";

        ProductType type = new ProductType();
        Product product1 = new Product();
        product1.setName(product1_name);
        Product product2 = new Product();
        product2.setName(product2_name);
        Product product3 = new Product();
        product3.setName(product3_name);
        // when
        type.addProduct(product1);
        type.addProduct(product2);
        type.addProduct(product3);

        SortedSet<Product> underTest = type.getProducts();
        // then
        assertThat(underTest).hasSize(3);
        assertThat(underTest).contains(product1, product2, product3);
        assertThat(product1.getType()).isSameAs(type);
        assertThat(product2.getType()).isSameAs(type);
        assertThat(product3.getType()).isSameAs(type);

        Iterator<Product> iterator = underTest.iterator();
        assertThat(iterator.next()).isSameAs(product2);
        assertThat(iterator.next()).isSameAs(product3);
        assertThat(iterator.next()).isSameAs(product1);
        // when
        Product product2_copy = new Product();
        product2_copy.setName(product2_name);
        product2_copy.setType(type);

        type.removeProduct(product2_copy);
        // then
        assertThat(underTest).hasSize(2);
        assertThat(underTest).contains(product1, product3);
        assertThat(underTest).doesNotContain(product2, product2_copy);
        assertThat(product1.getType()).isSameAs(type);
        assertThat(product2.getType()).isNull();
        assertThat(product2_copy.getType()).isNull();
        assertThat(product3.getType()).isSameAs(type);

        iterator = underTest.iterator();
        assertThat(iterator.next()).isSameAs(product3);
        assertThat(iterator.next()).isSameAs(product1);
    }

    @Test
    @Order(2)
    @DisplayName("Adding products without name is not allowed")
    void addingProductsWithoutNameIsNotAllowed() {
        // given
        ProductType type = new ProductType();
        Product productWithNullName = new Product();
        // then
        assertThatThrownBy(() -> type.addProduct(productWithNullName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @Order(3)
    @DisplayName("Getters and setters perform correctly")
    void gettersAndSettersPerformCorrectly() {
        // given
        ProductType type = new ProductType();
        String code = "TEST_CODE";
        String name = "TEST NAME";
        // when
        type.setCode(code);
        type.setName(name);
        // then
        assertThat(type.getCode()).isSameAs(code);
        assertThat(type.getName()).isSameAs(name);
        assertThat(type.getProducts()).isNotNull();
        assertThat(type.getProducts()).hasSize(0);
    }

    @Test
    @Order(4)
    @DisplayName("Equals and hashCode perform correctly")
    void equalsAndHashCodePerformCorrectly() {
        // given
        ProductType type1 = new ProductType();
        ProductType type2 = new ProductType();
        ProductType type3 = new ProductType();
        ProductType type4 = new ProductType();
        // when
        type1.setName(new String("NAME 1"));
        type2.setName(new String("NAME 1"));
        type3.setName(new String("NAME 2"));
        type4.setName(new String("NAME 2"));
        // then
        assertThat(type1.getName()).isNotSameAs(type2.getName());
        assertThat(type1).isEqualTo(type2);
        assertThat(type1).hasSameHashCodeAs(type2);
        assertThat(type3).isEqualTo(type4);
        assertThat(type3).isEqualTo(type4);
        assertThat(type1).isNotEqualTo(type3);
        assertThat(type1).doesNotHaveSameHashCodeAs(type3);
        assertThat(type2).isNotEqualTo(type4);
        assertThat(type2).doesNotHaveSameHashCodeAs(type4);
    }
}
