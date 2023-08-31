package com.panov.bankingapi.transaction;

import com.panov.bankingapi.account.Account;
import com.panov.bankingapi.branch.Branch;
import com.panov.bankingapi.employee.Employee;
import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "TransactionIdSequence"
    )
    @SequenceGenerator(
        name = "TransactionIdSequence",
        sequenceName = "transaction_id_seq",
        allocationSize = 10
    )
    @Column(
        name = "txn_id",
        nullable = false
    )
    private Long id;

    @Column(
        name = "txn_time",
        nullable = false
    )
    @Generated(
        event = EventType.INSERT,
        sql = "now() at time zone 'utc'"
    )
    private LocalDateTime transactionTime;

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @JoinColumn(
        name = "account_id",
        nullable = false
    )
    private Account account;

    @Column(
        name = "txn_type_cd",
        nullable = false
    )
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(
        name = "amount",
        nullable = false,
        scale = 2
    )
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teller_emp_id")
    private Employee teller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "execution_branch_id")
    private Branch branch;

    @Column(
        name = "funds_avail_date",
        nullable = false
    )
    private LocalDate availableFrom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getTeller() {
        return teller;
    }

    public void setTeller(Employee teller) {
        this.teller = teller;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public LocalDate getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
