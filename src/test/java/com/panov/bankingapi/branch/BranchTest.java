package com.panov.bankingapi.branch;

import com.panov.bankingapi.employee.Employee;
import com.panov.bankingapi.share.AddressInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BranchTest {
    @Test
    @Order(1)
    @DisplayName("Employee registry with bidirectional entity association is being managed correctly")
    void employeeBidirectionalAssociationIsBeingManagedCorrectly() {
        // given
        Branch branch = new Branch();
        Employee employee1 = new Employee();
        employee1.setStartDate(LocalDate.of(2019, 5, 3));
        Employee employee2 = new Employee();
        employee2.setStartDate(LocalDate.of(2022, 2, 2));
        Employee employee3 = new Employee();
        employee3.setStartDate(LocalDate.of(2023, 8, 29));
        // when
        branch.addEmployee(employee1);
        branch.addEmployee(employee3);
        branch.addEmployee(employee2);

        Map<LocalDate, Employee> underTest = branch.getEmployeesRegistry();
        LocalDate testDateEmp1 = LocalDate.of(2019, 5, 3);
        LocalDate testDateEmp2 = LocalDate.of(2022, 2, 2);
        LocalDate testDateEmp3 = LocalDate.of(2023, 8, 29);
        // then
        assertThat(underTest).hasSize(3);
        assertThat(underTest.get(testDateEmp1)).isSameAs(employee1);
        assertThat(underTest.get(testDateEmp2)).isSameAs(employee2);
        assertThat(underTest.get(testDateEmp3)).isSameAs(employee3);
        assertThat(employee1.getBranch()).isSameAs(branch);
        assertThat(employee2.getBranch()).isSameAs(branch);
        assertThat(employee3.getBranch()).isSameAs(branch);

        Iterator<Map.Entry<LocalDate, Employee>> iterator = underTest.entrySet().iterator();
        assertThat(iterator.next().getValue()).isSameAs(employee3);
        assertThat(iterator.next().getValue()).isSameAs(employee2);
        assertThat(iterator.next().getValue()).isSameAs(employee1);
        // when
        Employee employee1_copy = new Employee();
        employee1_copy.setStartDate(testDateEmp1);
        employee1_copy.setBranch(branch);
        Employee employee3_copy = new Employee();
        employee3_copy.setStartDate(testDateEmp3);
        employee3_copy.setBranch(branch);

        branch.removeEmployee(employee3_copy);
        branch.removeEmployee(employee1_copy);
        // then
        assertThat(underTest).hasSize(1);
        assertThat(underTest.get(testDateEmp1)).isNull();
        assertThat(underTest.get(testDateEmp2)).isSameAs(employee2);
        assertThat(underTest.get(testDateEmp3)).isNull();
        assertThat(employee1.getBranch()).isNull();
        assertThat(employee1_copy.getBranch()).isNull();
        assertThat(employee2.getBranch()).isSameAs(branch);
        assertThat(employee3.getBranch()).isNull();
        assertThat(employee3_copy.getBranch()).isNull();
    }

    @Test
    @Order(2)
    @DisplayName("Getters and setters perform correctly")
    void gettersAndSettersPerformCorrectly() {
        // given
        Branch branch = new Branch();
        Long id = 42L;
        String name = "Branch Under Test";

        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setRegion("Test Region");
        addressInfo.setCity("Test City");
        addressInfo.setFullAddress("Test Street, Test Building, Test Apartment");
        addressInfo.setPostalCode("10111");

        Employee employee = new Employee();
        employee.setStartDate(LocalDate.of(2022, 1, 1));

        // when
        branch.setId(id);
        branch.setName(name);
        branch.setAddressInfo(addressInfo);
        branch.addEmployee(employee);
        // then
        assertThat(branch.getId()).isSameAs(id);
        assertThat(branch.getName()).isSameAs(name);
        assertThat(branch.getAddressInfo()).isSameAs(addressInfo);
        assertThat(branch.getEmployeesRegistry()).hasSize(1);
    }

    @Test
    @Order(3)
    @DisplayName("Equals and hashCode perform correctly")
    void equalsAndHashCodePerformCorrectly() {
        // given
        Branch branch1 = new Branch();
        Branch branch2 = new Branch();
        Branch branch3 = new Branch();
        Branch branch4 = new Branch();
        // when
        branch1.setName(new String("TEST NAME 1"));
        branch2.setName(new String("TEST NAME 1"));
        branch3.setName(new String("TEST NAME 2"));
        branch4.setName(new String("TEST NAME 2"));
        // then
        assertThat(branch1).isNotSameAs(branch2);
        assertThat(branch3).isNotSameAs(branch4);
        assertThat(branch1).isEqualTo(branch2);
        assertThat(branch1).hasSameHashCodeAs(branch2);
        assertThat(branch3).isEqualTo(branch4);
        assertThat(branch3).hasSameHashCodeAs(branch4);
        assertThat(branch2).isNotEqualTo(branch4);
        assertThat(branch2).doesNotHaveSameHashCodeAs(branch4);
        assertThat(branch1).isNotEqualTo(branch3);
        assertThat(branch1).doesNotHaveSameHashCodeAs(branch3);
    }
}
