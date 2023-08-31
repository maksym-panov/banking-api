package com.panov.bankingapi.department;

import com.panov.bankingapi.employee.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class DepartmentTest {
    @Test
    @Order(1)
    @DisplayName("Employee entity bidirectional association is being managed correctly")
    void employeeAssociationIsBeingManagedCorrectly() {
        // given
        Department department = new Department();

        LocalDate testDate1 = LocalDate.of(2000, 12, 1);
        LocalDate testDate2 = LocalDate.now();

        Employee employee1 = new Employee();
        employee1.setStartDate(testDate1);
        Employee employee2 = new Employee();
        employee2.setStartDate(testDate2);
        // when
        department.addEmployee(employee1);
        department.addEmployee(employee2);

        Map<LocalDate, Employee> underTest = department.getEmployeesRegistry();
        // then
        assertThat(underTest).hasSize(2);
        assertThat(underTest.get(testDate1)).isSameAs(employee1);
        assertThat(underTest.get(testDate2)).isSameAs(employee2);
        assertThat(employee1.getDepartment()).isSameAs(department);
        assertThat(employee2.getDepartment()).isSameAs(department);

        Iterator<Map.Entry<LocalDate, Employee>> iterator = underTest.entrySet().iterator();
        assertThat(iterator.next().getValue()).isSameAs(employee2);
        assertThat(iterator.next().getValue()).isSameAs(employee1);
        // when
        Employee employee2_copy = new Employee();
        employee2_copy.setStartDate(testDate2);
        employee2_copy.setDepartment(department);

        department.removeEmployee(employee2_copy);
        // then
        assertThat(underTest).hasSize(1);
        assertThat(underTest.get(testDate1)).isSameAs(employee1);
        assertThat(employee1.getDepartment()).isSameAs(department);
        assertThat(employee2.getDepartment()).isNull();
        assertThat(employee2_copy.getDepartment()).isNull();
    }

    @Test
    @Order(2)
    @DisplayName("Getters and setters perform correctly")
    void gettersAndSettersPerformCorrectly() {
        // given
        Department department = new Department();
        Long id = 42L;
        String name = "TEST NAME";
        // when
        department.setId(id);
        department.setName(name);
        // then
        assertThat(department.getId()).isSameAs(id);
        assertThat(department.getName()).isSameAs(name);
    }

    @Test
    @Order(3)
    @DisplayName("Equals and hashCode perform correctly")
    void equalsAndHashCodePerformCorrectly() {
        // given
        Department department1 = new Department();
        Department department2 = new Department();
        Department department3 = new Department();
        Department department4 = new Department();
        // when
        department1.setName(new String("TEST NAME 1"));
        department2.setName(new String("TEST NAME 1"));
        department3.setName(new String("TEST NAME 2"));
        department4.setName(new String("TEST NAME 2"));
        // then
        assertThat(department1).isEqualTo(department2);
        assertThat(department1).hasSameHashCodeAs(department2);
        assertThat(department3).isEqualTo(department4);
        assertThat(department3).hasSameHashCodeAs(department4);
        assertThat(department1).isNotEqualTo(department3);
        assertThat(department1).doesNotHaveSameHashCodeAs(department3);
        assertThat(department2).isNotEqualTo(department4);
        assertThat(department2).doesNotHaveSameHashCodeAs(department4);
    }
}
