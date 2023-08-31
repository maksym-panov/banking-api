package com.panov.bankingapi.officer;

import com.panov.bankingapi.customer.business.Business;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class OfficerTest {
    @Test
    @Order(1)
    @DisplayName("Getters and setters perform correctly")
    void gettersAndSettersPerformCorrectly() {
        // given
        Officer officer = new Officer();
        Long id = 42L;
        String firstName = "TEST FNAME";
        String lastName = "TEST LNAME";
        String title = "TEST TITLE";
        LocalDate startDate = LocalDate.now().minusYears(3);
        LocalDate endDate = LocalDate.now();
        Business business = new Business();
        // when
        officer.setId(id);
        officer.setFirstName(firstName);
        officer.setLastName(lastName);
        officer.setTitle(title);
        officer.setStartDate(startDate);
        officer.setEndDate(endDate);
        officer.setBusiness(business);
        // then
        assertThat(officer.getId()).isSameAs(id);
        assertThat(officer.getFirstName()).isSameAs(firstName);
        assertThat(officer.getLastName()).isSameAs(lastName);
        assertThat(officer.getTitle()).isSameAs(title);
        assertThat(officer.getStartDate()).isSameAs(startDate);
        assertThat(officer.getEndDate()).isSameAs(endDate);
        assertThat(officer.getBusiness()).isSameAs(business);
    }

    @Test
    @Order(2)
    @DisplayName("Equals and hashCode perform correctly")
    void equalsAndHashCodePerformCorrectly() {
        // given
        Officer officer1 = new Officer();
        Officer officer2 = new Officer();
        Officer officer3 = new Officer();
        Officer officer4 = new Officer();
        // when
        officer1.setId(Long.valueOf(42000L));
        officer2.setId(Long.valueOf(42000L));
        officer3.setId(Long.valueOf(24000L));
        officer4.setId(Long.valueOf(24000L));
        // then
        assertThat(officer1.getId()).isNotSameAs(officer2.getId());
        assertThat(officer1).isEqualTo(officer2);
        assertThat(officer1).hasSameHashCodeAs(officer2);
        assertThat(officer3).isEqualTo(officer4);
        assertThat(officer3).hasSameHashCodeAs(officer4);
        assertThat(officer1).isNotEqualTo(officer3);
        assertThat(officer1).doesNotHaveSameHashCodeAs(officer3);
        assertThat(officer2).isNotEqualTo(officer4);
        assertThat(officer2).doesNotHaveSameHashCodeAs(officer4);
    }
}
