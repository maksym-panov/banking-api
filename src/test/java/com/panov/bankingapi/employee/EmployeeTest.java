package com.panov.bankingapi.employee;

import com.panov.bankingapi.branch.Branch;
import com.panov.bankingapi.department.Department;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class EmployeeTest {
    @Test
    @Order(1)
    @DisplayName("Subordinate employee entity bidirectional association is being managed correctly")
    void subordinateAssociationIsBeingManagedCorrectly() {
        // given
        Employee employee = new Employee();
        Employee subordinate1 = new Employee();
        subordinate1.setId(1L);
        Employee subordinate2 = new Employee();
        subordinate2.setId(2L);
        // when
        employee.addSubordinate(subordinate1);
        employee.addSubordinate(subordinate2);

        List<Employee> underTest = employee.getSubordinates();
        // then
        assertThat(underTest).hasSize(2);
        assertThat(underTest.get(0)).isSameAs(subordinate1);
        assertThat(underTest.get(1)).isSameAs(subordinate2);
        assertThat(subordinate1.getSuperior()).isSameAs(employee);
        assertThat(subordinate2.getSuperior()).isSameAs(employee);
        // when
        Employee subordinate1_copy = new Employee();
        subordinate1_copy.setId(1L);
        subordinate1_copy.setSuperior(employee);

        employee.removeSubordinate(subordinate1_copy);
        // then
        assertThat(underTest).hasSize(1);
        assertThat(underTest.get(0)).isSameAs(subordinate2);
        assertThat(subordinate2.getSuperior()).isSameAs(employee);
        assertThat(subordinate1.getSuperior()).isNull();
        assertThat(subordinate1_copy.getSuperior()).isNull();
    }

    @Test
    @Order(2)
    @DisplayName("Getters and setters perform correctly")
    void gettersAndSettersPerformCorrectly() {
        // given
        Employee employee = new Employee();
        Long id = 42L;
        Employee superior = new Employee();
        Department department = new Department();
        Branch branch = new Branch();
        String title = "TEST TITLE";
        String firstName = "TEST FIRSTNAME";
        String lastName = "TEST LASTNAME";
        LocalDate startDate = LocalDate.now().minusYears(2);
        LocalDate endDate = LocalDate.now();
        // when
        employee.setId(id);
        employee.setSuperior(superior);
        employee.setDepartment(department);
        employee.setBranch(branch);
        employee.setTitle(title);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setStartDate(startDate);
        employee.setEndDate(endDate);
        // then
        assertThat(employee.getId()).isSameAs(id);
        assertThat(employee.getSuperior()).isSameAs(superior);
        assertThat(employee.getDepartment()).isSameAs(department);
        assertThat(employee.getBranch()).isSameAs(branch);
        assertThat(employee.getTitle()).isSameAs(title);
        assertThat(employee.getFirstName()).isSameAs(firstName);
        assertThat(employee.getLastName()).isSameAs(lastName);
        assertThat(employee.getStartDate()).isSameAs(startDate);
        assertThat(employee.getEndDate()).isSameAs(endDate);
    }

    @Test
    @Order(3)
    @DisplayName("Equals and hashCode perform correctly")
    void equalsAndHashCodePerformCorrectly() {
        // given
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        Employee employee3 = new Employee();
        Employee employee4 = new Employee();
        // when
        employee1.setId(Long.valueOf(42000L));
        employee2.setId(Long.valueOf(42000L));
        employee3.setId(Long.valueOf(24000L));
        employee4.setId(Long.valueOf(24000L));
        // then
        assertThat(employee1.getId()).isNotSameAs(employee2.getId());
        assertThat(employee1).isEqualTo(employee2);
        assertThat(employee1).hasSameHashCodeAs(employee2);
        assertThat(employee3).isEqualTo(employee4);
        assertThat(employee3).hasSameHashCodeAs(employee4);
        assertThat(employee1).isNotEqualTo(employee3);
        assertThat(employee1).doesNotHaveSameHashCodeAs(employee3);
        assertThat(employee2).isNotEqualTo(employee4);
        assertThat(employee2).doesNotHaveSameHashCodeAs(employee4);
    }
}
