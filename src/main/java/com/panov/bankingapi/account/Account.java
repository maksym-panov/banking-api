package com.panov.bankingapi.account;

import com.panov.bankingapi.branch.Branch;
import com.panov.bankingapi.customer.Customer;
import com.panov.bankingapi.employee.Employee;
import com.panov.bankingapi.product.Product;
import com.panov.bankingapi.transaction.Transaction;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import org.hibernate.annotations.*;
import org.hibernate.generator.EventType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "AccountIdSequence"
    )
    @SequenceGenerator(
        name = "AccountIdSequence",
        sequenceName = "account_id_seq",
        allocationSize = 1
    )
    @Column(
        name = "account_id",
        nullable = false
    )
    private Long id;

    @Column(
        name = "open_date",
        nullable = false
    )
    @Generated(
            event = EventType.INSERT,
            sql = "now() at time zone 'utc'"
    )
    private LocalDate openDate;

    @Column(name = "close_date")
    private LocalDate closeDate;

    @Column(
        name = "last_activity_date",
        nullable = false
    )
    @Generated(
            event = { EventType.INSERT, EventType.UPDATE },
            sql = "now() at time zone 'utc'"
    )
    private LocalDate lastActivity;

    @Column(
        name = "status",
        nullable = false
    )
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(
        name = "avail_balance",
        nullable = false,
        scale = 2
    )
    private BigDecimal available;

    @Column(
        name = "pending_balance",
        nullable = false,
        scale = 2
    )
    private BigDecimal pending;

    @OneToMany(
        mappedBy = "account",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @OrderBy("transactionTime desc")
    private List<Transaction> transactions = new ArrayList<>();

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @JoinColumn(
        name = "product_cd",
        nullable = false
    )
    private Product product;

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @JoinColumn(
        name = "cust_id",
        nullable = false
    )
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "open_branch_id")
    private Branch openBranch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "open_emp_id")
    private Employee openEmployee;

    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        transactions.add(transaction);
    }

    public void removeTransaction(Transaction transaction) {
        transaction.setAccount(null);
        transactions.remove(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public LocalDate getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDate lastActivity) {
        this.lastActivity = lastActivity;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }

    public BigDecimal getPending() {
        return pending;
    }

    public void setPending(BigDecimal pending) {
        this.pending = pending;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Branch getOpenBranch() {
        return openBranch;
    }

    public void setOpenBranch(Branch openBranch) {
        this.openBranch = openBranch;
    }

    public Employee getOpenEmployee() {
        return openEmployee;
    }

    public void setOpenEmployee(Employee openEmployee) {
        this.openEmployee = openEmployee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
