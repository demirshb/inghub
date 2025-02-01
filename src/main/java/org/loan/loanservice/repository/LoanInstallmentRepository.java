package org.loan.loanservice.repository;


import org.loan.loanservice.model.entity.Customer;
import org.loan.loanservice.model.entity.Loan;
import org.loan.loanservice.model.entity.LoanInstalment;
import org.loan.loanservice.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanInstallmentRepository extends JpaRepository<LoanInstalment, Integer> {


}