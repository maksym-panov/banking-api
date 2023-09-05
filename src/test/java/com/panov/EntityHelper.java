package com.panov;

import com.panov.bankingapi.account.Account;
import com.panov.bankingapi.account.AccountStatus;
import com.panov.bankingapi.branch.Branch;
import com.panov.bankingapi.customer.Customer;
import com.panov.bankingapi.customer.business.Business;
import com.panov.bankingapi.customer.business.BusinessState;
import com.panov.bankingapi.customer.individual.Individual;
import com.panov.bankingapi.department.Department;
import com.panov.bankingapi.employee.Employee;
import com.panov.bankingapi.officer.Officer;
import com.panov.bankingapi.product.Product;
import com.panov.bankingapi.product_type.ProductType;
import com.panov.bankingapi.share.AddressInfo;
import com.panov.bankingapi.transaction.Transaction;
import com.panov.bankingapi.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityHelper {
    private static final Random random = new Random();
    private static final List<LocalDate> dates = new ArrayList<>();
    private static final List<LocalDate> futureDates = new ArrayList<>();

    static {
        for (int i = 0; i < 100; ++i) {
            int year = 1991 + Math.abs(random.nextInt()) % 32;
            int month = 1 + Math.abs(random.nextInt()) % 12;
            int day = 1 + Math.abs(random.nextInt()) % 28;

            dates.add(LocalDate.of(year, month, day));
        }

        for (int i = 0; i < 100; ++i) {
           int months = Math.abs(random.nextInt()) % 12;
           int days = Math.abs(random.nextInt()) % 31;

           futureDates.add(
               LocalDate.now()
                       .plusMonths(months)
                       .plusDays(days)
           );
        }
    }

    private static final List<String> firstnames = List.of(
        "Maksym", "Oleg", "Serhii",
        "Oleksiy", "Andrii", "Yurii",
        "Mykyta", "Ivan", "Taras",
        "Oleksandr", "Vitalii", "Stanislav"
    );

    private static final List<String> lastnames = List.of(
        "Panov", "Martynenko", "Teplinskiy",
        "Dronov", "Burlaka", "Sibilov",
        "Anipatov", "Lifencev", "Shevchenko",
        "Voloshyn", "Sidin", "Zubko"
    );

    private static final List<String> regions = List.of(
        "Kharkiv", "Kyiv", "Ivano-Frankivsk",
        "Odessa", "Lviv", "Kherson",
        "Donetsk", "Khmelnytskyi", "Chernihiv",
        "Zaporizhzhia", "Sumy", "Cherkasy"
    );

    private static final List<String> cities = List.of(
        "Andriivka", "Verbove", "Zarozhne",
        "Truskivka", "Oleksiiske", "Snizhne",
        "Chervone", "Verhivka", "Solomyne",
        "Nudne", "Vesele", "Oskil"
    );

    private static final List<String> fullAddresses = List.of(
        "Shevchenka St., 13", "Gogol St., 17-A, apartment 11",
        "Myru St., 102-A", "Nauky St., 9-B", "Leonova St. 105, apartment 45",
        "Pravdy Prosp. 7-A", "Kharkivska St., 250, apartment 99",
        "Kharkiv Heroes St., 221-B, apartment 13", "Viiskova St., 11",
        "Feuerbach St., 105, apartment 153", "Kontorska St. 97, apartment 43"
    );

    private static final List<String> postalCodes = List.of(
        "10111", "63540", "43001",
        "96013", "19666", "54320",
        "48900", "76133", "55871",
        "32333", "67280", "15991"
    );

    private static final List<String> officialNames = List.of(
        "Apex Electronics", "Lion Productions", "Smart Security",
        "Icebergarts", "Marblightning", "Pixystems",
        "Twisterecords", "Robinair", "Smartsearch",
        "Freakstones", "Bumblebee Aviation", "Yewdew"
    );

    private static final List<String>  employeeTitles = List.of(
        "Bank Teller", "Banker", "Loan Processor",
        "Mortgage Consultant", "Investment Representative",
        "Credit Analyst", "Investment Banker", "Relationship Manager",
        "Financial Advisor", "Financial Analyst", "Asset Manager",
        "CEO", "Secretary", "Manager"
    );

    private static final List<String> officerTitles = List.of(
        "Branch Manager", "Assistant Manager", "Loan Officer",
        "Corporate Lawyer", "Employment Lawyer", "Bankruptcy Lawyer",
        "Intellectual Property Lawyer", "Estate Planning Lawyer"
    );

    private static final List<String> productTypeNames = List.of(
        "Savings", "Checking", "Mortgage",
        "Loans", "Certificate"
    );

    private static final List<String> productNames = List.of(
        "Personal Checking Account", "Corporate Checking Account",
        "Savings Account", "Money Market Account", "Credit Card",
        "Certificate of Deposit", "Debit Card"
    );

    private static final List<BusinessState> states = List.of(
        BusinessState.INACTIVE, BusinessState.ACTIVE
    );

    private static final List<AccountStatus> accountStatuses = List.of(
        AccountStatus.CLOSED, AccountStatus.BLOCKED, AccountStatus.ACTIVE
    );

    private static final List<TransactionType> transactionTypes = List.of(
        TransactionType.DEBIT, TransactionType.CREDIT
    );

    public static String randomNumericId(int length) {
        StringBuilder result = new StringBuilder();

        result.append(Math.abs(random.nextInt()) % 9 + 1);
        for (int i = 0; i < length - 1; ++i)
            result.append(Math.abs(random.nextInt()) % 10);

        return result.toString();
    }

    public static <T> T randomOfList(List<T> list) {
        if (list.size() == 0) return null;
        int id = Math.abs(random.nextInt()) % list.size();
        return list.get(id);
    }

    public static LocalDate randomBiggerDate(LocalDate given) {
        LocalDate result = LocalDate.of(1900, 1, 1);
        int tries = 0;
        while (tries < 50 && result.isBefore(given)) {
            result = randomOfList(dates);
            ++tries;
        }
        if (tries == 50) return null;
        return result;
    }

    public static AddressInfo randomAddressInfo() {
        return new AddressInfo(
            randomOfList(regions),
            randomOfList(cities),
            randomOfList(fullAddresses),
            randomOfList(postalCodes)
        );
    }

    public static Individual randomIndividual() {
        Individual individual = new Individual();

        individual.setTaxpayerId(randomNumericId(10));
        individual.setFirstName(randomOfList(firstnames));
        individual.setLastName(randomOfList(lastnames));
        individual.setBirthday(randomOfList(dates));
        individual.setAddressInfo(randomAddressInfo());

        return individual;
    }

    public static Business randomBusiness() {
        Business business = new Business();

        business.setUsreou(randomNumericId(8));
        business.setOfficialName(randomOfList(officialNames));
        business.setState(randomOfList(states));
        business.setAddressInfo(randomAddressInfo());
        business.setIncorpDate(randomOfList(dates));

        return business;
    }

    public static Customer randomCustomer() {
        if (Math.abs(random.nextInt()) % 2 == 0)
            return randomIndividual();
        return randomBusiness();
    }

    public static Branch randomBranch() {
        Branch branch = new Branch();
        branch.setName("TEST BRANCH #" + Math.abs(random.nextInt()));
        branch.setAddressInfo(randomAddressInfo());
        return branch;
    }

    public static Department randomDepartment() {
        Department department = new Department();
        department.setName("TEST DEPARTMENT #" + Math.abs(random.nextInt()));
        return department;
    }

    public static Employee randomEmployee() {
        Employee employee = new Employee();
        employee.setFirstName(randomOfList(firstnames));
        employee.setLastName(randomOfList(lastnames));
        employee.setTitle(randomOfList(employeeTitles));
        return employee;
    }

    public static Officer randomOfficer() {
        Officer officer = new Officer();
        officer.setTitle(randomOfList(officerTitles));
        officer.setFirstName(randomOfList(firstnames));
        officer.setLastName(randomOfList(lastnames));
        officer.setStartDate(randomOfList(dates));
        if (Math.abs(random.nextInt()) % 10 > 5) {
            officer.setEndDate(
                randomBiggerDate(officer.getStartDate())
            );
        }
        return officer;
    }

    public static ProductType randomProductType() {
        ProductType productType = new ProductType();
        productType.setCode("PTYPE_CD-" + Math.abs(random.nextInt()));
        productType.setName(randomOfList(productTypeNames));
        return productType;
    }

    public static Product randomProduct() {
        Product product = new Product();
        product.setCode("P_CD-" + Math.abs(random.nextInt()));
        product.setName(randomOfList(productNames));
        product.setOfferDate(randomOfList(dates));
        if (Math.abs(random.nextInt()) % 10 > 8) {
            product.setRetireDate(
                randomBiggerDate(product.getOfferDate())
            );
        }
        return product;
    }

    public static BigDecimal randomMoney(double seed) {
        return BigDecimal.valueOf(
            Math.round(random.nextDouble() * seed * 100) / 100
        );
    }

    public static Account randomAccount() {
        Account account = new Account();
        account.setAvailable(randomMoney(100000));
        account.setPending(randomMoney(1000));
        account.setStatus(randomOfList(accountStatuses));
        return account;
    }

    public static Transaction randomTransaction() {
        Transaction transaction = new Transaction();
        transaction.setType(randomOfList(transactionTypes));
        transaction.setDescription("DESCRIPTION " + randomNumericId(7));
        transaction.setAmount(randomMoney(1000));
        transaction.setAvailableFrom(LocalDate.now());
        if (Math.abs(random.nextInt()) % 10 > 6) {
            transaction.setAvailableFrom(randomOfList(futureDates));
        }
        return transaction;
    }
}
