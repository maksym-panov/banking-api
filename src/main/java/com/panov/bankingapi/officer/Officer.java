package com.panov.bankingapi.officer;

import com.panov.bankingapi.customer.business.Business;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "officer")
public class Officer {
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "OfficerIdSequence"
    )
    @SequenceGenerator(
        name = "OfficerIdSequence",
        sequenceName = "officer_id_seq",
        allocationSize = 5
    )
    @Column(
        name = "officer_id",
        nullable = false
    )
    private Long id;

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @JoinColumn(
        name = "cust_id",
        nullable = false
    )
    private Business business;

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
        length = 63
    )
    private String title;

    @Column(
        name = "start_date",
        nullable = false
    )
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Officer officer = (Officer) o;
        return Objects.equals(id, officer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
