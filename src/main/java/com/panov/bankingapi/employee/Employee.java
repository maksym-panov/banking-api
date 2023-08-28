package com.panov.bankingapi.employee;

import com.panov.bankingapi.account.Account;
import com.panov.bankingapi.branch.Branch;
import com.panov.bankingapi.department.Department;
import com.panov.bankingapi.transaction.Transaction;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.SourceType;
import org.hibernate.generator.EventType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "EmployeeIdSequence"
    )
    @SequenceGenerator(
        name = "EmployeeIdSequence",
        sequenceName = "employee_id_seq",
        allocationSize = 10
    )
    @Column(
        name = "emp_id",
        nullable = false
    )
    private Long id;

    @Column(
        name = "fname",
        nullable = false,
        length = 63
    )
    private String firstName;

    @Column(
        name = "lname",
        nullable = false,
        length = 63
    )
    private String lastName;

    @Column(
        name = "title",
        nullable = false,
        length = 63
    )
    private String title;

    @Column(
        name = "start_date",
        nullable = false
    )
    @Generated(
            event = EventType.INSERT,
            sql = "now() at time zone 'utc'"
    )
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "superior_emp_id")
    private Employee superior;

    @OneToMany(mappedBy = "superior")
    @OrderBy("lastName asc, firstName asc")
    private List<Employee> subordinates = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_branch_id")
    private Branch branch;

    @OneToMany(mappedBy = "openEmployee")
    @OrderBy("openDate desc")
    private List<Account> openedAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "teller")
    @OrderBy("transactionTime desc")
    private List<Transaction> transactions = new ArrayList<>();

    public void addSubordinate(Employee employee) {
        employee.setSuperior(this);
        subordinates.add(employee);
    }

    public void removeSubordinate(Employee employee) {
        employee.setSuperior(null);
        subordinates.remove(employee);
    }

    public void addOpenedAccount(Account account) {
        account.setOpenEmployee(this);
        openedAccounts.add(account);
    }

    public void removeOpenedAccount(Account account) {
        account.setOpenEmployee(null);
        openedAccounts.remove(account);
    }

    public void addTransaction(Transaction transaction) {
        transaction.setTeller(this);
        transactions.add(transaction);
    }

    public void removeTransaction(Transaction transaction) {
        transaction.setTeller(null);
        transactions.remove(transaction);
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public List<Account> getOpenedAccounts() {
        return openedAccounts;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Employee getSuperior() {
        return superior;
    }

    public void setSuperior(Employee superior) {
        this.superior = superior;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
