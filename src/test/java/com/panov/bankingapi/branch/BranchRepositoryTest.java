package com.panov.bankingapi.branch;

import com.panov.EntityHelper;
import com.panov.bankingapi.share.AddressInfo;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.Optional;
import java.util.SortedSet;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class BranchRepositoryTest {
    private BranchRepository underTest;

    @Autowired
    public BranchRepositoryTest(BranchRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    @DisplayName("Should set ID to saved entity")
    void shouldSetIdToSavedEntity() {
        // given
        Branch branch = new Branch();
        // when
        Long generatedId = underTest.save(branch);
        // then
        assertThat(branch.getId()).isNotNull();
        assertThat(branch.getId()).isEqualTo(generatedId);
        // clear
        underTest.delete(generatedId);
    }

    @Test
    @DisplayName("Should remove existing branch by its ID")
    void shouldRemoveExistingBranchByItsId() {
        // given
        Branch branch = EntityHelper.randomBranch();
        // when
        Long generatedId = underTest.save(branch);
        Optional<Branch> optionalFromPersistenceBefore =
                underTest.findById(generatedId);
        underTest.delete(generatedId);
        Optional<Branch> optionalFromPersistenceAfter =
                underTest.findById(generatedId);
        // then
        assertThat(optionalFromPersistenceBefore).isNotEmpty();
        Branch fromPersistence = optionalFromPersistenceBefore.get();
        assertThat(fromPersistence.getName()).isEqualTo(branch.getName());
        assertThat(fromPersistence.getAddressInfo()).isEqualTo(branch.getAddressInfo());
        assertThat(optionalFromPersistenceAfter).isEmpty();
    }

    @Test
    @DisplayName("Should return list of all existing branches sorted by names in ascending order")
    void shouldReturnListOfAllExistingBranches() {
        // given
        Branch branch1 = EntityHelper.randomBranch();
        Branch branch2 = EntityHelper.randomBranch();
        Branch branch3 = EntityHelper.randomBranch();
        branch1.setName("aaaaaa");
        branch2.setName("cccccc");
        branch3.setName("bbbbbb");
        // when
        Long branchId1 = underTest.save(branch1);
        Long branchId2 = underTest.save(branch2);
        Long branchId3 = underTest.save(branch3);

        SortedSet<Branch> retrieved = underTest.findAll();
        // then
        assertThat(retrieved).hasSize(3);
        assertThat(retrieved).contains(branch1);
        assertThat(retrieved).contains(branch2);
        assertThat(retrieved).contains(branch3);

        Iterator<Branch> iterator = retrieved.iterator();
        assertThat(iterator.next()).isEqualTo(branch1);
        assertThat(iterator.next()).isEqualTo(branch3);
        assertThat(iterator.next()).isEqualTo(branch2);
        // clear
        underTest.delete(branchId1);
        underTest.delete(branchId2);
        underTest.delete(branchId3);
    }

    @Test
    @DisplayName("Should find an existing branch by its ID")
    void shouldFindExistingBranchByItsId() {
        // given
        Branch branch = EntityHelper.randomBranch();
        // when
        Long generatedId = underTest.save(branch);
        Optional<Branch> fromPersistenceOptional =
                underTest.findById(generatedId);
        // then
        assertThat(fromPersistenceOptional).isNotEmpty();
        Branch fromPersistence = fromPersistenceOptional.get();
        assertThat(fromPersistence.getName()).isEqualTo(branch.getName());
        assertThat(fromPersistence.getAddressInfo()).isEqualTo(branch.getAddressInfo());
        // clear
        underTest.delete(generatedId);
    }

    @Test
    @DisplayName("Should find an existing branch by its name")
    void shouldFindExistingBranchByItsName() {
        // given
        Branch branch = EntityHelper.randomBranch();
        // when
        Long generatedId = underTest.save(branch);
        Optional<Branch> fromPersistenceOptional =
                underTest.findByName(branch.getName());
        // then
        assertThat(fromPersistenceOptional).isNotEmpty();
        Branch fromPersistence = fromPersistenceOptional.get();
        assertThat(fromPersistence).isEqualTo(branch);
        assertThat(fromPersistence.getAddressInfo()).isEqualTo(branch.getAddressInfo());
        // clear
        underTest.delete(generatedId);
    }

    @Test
    @DisplayName("Should return an empty optional if there is no branch with provided ID")
    void shouldReturnEmptyOptionalIfThereIsNoBranchWithProvidedId() {
        // given
        Long illegalId1 = 11111L;
        Long illegalId2 = 4242424242L;
        Long illegalId3 = 98765L;
        // when
        Optional<Branch> empty1 = underTest.findById(illegalId1);
        Optional<Branch> empty2 = underTest.findById(illegalId2);
        Optional<Branch> empty3 = underTest.findById(illegalId3);
        // then
        assertThat(empty1).isEmpty();
        assertThat(empty2).isEmpty();
        assertThat(empty3).isEmpty();
    }

    @Test
    @DisplayName("Should return an empty optional if there is no branch with provided name")
    void shouldReturnEmptyOptionalIfThereIsNoBranchWithProvidedName() {
        // given
        String illegalName1 = "illegal-1";
        String illegalName2 = "illegal-2";
        String illegalName3 = "illegal-3";
        // when
        Optional<Branch> empty1 = underTest.findByName(illegalName1);
        Optional<Branch> empty2 = underTest.findByName(illegalName2);
        Optional<Branch> empty3 = underTest.findByName(illegalName3);
        // then
        assertThat(empty1).isEmpty();
        assertThat(empty2).isEmpty();
        assertThat(empty3).isEmpty();
    }

    @Test
    @DisplayName("Should throw an exception after saving an illegal branch object")
    void shouldThrowExceptionAfterSavingIllegalBranchObject() {
        // given
        Branch illegalWithBadName = EntityHelper.randomBranch();
        illegalWithBadName.setName(null);

        Branch illegalWithoutAddressInfo = new Branch();
        illegalWithoutAddressInfo.setName("illegal-without-address");

        Branch illegalWithoutRegion = EntityHelper.randomBranch();
        illegalWithoutRegion.getAddressInfo().setRegion(null);

        Branch illegalWithoutCity = EntityHelper.randomBranch();
        illegalWithoutCity.getAddressInfo().setCity(null);

        Branch illegalWithoutFullAddress = EntityHelper.randomBranch();
        illegalWithoutFullAddress.getAddressInfo().setFullAddress(null);

        Branch illegalWithoutPostalCode = EntityHelper.randomBranch();
        illegalWithoutPostalCode.getAddressInfo().setPostalCode(null);

        // then
        assertThatThrownBy(() -> underTest.save(illegalWithBadName))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> underTest.save(illegalWithoutAddressInfo))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> underTest.save(illegalWithoutRegion))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> underTest.save(illegalWithoutCity))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> underTest.save(illegalWithoutFullAddress))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> underTest.save(illegalWithoutPostalCode))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Branch employee list should be lazy")
    void branchEmployeeListShouldBeLazy() {
        // given
        Branch branch = EntityHelper.randomBranch();
        // when
        Long generatedId = underTest.save(branch);
        Optional<Branch> fromPersistenceOptional =
                underTest.findById(generatedId);
        // then
        assertThat(fromPersistenceOptional).isNotEmpty();
        Branch fromPersistence = fromPersistenceOptional.get();
        assertThatThrownBy(fromPersistence::getEmployeesRegistry)
                .isInstanceOf(PersistenceException.class);
        // clear
        underTest.delete(generatedId);
    }

    @Test
    @DisplayName("Should update existing branch entity")
    void shouldUpdateExistingBranchEntity() {
        // given
        Branch before = new Branch();
        String nameBefore = "before-name";
        String regionBefore = "before-region";
        String cityBefore = "before-city";
        String fullAddressBefore = "before-full-address";
        String postalCodeBefore = "11111";

        AddressInfo addressInfoBefore = new AddressInfo(
            regionBefore, cityBefore,
            fullAddressBefore, postalCodeBefore
        );

        before.setName(nameBefore);
        before.setAddressInfo(addressInfoBefore);

        Branch after = new Branch();
        String nameAfter = "after-name";
        String regionAfter = "after-region";
        String cityAfter = "after-region";
        String fullAddressAfter = "after-full-address";
        String postalCodeAfter = "22222";

        AddressInfo addressInfoAfter = new AddressInfo(
            regionAfter, cityAfter,
            fullAddressAfter, postalCodeAfter
        );

        after.setName(nameAfter);
        after.setAddressInfo(addressInfoAfter);

        // when
        Long generatedBySaveId = underTest.save(before);
        Optional<Branch> retrievedBeforeOptional =
                underTest.findById(generatedBySaveId);

        after.setId(generatedBySaveId);
        Long generatedByUpdateId = underTest.update(after);
        Optional<Branch>  retrievedAfterOptional =
                underTest.findById(generatedByUpdateId);
        // then
        assertThat(generatedBySaveId).isEqualTo(generatedByUpdateId);

        assertThat(retrievedBeforeOptional).isNotEmpty();
        Branch retrievedBefore = retrievedBeforeOptional.get();
        assertThat(retrievedBefore.getName()).isEqualTo(nameBefore);
        assertThat(retrievedBefore.getAddressInfo().getRegion())
                .isEqualTo(regionBefore);
        assertThat(retrievedBefore.getAddressInfo().getCity())
                .isEqualTo(cityBefore);
        assertThat(retrievedBefore.getAddressInfo().getFullAddress())
                .isEqualTo(fullAddressBefore);
        assertThat(retrievedBefore.getAddressInfo().getPostalCode())
                .isEqualTo(postalCodeBefore);

        assertThat(retrievedAfterOptional).isNotEmpty();
        Branch retrievedAfter = retrievedAfterOptional.get();
        assertThat(retrievedAfter.getName()).isEqualTo(nameAfter);
        assertThat(retrievedAfter.getAddressInfo().getRegion())
                .isEqualTo(regionAfter);
        assertThat(retrievedAfter.getAddressInfo().getCity())
                .isEqualTo(cityAfter);
        assertThat(retrievedAfter.getAddressInfo().getFullAddress())
                .isEqualTo(fullAddressAfter);
        assertThat(retrievedAfter.getAddressInfo().getPostalCode())
                .isEqualTo(postalCodeAfter);
        // clear
        underTest.delete(generatedBySaveId);
    }
}
