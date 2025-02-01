package org.loan.loanservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.loan.loanservice.model.entity.Customer;
import org.loan.loanservice.model.entity.Loan;
import org.loan.loanservice.model.entity.LoanInstalment;
import org.loan.loanservice.model.request.LoanRequestUser;
import org.loan.loanservice.model.response.CreateLoanResponse;
import org.loan.loanservice.model.response.PayLoanResponse;
import org.loan.loanservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create-loan")
    public ResponseEntity<CreateLoanResponse> createLoan(
            @RequestBody() @Valid LoanRequestUser request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = (Customer) authentication.getPrincipal();

        return ResponseEntity.ok(userService.createLoan(customer.getId(), request.getAmount(), request.getNumberOfInstallment()));
    }

    @PostMapping("/pay-loan")
    public ResponseEntity<PayLoanResponse> payLoan(
            @RequestParam int loanId, @RequestParam long amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = (Customer) authentication.getPrincipal();

        return ResponseEntity.ok(userService.payLoan(customer.getId(), loanId, amount));
    }

    @GetMapping("/list-loan")
    public ResponseEntity<List<Loan>> listLoan() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = (Customer) authentication.getPrincipal();

        return ResponseEntity.ok(userService.listLoan(customer.getId()));
    }

    @GetMapping("/list-installment")
    public ResponseEntity<List<LoanInstalment>> listLoanInstallments(@RequestParam int loanId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = (Customer) authentication.getPrincipal();

        return ResponseEntity.ok(userService.listLoanInstallments(customer.getId(), loanId));
    }


}