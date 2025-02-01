package org.loan.loanservice.repository;


import org.loan.loanservice.model.entity.Customer;
import org.loan.loanservice.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByRole(Role role);
}