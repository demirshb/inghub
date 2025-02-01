package org.loan.loanservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.loan.loanservice.model.entity.Loan;
import org.loan.loanservice.model.entity.LoanInstalment;
import org.loan.loanservice.model.request.LoanRequestAdmin;
import org.loan.loanservice.model.response.CreateLoanResponse;
import org.loan.loanservice.model.response.PayLoanResponse;
import org.loan.loanservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserService userService;

    @PostMapping("/create-loan")
    public ResponseEntity<CreateLoanResponse> createLoan(
            @RequestBody() @Valid LoanRequestAdmin request) {
        return ResponseEntity.ok(userService.createLoan(request.getCustomerId(), request.getAmount(), request.getNumberOfInstallment()));
    }

    @PostMapping("/pay-loan")
    public ResponseEntity<PayLoanResponse> payLoan(
            @RequestParam int loanId, @RequestParam long amount, @RequestParam Integer customerId) {
        return ResponseEntity.ok(userService.payLoan(customerId, loanId, amount));
    }

    @GetMapping("/list-loan")
    public ResponseEntity<List<Loan>> listLoan(@RequestParam Integer customerId) {
        return ResponseEntity.ok(userService.listLoan(customerId));
    }

    @GetMapping("/list-installment")
    public ResponseEntity<List<LoanInstalment>> listLoanInstallments(@RequestParam int customerId, @RequestParam int loanId) {
        return ResponseEntity.ok(userService.listLoanInstallments(customerId, loanId));
    }

}