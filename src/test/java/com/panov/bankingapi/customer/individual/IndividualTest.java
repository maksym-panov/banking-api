package com.panov.bankingapi.customer.individual;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class IndividualTest {
    @Test
    @Order(1)
    @DisplayName("Getters and setters perform correctly")
    void gettersAndSettersPerformCorrectly() {
        // given
        Individual individual = new Individual();
        String taxpayerId = "3111400552";
        String firstName = "Maksym";
        String lastName = "Panov";
        LocalDate birthday = LocalDate.of(2004, 6, 7);
        // when
        individual.setTaxpayerId(taxpayerId);
        individual.setFirstName(firstName);
        individual.setLastName(lastName);
        individual.setBirthday(birthday);
        // then
        assertThat(individual.getTaxpayerId()).isSameAs(taxpayerId);
        assertThat(individual.getFirstName()).isSameAs(firstName);
        assertThat(individual.getLastName()).isSameAs(lastName);
        assertThat(individual.getBirthday()).isSameAs(birthday);
    }

    @Test
    @Order(2)
    @DisplayName("Equals and hashCode perform correctly")
    void equalsAndHashCodePerformCorrectly() {
        // given
        Individual individual1 = new Individual();
        Individual individual2 = new Individual();
        Individual individual3 = new Individual();
        Individual individual4 = new Individual();
        // when
        individual1.setTaxpayerId(new String("1111111111"));
        individual2.setTaxpayerId(new String("1111111111"));
        individual3.setTaxpayerId(new String("2222222222"));
        individual4.setTaxpayerId(new String("2222222222"));
        // then
        assertThat(individual1).isEqualTo(individual2);
        assertThat(individual1).hasSameHashCodeAs(individual2);
        assertThat(individual3).isEqualTo(individual4);
        assertThat(individual3).hasSameHashCodeAs(individual4);
        assertThat(individual1).isNotEqualTo(individual3);
        assertThat(individual1).doesNotHaveSameHashCodeAs(individual3);
        assertThat(individual2).isNotEqualTo(individual4);
        assertThat(individual2).doesNotHaveSameHashCodeAs(individual4);
    }
}
