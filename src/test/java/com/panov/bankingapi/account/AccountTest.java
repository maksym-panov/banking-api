package com.panov.bankingapi.account;

import com.panov.bankingapi.branch.Branch;
import com.panov.bankingapi.customer.business.Business;
import com.panov.bankingapi.employee.Employee;
import com.panov.bankingapi.product.Product;
import com.panov.bankingapi.transaction.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;


public class AccountTest {
    @Test
    @Order(1)
    @DisplayName("Transaction bidirectional association is being managed correctly")
    void managesBidirectionalAssociationWithTransactions() {
        // given
        Account a = new Account();
        LocalDateTime testTimeTxn1 = LocalDateTime.now();
        LocalDateTime testTimeTxn2 = LocalDateTime.now().minusYears(2).minusMonths(3).minusWeeks(1).minusDays(5).minusHours(23).minusMinutes(10).minusSeconds(1);

        Transaction txn1 = new Transaction();
        txn1.setTransactionTime(testTimeTxn1);
        Transaction txn2 = new Transaction();
        txn2.setTransactionTime(testTimeTxn2);
        // when
        a.addTransaction(txn2);
        a.addTransaction(txn1);

        Map<LocalDateTime, Transaction> underTest = a.getTransactionsRegistry();
        // then
        assertThat(underTest).hasSize(2);
        assertThat(underTest.get(testTimeTxn1)).isSameAs(txn1);
        assertThat(underTest.get(testTimeTxn2)).isSameAs(txn2);
        assertThat(txn1.getAccount()).isSameAs(a);
        assertThat(txn2.getAccount()).isSameAs(a);

        Iterator<Map.Entry<LocalDateTime, Transaction>> iterator = underTest.entrySet().iterator();
        assertThat(iterator.next().getValue()).isSameAs(txn1);
        assertThat(iterator.next().getValue()).isSameAs(txn2);
        // given
        Transaction txn2_copy = new Transaction();
        txn2_copy.setTransactionTime(testTimeTxn2);
        txn2_copy.setAccount(a);
        // when
        a.removeTransaction(txn2_copy);
        // then
        assertThat(underTest).hasSize(1);
        assertThat(underTest.get(testTimeTxn1)).isSameAs(txn1);
        assertThat(underTest.get(testTimeTxn2)).isNull();
        assertThat(txn1.getTransactionTime()).isSameAs(testTimeTxn1);
        assertThat(txn2.getAccount()).isNull();
        assertThat(txn2_copy.getAccount()).isNull();
    }

    @Test
    @Order(2)
    @DisplayName("Getters and setters perform correctly")
    void accountGettersAndSettersShouldPerformCorrectly() {
        // given
        Account a = new Account();
        Long id = 23L;
        BigDecimal available = BigDecimal.valueOf(350.50);
        BigDecimal pending = BigDecimal.TEN;
        AccountStatus status = AccountStatus.BLOCKED;
        Employee openEmployee = new Employee();
        Business customer = new Business();
        Branch openBranch = new Branch();
        Product product = new Product();
        LocalDate openDate = LocalDate.of(2020, 1, 1);
        LocalDate closeDate = LocalDate.now();
        LocalDate lastActivity = LocalDate.now();
        // when
        a.setId(id);
        a.setAvailable(available);
        a.setPending(pending);
        a.setStatus(status);
        a.setOpenEmployee(openEmployee);
        a.setCustomer(customer);
        a.setOpenBranch(openBranch);
        a.setProduct(product);
        a.setOpenDate(openDate);
        a.setCloseDate(closeDate);
        a.setLastActivity(lastActivity);
        // then
        assertThat(a.getId()).isSameAs(id);
        assertThat(a.getAvailable()).isSameAs(available);
        assertThat(a.getPending()).isSameAs(pending);
        assertThat(a.getStatus()).isSameAs(status);
        assertThat(a.getOpenEmployee()).isSameAs(openEmployee);
        assertThat(a.getCustomer()).isSameAs(customer);
        assertThat(a.getOpenBranch()).isSameAs(openBranch);
        assertThat(a.getProduct()).isSameAs(product);
        assertThat(a.getOpenDate()).isSameAs(openDate);
        assertThat(a.getCloseDate()).isSameAs(closeDate);
        assertThat(a.getLastActivity()).isSameAs(lastActivity);

        assertThat(a.getTransactionsRegistry()).isNotNull();
        assertThat(a.getTransactionsRegistry()).hasSize(0);
    }

    @Test
    @Order(3)
    @DisplayName("Equals and hashCode perform correctly")
    void equalsAndHashCodePerformCorrectly() {
        // given
        Account account1 = new Account();
        Account account2 = new Account();

        Account account3 = new Account();
        Account account4 = new Account();

        // when
        account1.setId(11L);
        account2.setId(11L);

        account3.setId(54L);
        account4.setId(54L);

        // then
        assertThat(account1).isEqualTo(account2);
        assertThat(account1).hasSameHashCodeAs(account2);

        assertThat(account3).isEqualTo(account4);
        assertThat(account3).hasSameHashCodeAs(account4);

        assertThat(account1).isNotEqualTo(account3);
    }
}
