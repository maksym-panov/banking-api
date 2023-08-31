package com.panov.bankingapi.customer;

import com.panov.bankingapi.account.Account;
import com.panov.bankingapi.share.AddressInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerTest {
    @Test
    @Order(1)
    @DisplayName("Account bidirectional association is being managed correctly")
    void accountAssociationIsBeingManagedCorrectly() {
        // given
        Customer customer = new Customer();
        Account account1 = new Account();
        account1.setId(1L);
        Account account2 = new Account();
        account2.setId(2L);
        Account account3 = new Account();
        account3.setId(3L);
        // when
        customer.addAccount(account1);
        customer.addAccount(account2);
        customer.addAccount(account3);

        List<Account> underTest = customer.getAccounts();
        // then
        assertThat(underTest).hasSize(3);
        assertThat(underTest.get(0)).isSameAs(account1);
        assertThat(underTest.get(1)).isSameAs(account2);
        assertThat(underTest.get(2)).isSameAs(account3);
        assertThat(account1.getCustomer()).isSameAs(customer);
        assertThat(account2.getCustomer()).isSameAs(customer);
        assertThat(account3.getCustomer()).isSameAs(customer);
        // given
        Account account1_copy = new Account();
        account1_copy.setId(1L);
        account1_copy.setCustomer(customer);
        Account account3_copy = new Account();
        account3_copy.setId(3L);
        account3_copy.setCustomer(customer);
        // when
        customer.removeAccount(account1_copy);
        customer.removeAccount(account3_copy);
        // then
        assertThat(underTest).hasSize(1);
        assertThat(underTest.get(0)).isSameAs(account2);
        assertThat(account2.getCustomer()).isSameAs(customer);
        assertThat(account1.getCustomer()).isNull();
        assertThat(account1_copy.getCustomer()).isNull();
        assertThat(account3.getCustomer()).isNull();
        assertThat(account3_copy.getCustomer()).isNull();
    }

    @Test
    @Order(2)
    @DisplayName("Getters and setters perform correctly")
    void customerGettersAndSettersPerformCorrectly() {
        // given
        Customer customer = new Customer();
        UUID id = UUID.randomUUID();
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setRegion("TEST REGION");
        addressInfo.setCity("TEST CITY");
        addressInfo.setFullAddress("TEST STREET, TEST BUILDING, TEST APARTMENT");
        addressInfo.setPostalCode("10111");
        // when
        customer.setId(id);
        customer.setAddressInfo(addressInfo);
        // then
        assertThat(customer.getId()).isSameAs(id);
        assertThat(customer.getAddressInfo()).isSameAs(addressInfo);
        assertThat(customer.getAddressInfo().getRegion()).isSameAs("TEST REGION");
        assertThat(customer.getAddressInfo().getCity()).isSameAs("TEST CITY");
        assertThat(customer.getAddressInfo().getFullAddress()).isSameAs("TEST STREET, TEST BUILDING, TEST APARTMENT");
        assertThat(customer.getAddressInfo().getPostalCode()).isSameAs("10111");
    }

    @Test
    @Order(3)
    @DisplayName("Equals and hashCode perform correctly")
    void equalsAndHashCodePerformCorrectly() {
        // given
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        Customer customer3 = new Customer();
        Customer customer4 = new Customer();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.fromString(id1.toString());
        UUID id3 = UUID.randomUUID();
        UUID id4 = UUID.fromString(id3.toString());
        // when
        customer1.setId(id1);
        customer2.setId(id2);
        customer3.setId(id3);
        customer4.setId(id4);
        // then
        assertThat(id1).isNotSameAs(id2);
        assertThat(id3).isNotSameAs(id4);
        assertThat(customer1).isEqualTo(customer2);
        assertThat(customer1).hasSameHashCodeAs(customer2);
        assertThat(customer3).isEqualTo(customer4);
        assertThat(customer3).hasSameHashCodeAs(customer4);
        assertThat(customer1).isNotEqualTo(customer3);
        assertThat(customer1).doesNotHaveSameHashCodeAs(customer3);
        assertThat(customer2).isNotEqualTo(customer4);
        assertThat(customer2).doesNotHaveSameHashCodeAs(customer4);
    }
}
