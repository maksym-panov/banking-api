package com.panov.bankingapi.customer.business;

import com.panov.bankingapi.officer.Officer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BusinessTest {
    @Test
    @Order(1)
    @DisplayName("Officer entity bidirectional association is being managed correctly")
    void officerAssociationIsBeingManagedCorrectly() {
        // given
        Long id1 = 1L, id2 = 2L, id3 = 3L;
        Business business = new Business();
        Officer officer1 = new Officer();
        officer1.setId(id1);
        Officer officer2 = new Officer();
        officer2.setId(id2);
        Officer officer3 = new Officer();
        officer3.setId(id3);
        // when
        business.addOfficer(officer1);
        business.addOfficer(officer2);
        business.addOfficer(officer3);

        List<Officer> underTest = business.getOfficers();
        // then
        assertThat(underTest).hasSize(3);
        assertThat(underTest.get(0)).isSameAs(officer1);
        assertThat(underTest.get(1)).isSameAs(officer2);
        assertThat(underTest.get(2)).isSameAs(officer3);
        assertThat(officer1.getBusiness()).isSameAs(business);
        assertThat(officer2.getBusiness()).isSameAs(business);
        assertThat(officer3.getBusiness()).isSameAs(business);
        // when
        Officer officer2_copy = new Officer();
        officer2_copy.setId(id2);
        officer2_copy.setBusiness(business);

        business.removeOfficer(officer2_copy);
        // then
        assertThat(underTest).hasSize(2);
        assertThat(underTest.get(0)).isSameAs(officer1);
        assertThat(underTest.get(1)).isSameAs(officer3);
        assertThat(officer2.getBusiness()).isNull();
        assertThat(officer2_copy.getBusiness()).isNull();
    }

    @Test
    @Order(2)
    @DisplayName("Getters and setters perform correctly")
    void gettersAndSettersPerformCorrectly() {
        // given
        Business business = new Business();
        String usreou = "12345678";
        String officialName = "TEST OFFICIAL NAME";
        BusinessState state = BusinessState.ACTIVE;
        LocalDate incorpDate = LocalDate.now();
        // when
        business.setUsreou(usreou);
        business.setOfficialName(officialName);
        business.setState(state);
        business.setIncorpDate(incorpDate);
        // then
        assertThat(business.getUsreou()).isSameAs(usreou);
        assertThat(business.getOfficialName()).isSameAs(officialName);
        assertThat(business.getState()).isSameAs(state);
        assertThat(business.getIncorpDate()).isSameAs(incorpDate);
    }

    @Test
    @Order(3)
    @DisplayName("Equals and hashCode perform correctly")
    void equalsAndHashCodePerformCorrectly() {
        // given
        Business business1 = new Business();
        Business business2 = new Business();
        Business business3 = new Business();
        Business business4 = new Business();
        // when
        business1.setUsreou(new String("11111111"));
        business2.setUsreou(new String("11111111"));
        business3.setUsreou(new String("22222222"));
        business4.setUsreou(new String("22222222"));
        // then
        assertThat(business1).isEqualTo(business2);
        assertThat(business1).hasSameHashCodeAs(business2);
        assertThat(business3).isEqualTo(business4);
        assertThat(business3).hasSameHashCodeAs(business4);
        assertThat(business1).isNotEqualTo(business3);
        assertThat(business1).doesNotHaveSameHashCodeAs(business3);
        assertThat(business2).isNotEqualTo(business4);
        assertThat(business2).doesNotHaveSameHashCodeAs(business4);
    }
}
