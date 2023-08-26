package com.panov.bankingapi;

import com.panov.bankingapi.department.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Transaction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingApiApplication.class, args);
    }

    @Bean
    CommandLineRunner test(EntityManagerFactory emf) {
        return args -> {
            EntityManager em = null;
            Transaction txn = null;

            try {
                em = emf.createEntityManager();
                txn = (Transaction) em.getTransaction();

                txn.begin();
                Department dep = new Department();
                dep.setName("Human Resources Department");
                em.persist(dep);

                System.out.println(dep.getId() + ", " + dep.getName());
                txn.commit();
            } catch (Exception e) {
                if (txn != null) {
                    txn.rollback();
                }
                throw e;
            } finally {
                if (em != null) {
                    em.close();
                }
            }
        };
    }
}
