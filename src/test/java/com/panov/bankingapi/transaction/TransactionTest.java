package com.panov.bankingapi.transaction;

import com.panov.bankingapi.account.Account;
import com.panov.bankingapi.branch.Branch;
import com.panov.bankingapi.employee.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionTest {
    @Test
    @Order(1)
    @DisplayName("Getters and setters perform correctly")
    void gettersAndSettersPerformCorrectly() {
        // given
        Transaction transaction = new Transaction();
        Long id = 42L;
        Employee teller = new Employee();
        Branch branch = new Branch();
        Account account = new Account();
        LocalDateTime transactionTime = LocalDateTime.now();
        LocalDate availableFrom = LocalDate.now();
        BigDecimal amount = BigDecimal.valueOf(100.00);
        TransactionType type = TransactionType.CREDIT;
        String description = "TEST DESCRIPTION";
        // when
        transaction.setId(id);
        transaction.setTeller(teller);
        transaction.setBranch(branch);
        transaction.setAccount(account);
        transaction.setTransactionTime(transactionTime);
        transaction.setAvailableFrom(availableFrom);
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setDescription(description);
        // then
        assertThat(transaction.getId()).isSameAs(id);
        assertThat(transaction.getTeller()).isSameAs(teller);
        assertThat(transaction.getBranch()).isSameAs(branch);
        assertThat(transaction.getAccount()).isSameAs(account);
        assertThat(transaction.getTransactionTime()).isSameAs(transactionTime);
        assertThat(transaction.getAvailableFrom()).isSameAs(availableFrom);
        assertThat(transaction.getAmount()).isSameAs(amount);
        assertThat(transaction.getType()).isSameAs(type);
        assertThat(transaction.getDescription()).isSameAs(description);
    }

    @Test
    @Order(2)
    @DisplayName("Equals and hashCode perform correctly")
    void equalsAndHashCodePerformCorrectly() {
        // given
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        Transaction transaction3 = new Transaction();
        Transaction transaction4 = new Transaction();
        // when
        transaction1.setId(Long.valueOf(42000L));
        transaction2.setId(Long.valueOf(42000L));
        transaction3.setId(Long.valueOf(24000L));
        transaction4.setId(Long.valueOf(24000L));
        // then
        assertThat(transaction1.getId()).isNotSameAs(transaction2.getId());
        assertThat(transaction1).isEqualTo(transaction2);
        assertThat(transaction1).hasSameHashCodeAs(transaction2);
        assertThat(transaction3).isEqualTo(transaction4);
        assertThat(transaction3).hasSameHashCodeAs(transaction4);
        assertThat(transaction1).isNotEqualTo(transaction3);
        assertThat(transaction1).doesNotHaveSameHashCodeAs(transaction3);
        assertThat(transaction2).isNotEqualTo(transaction4);
        assertThat(transaction2).doesNotHaveSameHashCodeAs(transaction4);
    }
}
