package com.panov.bankingapi.account;

import com.panov.EntityHelper;
import com.panov.bankingapi.branch.Branch;
import com.panov.bankingapi.branch.BranchRepository;
import com.panov.bankingapi.customer.business.Business;
import com.panov.bankingapi.customer.business.BusinessRepository;
import com.panov.bankingapi.customer.individual.Individual;
import com.panov.bankingapi.customer.individual.IndividualRepository;
import com.panov.bankingapi.employee.Employee;
import com.panov.bankingapi.employee.EmployeeRepository;
import com.panov.bankingapi.product.Product;
import com.panov.bankingapi.product.ProductRepository;
import com.panov.bankingapi.product_type.ProductType;
import com.panov.bankingapi.product_type.ProductTypeRepository;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class AccountRepositoryTest {
    private final AccountRepository underTest;
    private final IndividualRepository individualRepository;
    private final BusinessRepository businessRepository;
    private final ProductTypeRepository typeRepository;
    private final ProductRepository productRepository;
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;

    @Autowired
    public AccountRepositoryTest(
        AccountRepository underTest,
        IndividualRepository individualRepository,
        BusinessRepository businessRepository,
        ProductTypeRepository tTypeRepository,
        ProductRepository productRepository,
        EmployeeRepository employeeRepository,
        BranchRepository branchRepository
    ) {
        this.underTest = underTest;
        this.individualRepository = individualRepository;
        this.businessRepository = businessRepository;
        this.typeRepository = tTypeRepository;
        this.productRepository = productRepository;
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
    }

    @Test
    @DisplayName("Should remove existing account by its ID")
    void shouldRemoveExistingAccountByItsId() {
        // given
        ProductType productType = EntityHelper.randomProductType();
        Product product = EntityHelper.randomProduct();
        Individual individual = EntityHelper.randomIndividual();
        Account account = EntityHelper.randomAccount();
        // when
        typeRepository.save(productType);
        productType.addProduct(product);
        productRepository.save(product);
        UUID individualId = individualRepository.save(individual);

        account.setProduct(product);
        individual.addAccount(account);

        Long generatedId = underTest.save(account);

        Optional<Account> optionalFromPersistenceBefore =
                underTest.findById(generatedId);
        underTest.delete(generatedId);
        Optional<Account> optionalFromPersistenceAfter =
                underTest.findById(generatedId);
        // then
        assertThat(optionalFromPersistenceBefore).isNotEmpty();
        assertThat(optionalFromPersistenceBefore.get()).isEqualTo(account);
        assertThat(optionalFromPersistenceAfter).isEmpty();
        // clear
        underTest.delete(generatedId);
        individualRepository.delete(individualId);
        productRepository.delete(product.getCode());
        typeRepository.delete(productType.getCode());
    }

    @Test
    @DisplayName("If no pagination params provided, should return list of all existing accounts of allowed types")
    void shouldReturnListOfAllExistingAccountsByDefault() {
        // given
        ProductType productType = EntityHelper.randomProductType();
        Product product = EntityHelper.randomProduct();
        Individual individual = EntityHelper.randomIndividual();

        Account account1 = EntityHelper.randomAccount();
        Account account2 = EntityHelper.randomAccount();
        Account account3 = EntityHelper.randomAccount();
        Account account4 = EntityHelper.randomAccount();
        Account account5 = EntityHelper.randomAccount();
        Account account6 = EntityHelper.randomAccount();

        account1.setStatus(AccountStatus.ACTIVE);
        account2.setStatus(AccountStatus.ACTIVE);
        account3.setStatus(AccountStatus.ACTIVE);
        account4.setStatus(AccountStatus.BLOCKED);
        account5.setStatus(AccountStatus.BLOCKED);
        account6.setStatus(AccountStatus.CLOSED);
        // when
        typeRepository.save(productType);
        productType.addProduct(product);
        productRepository.save(product);
        UUID individualId = individualRepository.save(individual);

        account1.setProduct(product);
        account2.setProduct(product);
        account3.setProduct(product);
        account4.setProduct(product);
        account5.setProduct(product);
        account6.setProduct(product);

        individual.addAccount(account1);
        individual.addAccount(account2);
        individual.addAccount(account3);
        individual.addAccount(account4);
        individual.addAccount(account5);
        individual.addAccount(account6);

        Long accId1 = underTest.save(account1);
        Long accId2 = underTest.save(account2);
        Long accId3 = underTest.save(account3);
        Long accId4 = underTest.save(account4);
        Long accId5 = underTest.save(account5);
        Long accId6 = underTest.save(account6);

        List<Account> allAccounts = underTest.find(
            new AccountStatus[] {
                AccountStatus.ACTIVE,
                AccountStatus.BLOCKED,
                AccountStatus.CLOSED
            },
            null, null
        );
        List<Account> activeAccounts = underTest.find(
            new AccountStatus[] { AccountStatus.ACTIVE },
            null, null
        );
        List<Account> blockedAccounts = underTest.find(
            new AccountStatus[] { AccountStatus.BLOCKED },
            null, null
        );
        List<Account> closedAccounts = underTest.find(
            new AccountStatus[] { AccountStatus.CLOSED },
            null, null
        );
        // then
        assertThat(allAccounts).hasSize(6);
        assertThat(allAccounts).contains(
            account1, account2, account3,
            account4, account5, account6
        );
        assertThat(activeAccounts).hasSize(3);
        assertThat(activeAccounts).contains(account1, account2, account3);
        assertThat(blockedAccounts).hasSize(2);
        assertThat(blockedAccounts).contains(account4, account5);
        assertThat(closedAccounts).hasSize(1);
        assertThat(closedAccounts).contains(account6);
        // clear
        underTest.delete(accId1);
        underTest.delete(accId2);
        underTest.delete(accId3);
        underTest.delete(accId4);
        underTest.delete(accId5);
        underTest.delete(accId6);
        individualRepository.delete(individualId);
        productRepository.delete(product.getCode());
        typeRepository.delete(productType.getCode());
    }

    @Test
    @DisplayName("Should paginate found accounts")
    void shouldPaginateFoundAccounts() {
        // given
        ProductType productType = EntityHelper.randomProductType();
        Product product = EntityHelper.randomProduct();
        Individual individual = EntityHelper.randomIndividual();

        Account account1 = EntityHelper.randomAccount();
        Account account2 = EntityHelper.randomAccount();
        Account account3 = EntityHelper.randomAccount();
        Account account4 = EntityHelper.randomAccount();
        Account account5 = EntityHelper.randomAccount();
        Account account6 = EntityHelper.randomAccount();

        account1.setStatus(AccountStatus.ACTIVE);
        account2.setStatus(AccountStatus.ACTIVE);
        account3.setStatus(AccountStatus.ACTIVE);
        account4.setStatus(AccountStatus.BLOCKED);
        account5.setStatus(AccountStatus.BLOCKED);
        account6.setStatus(AccountStatus.CLOSED);
        // when
        typeRepository.save(productType);
        productType.addProduct(product);
        productRepository.save(product);
        UUID individualId = individualRepository.save(individual);

        account1.setProduct(product);
        account2.setProduct(product);
        account3.setProduct(product);
        account4.setProduct(product);
        account5.setProduct(product);
        account6.setProduct(product);

        individual.addAccount(account1);
        individual.addAccount(account2);
        individual.addAccount(account3);
        individual.addAccount(account4);
        individual.addAccount(account5);
        individual.addAccount(account6);

        Long accId1 = underTest.save(account1);
        try { Thread.sleep(10); } catch (Exception ignored) {}
        Long accId2 = underTest.save(account2);
        try { Thread.sleep(10); } catch (Exception ignored) {}
        Long accId3 = underTest.save(account3);
        try { Thread.sleep(10); } catch (Exception ignored) {}
        Long accId4 = underTest.save(account4);
        try { Thread.sleep(10); } catch (Exception ignored) {}
        Long accId5 = underTest.save(account5);
        try { Thread.sleep(10); } catch (Exception ignored) {}
        Long accId6 = underTest.save(account6);

        AccountStatus[] all = new AccountStatus[] {
            AccountStatus.ACTIVE,
            AccountStatus.BLOCKED,
            AccountStatus.CLOSED
        };

        AccountStatus[] active = new AccountStatus[] { AccountStatus.ACTIVE };

        List<Account> allPage6Size1 = underTest.find(all, 1, 6);
        List<Account> allPage1Size2 = underTest.find(all, 1, 2);
        List<Account> allPage2Size2 = underTest.find(all, 2, 2);
        List<Account> allPage3Size2 = underTest.find(all, 3, 2);
        List<Account> allPage1Size4 = underTest.find(all, 1, 4);
        List<Account> allPage2Size4 = underTest.find(all, 2, 4);
        List<Account> allPage3Size4 = underTest.find(all, 3, 4);
        List<Account> allPage1Size10 = underTest.find(all, 1, 10);
        List<Account> allPage10Size10 = underTest.find(all, 10, 10);

        List<Account> activePage6Size1 = underTest.find(active, 1, 6);
        List<Account> activePage1Size2 = underTest.find(active, 1, 2);
        List<Account> activePage2Size2 = underTest.find(active, 2, 2);
        List<Account> activePage3Size2 = underTest.find(active, 3, 2);
        List<Account> activePage1Size4 = underTest.find(active, 1, 4);
        List<Account> activePage2Size4 = underTest.find(active, 2, 4);
        List<Account> activePage3Size4 = underTest.find(active, 3, 4);
        List<Account> activePage1Size10 = underTest.find(active, 1, 10);
        List<Account> activePage10Size10 = underTest.find(active, 10, 10);
        // then
        assertThat(allPage6Size1).hasSize(1);
        assertThat(allPage6Size1).contains(account6);
        assertThat(allPage1Size2).hasSize(2);
        assertThat(allPage1Size2).contains(account1, account2);
        assertThat(allPage2Size2).hasSize(2);
        assertThat(allPage2Size2).contains(account3, account4);
        assertThat(allPage3Size2).hasSize(2);
        assertThat(allPage3Size2).contains(account5, account6);
        assertThat(allPage1Size4).hasSize(4);
        assertThat(allPage1Size4).contains(account1, account2, account3, account4);
        assertThat(allPage2Size4).hasSize(2);
        assertThat(allPage2Size4).contains(account5, account6);
        assertThat(allPage3Size4).isEmpty();
        assertThat(allPage1Size10).hasSize(6);
        assertThat(allPage1Size10).contains(
            account1, account2, account3,
            account4, account5, account6
        );
        assertThat(allPage10Size10).isEmpty();

        assertThat(activePage6Size1).isEmpty();
        assertThat(activePage1Size2).hasSize(2);
        assertThat(activePage1Size2).contains(account1, account2);
        assertThat(activePage2Size2).hasSize(1);
        assertThat(activePage2Size2).contains(account3);
        assertThat(activePage3Size2).isEmpty();
        assertThat(activePage1Size4).hasSize(3);
        assertThat(activePage1Size4).contains(account1, account2, account3);
        assertThat(activePage2Size4).isEmpty();
        assertThat(activePage3Size4).isEmpty();
        assertThat(activePage1Size10).hasSize(3);
        assertThat(activePage1Size10).contains(account1, account2, account3);
        assertThat(activePage10Size10).isEmpty();
        // clear
        underTest.delete(accId1);
        underTest.delete(accId2);
        underTest.delete(accId3);
        underTest.delete(accId4);
        underTest.delete(accId5);
        underTest.delete(accId6);
        individualRepository.delete(individualId);
        productRepository.delete(product.getCode());
        typeRepository.delete(productType.getCode());
    }

    @Test
    @DisplayName("Should save account and find an exact one by its generated ID")
    void shouldFindExistingAccountByItsId() {
        // given
        Individual owner = EntityHelper.randomIndividual();
        ProductType type = EntityHelper.randomProductType();
        Product product = EntityHelper.randomProduct();
        Account account = new Account();
        // when
        typeRepository.save(type);
        type.addProduct(product);
        productRepository.save(product);
        UUID individualId = individualRepository.save(owner);

        account.setProduct(product);
        owner.addAccount(account);
        Long generatedId = underTest.save(account);
        // then
        Optional<Account> optionalFromPersistence =
                underTest.findById(generatedId);
        assertThat(optionalFromPersistence).isNotEmpty();

        Account fromPersistence = optionalFromPersistence.get();
        assertThat(fromPersistence.getId()).isEqualTo(account.getId());
        assertThat(fromPersistence.getLastActivity()).isEqualTo(account.getLastActivity());
        assertThat(fromPersistence.getOpenDate()).isEqualTo(account.getOpenDate());
        assertThat(fromPersistence.getCloseDate()).isEqualTo(account.getOpenDate());
        assertThat(fromPersistence.getStatus()).isEqualTo(account.getStatus());
        assertThat(fromPersistence.getAvailable()).isEqualTo(account.getAvailable());
        assertThat(fromPersistence.getPending()).isEqualTo(account.getPending());
        // clear
        underTest.delete(generatedId);
        individualRepository.delete(individualId);
        productRepository.delete(product.getCode());
        typeRepository.delete(type.getCode());
    }

    @Test
    @DisplayName("Should return an empty optional if there is no account with provided id")
    void shouldReturnEmptyOptionalIfThereIsNoSuchEntity() {
        // given
        Long notExistingId1 = 999999999999L;
        Long notExistingId2 = 1111L;
        Long notExistingId3 = 424242L;
        // when
        Optional<Account> optionalFromPersistence1 =
                underTest.findById(notExistingId1);
        Optional<Account> optionalFromPersistence2 =
                underTest.findById(notExistingId2);
        Optional<Account> optionalFromPersistence3 =
                underTest.findById(notExistingId3);
        // then
        assertThat(optionalFromPersistence1).isEmpty();
        assertThat(optionalFromPersistence2).isEmpty();
        assertThat(optionalFromPersistence3).isEmpty();
    }

    @Test
    @DisplayName("Should throw an exception after saving account object with incorrect data")
    void shouldThrowExceptionAfterSavingIncorrectAccount() {
        // given
        ProductType productType = EntityHelper.randomProductType();
        Product product = EntityHelper.randomProduct();
        Individual customer = EntityHelper.randomIndividual();

        Account withoutCustomer = EntityHelper.randomAccount();
        Account withoutProduct = EntityHelper.randomAccount();
        Account withoutStatus = EntityHelper.randomAccount();
        Account withoutPending = EntityHelper.randomAccount();
        Account withoutAvailable = EntityHelper.randomAccount();
        // when
        typeRepository.save(productType);
        product.setType(productType);
        productRepository.save(product);
        UUID individualId = individualRepository.save(customer);

        customer.addAccount(withoutProduct);
        customer.addAccount(withoutStatus);
        customer.addAccount(withoutPending);
        customer.addAccount(withoutAvailable);

        withoutCustomer.setProduct(product);
        withoutStatus.setProduct(product);
        withoutPending.setProduct(product);
        withoutAvailable.setProduct(product);

        withoutStatus.setStatus(null);
        withoutPending.setPending(null);
        withoutAvailable.setAvailable(null);
        // then
        assertThatThrownBy(() -> underTest.save(withoutCustomer))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> underTest.save(withoutProduct))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> underTest.save(withoutStatus))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> underTest.save(withoutPending))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> underTest.save(withoutAvailable))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> underTest.save(withoutAvailable))
                .isInstanceOf(IllegalArgumentException.class);
        // clear
        individualRepository.delete(individualId);
        productRepository.delete(product.getCode());
        typeRepository.delete(productType.getCode());
    }

    @Test
    @DisplayName("Should throw an exception after saving account object with basic values that violate constraints")
    void shouldThrowExceptionAfterSavingConstraintViolatingAccount() {
        // given
        Business business = EntityHelper.randomBusiness();
        ProductType productType = EntityHelper.randomProductType();
        Product product = EntityHelper.randomProduct();
        Account illegalPending = EntityHelper.randomAccount();
        Account illegalAvailable = EntityHelper.randomAccount();
        Account illegalStatus = EntityHelper.randomAccount();
        Account illegalCloseDate = EntityHelper.randomAccount();
        // when
        UUID businessId = businessRepository.save(business);
        typeRepository.save(productType);
        product.setType(productType);
        productRepository.save(product);

        business.addAccount(illegalPending);
        business.addAccount(illegalAvailable);
        business.addAccount(illegalStatus);
        business.addAccount(illegalCloseDate);

        illegalPending.setProduct(product);
        illegalAvailable.setProduct(product);
        illegalStatus.setProduct(product);
        illegalCloseDate.setProduct(product);

        illegalPending.setPending(BigDecimal.valueOf(-1));
        illegalAvailable.setAvailable(BigDecimal.valueOf(-1));
        illegalStatus.setStatus(null);
        illegalCloseDate.setCloseDate(
            LocalDate.now().minusDays(1)
        );
        // then
        assertThatThrownBy(() -> underTest.save(illegalPending))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> underTest.save(illegalAvailable))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> underTest.save(illegalStatus))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> underTest.save(illegalCloseDate))
                .isInstanceOf(IllegalArgumentException.class);
        // clear
        businessRepository.delete(businessId);
        productRepository.delete(product.getCode());
        typeRepository.delete(productType.getCode());
    }

    @Test
    @DisplayName("Associations should be lazy by default")
    void associationsShouldBeLazyByDefault() {
        // given
        ProductType productType = EntityHelper.randomProductType();
        Product product = EntityHelper.randomProduct();
        Business business = EntityHelper.randomBusiness();
        Employee openEmployee = EntityHelper.randomEmployee();
        Branch openBranch = EntityHelper.randomBranch();
        Account account = EntityHelper.randomAccount();
        // when
        typeRepository.save(productType);
        product.setType(productType);
        productRepository.save(product);
        UUID businessId = businessRepository.save(business);
        Long employeeId = employeeRepository.save(openEmployee);
        Long branchId = branchRepository.save(openBranch);

        account.setProduct(product);
        business.addAccount(account);
        account.setOpenEmployee(openEmployee);
        account.setOpenBranch(openBranch);

        Long generatedId = underTest.save(account);
        Optional<Account> fromPersistenceOptional =
                underTest.findById(generatedId);
        // then
        assertThat(fromPersistenceOptional).isNotEmpty();

        Account fromPersistence = fromPersistenceOptional.get();
        assertThatThrownBy(fromPersistence::getProduct)
                .isInstanceOf(PersistenceException.class);
        assertThatThrownBy(fromPersistence::getCustomer)
                .isInstanceOf(PersistenceException.class);
        assertThatThrownBy(fromPersistence::getOpenBranch)
                .isInstanceOf(PersistenceException.class);
        assertThatThrownBy(fromPersistence::getOpenEmployee)
                .isInstanceOf(PersistenceException.class);
        // clear
        underTest.delete(generatedId);
        branchRepository.delete(branchId);
        employeeRepository.delete(employeeId);
        businessRepository.delete(businessId);
        productRepository.delete(product.getCode());
        typeRepository.delete(productType.getCode());
    }

    @Test
    @DisplayName("findByCustomer() should return an empty list if there is no such customer")
    void shouldReturnEmptyListIfThereIsNoSuchCustomer() {
        // given
        UUID notExistingCustomerId = UUID.randomUUID();
        // when
        List<Account> forNotExistingCustomer =
                underTest.findByCustomer(notExistingCustomerId);
        // then
        assertThat(forNotExistingCustomer).isEmpty();
    }

    @Test
    @DisplayName("findByCustomer() should return a list of accounts of specific customer")
    void shouldReturnListOfAccountsOfSpecificCustomer() {
        // given
        ProductType productType = EntityHelper.randomProductType();
        Product product = EntityHelper.randomProduct();
        Business business = EntityHelper.randomBusiness();
        Individual individual = EntityHelper.randomIndividual();
        Account account1 = EntityHelper.randomAccount();
        Account account2 = EntityHelper.randomAccount();
        Account account3 = EntityHelper.randomAccount();
        Account account4 = EntityHelper.randomAccount();
        Account account5 = EntityHelper.randomAccount();
        Account account6 = EntityHelper.randomAccount();
        // when
        typeRepository.save(productType);
        productType.addProduct(product);
        productRepository.save(product);
        UUID businessId = businessRepository.save(business);
        UUID individualId = individualRepository.save(individual);

        account1.setProduct(product);
        account2.setProduct(product);
        account3.setProduct(product);
        account4.setProduct(product);
        account5.setProduct(product);
        account6.setProduct(product);

        business.addAccount(account1);
        business.addAccount(account2);
        business.addAccount(account3);
        individual.addAccount(account4);
        individual.addAccount(account5);
        individual.addAccount(account6);

        Long accId1 = underTest.save(account1);
        Long accId2 = underTest.save(account2);
        Long accId3 = underTest.save(account3);
        Long accId4 = underTest.save(account4);
        Long accId5 = underTest.save(account5);
        Long accId6 = underTest.save(account6);

        List<Account> ofBusiness = underTest.findByCustomer(businessId);
        List<Account> ofIndividual = underTest.findByCustomer(individualId);
        // then
        assertThat(ofBusiness).hasSize(3);
        assertThat(ofBusiness).contains(account1, account2, account3);
        assertThat(ofIndividual).hasSize(3);
        assertThat(ofIndividual).contains(account4, account5, account6);
        // clear
        underTest.delete(accId1);
        underTest.delete(accId2);
        underTest.delete(accId3);
        underTest.delete(accId4);
        underTest.delete(accId5);
        underTest.delete(accId6);
        individualRepository.delete(individualId);
        businessRepository.delete(businessId);
        productRepository.delete(product.getCode());
        typeRepository.delete(productType.getCode());
    }

    @Test
    @DisplayName("Should update existing account entity")
    void shouldUpdateExistingAccountEntity() {
        // given
        ProductType productType = EntityHelper.randomProductType();
        Product product = EntityHelper.randomProduct();
        Business business = EntityHelper.randomBusiness();
        Account before = new Account();
        Account after = new Account();

        AccountStatus beforeStatus = AccountStatus.ACTIVE;
        BigDecimal beforeAvailable = BigDecimal.valueOf(100.00);
        BigDecimal beforePending = BigDecimal.valueOf(10.0);

        AccountStatus afterStatus = AccountStatus.BLOCKED;
        BigDecimal afterAvailable = BigDecimal.valueOf(50.00);
        BigDecimal afterPending = BigDecimal.valueOf(50.00);

        before.setStatus(beforeStatus);
        before.setAvailable(beforeAvailable);
        before.setPending(beforePending);

        after.setStatus(afterStatus);
        after.setAvailable(afterAvailable);
        after.setPending(afterPending);
        // when
        typeRepository.save(productType);
        productType.addProduct(product);
        productRepository.save(product);
        UUID businessId = businessRepository.save(business);

        business.addAccount(before);
        before.setProduct(product);

        Long generatedId = underTest.save(before);
        Optional<Account> beforeOptionalFromPersistence =
                underTest.findById(generatedId);

        after.setId(generatedId);

        Long generatedByUpdateId = underTest.update(after);
        Optional<Account> afterOptionalFromPersistence =
                underTest.findById(generatedByUpdateId);
        // then
        assertThat(generatedId).isEqualTo(generatedByUpdateId);
        assertThat(beforeOptionalFromPersistence).isNotEmpty();
        Account beforeFromPersistence = beforeOptionalFromPersistence.get();
        assertThat(beforeFromPersistence.getStatus()).isEqualTo(beforeStatus);
        assertThat(beforeFromPersistence.getAvailable()).isEqualTo(beforeAvailable);
        assertThat(beforeFromPersistence.getPending()).isEqualTo(beforePending);
        assertThat(afterOptionalFromPersistence).isNotEmpty();
        Account afterFromPersistence = afterOptionalFromPersistence.get();
        assertThat(afterFromPersistence.getStatus()).isEqualTo(afterStatus);
        assertThat(afterFromPersistence.getAvailable()).isEqualTo(afterAvailable);
        assertThat(afterFromPersistence.getPending()).isEqualTo(afterPending);
        // clear
        underTest.delete(generatedId);
        businessRepository.delete(businessId);
        productRepository.delete(product.getCode());
        typeRepository.delete(productType.getCode());
    }
}
