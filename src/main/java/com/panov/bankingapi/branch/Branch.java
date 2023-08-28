package com.panov.bankingapi.branch;

import com.panov.bankingapi.account.Account;
import com.panov.bankingapi.employee.Employee;
import com.panov.bankingapi.share.LocalDateDescendingComparator;
import com.panov.bankingapi.transaction.Transaction;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.SortNatural;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "branch")
public class Branch {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "BranchIdSequence"
    )
    @SequenceGenerator(
        name = "BranchIdSequence",
        sequenceName = "branch_id_seq",
        allocationSize = 1
    )
    @Column(
        name = "branch_id",
        nullable = false
    )
    private Long id;

    @NaturalId
    @Column(
        name = "`name`",
        nullable = false
    )
    private String name;

    @Column(
        name = "region",
        nullable = false,
        length = 63
    )
    private String region;

    @Column(
        name = "city",
        nullable = false
    )
    private String city;

    @Column(
        name = "address",
        nullable = false
    )
    private String address;

    @Column(
        name = "postal_code",
        nullable = false,
        length = 5
    )
    private String postalCode;

    @OneToMany(mappedBy = "openBranch")
    @MapKey(name = "openDate")
    @SortNatural
    private Map<LocalDate, Account> accountsRegistry =
            new TreeMap<>(new LocalDateDescendingComparator());

    @OneToMany(
        mappedBy = "branch",
        cascade = {
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.PERSIST
        }
    )
    @MapKey(name = "startDate")
    @SortNatural
    private Map<LocalDate, Employee> employeesRegistry =
            new TreeMap<>(new LocalDateDescendingComparator());

    @OneToMany(mappedBy = "branch")
    @OrderBy("transactionTime desc")
    private List<Transaction> transactions = new ArrayList<>();

    public void addAccount(Account account) {
        account.setOpenBranch(this);
        accountsRegistry.put(account.getOpenDate(), account);
    }

    public void removeAccount(Account account) {
        account.setOpenBranch(null);
        accountsRegistry.remove(account.getOpenDate());
    }

    public void addEmployee(Employee employee) {
        employee.setBranch(this);
        employeesRegistry.put(employee.getStartDate(), employee);
    }

    public void removeEmployee(Employee employee) {
        employee.setBranch(null);
        employeesRegistry.remove(employee.getStartDate());
    }

    public void addTransaction(Transaction transaction) {
        transaction.setBranch(this);
        transactions.add(transaction);
    }

    public void removeTransaction(Transaction transaction) {
        transaction.setBranch(null);
        transactions.remove(transaction);
    }

    public Map<LocalDate, Account> getAccountsRegistry() {
        return accountsRegistry;
    }

    public Map<LocalDate, Employee> getEmployeesRegistry() {
        return employeesRegistry;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branch branch = (Branch) o;
        return name.equals(branch.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
