package org.loan.loanservice.repository;


import org.loan.loanservice.model.entity.Loan;
import org.loan.loanservice.model.entity.LoanInstalment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {
    Optional<Loan> findByisPaidFalse();

    @Query("SELECT l from Loan l where l.customerId = :customerId and l.id = :loanId")
    Loan findCustomerLoan(Integer customerId, int loanId);
    @Query("SELECT l from Loan l where l.customerId = :customerId")
    List<Loan> findByCustomerId(Integer customerId);
}