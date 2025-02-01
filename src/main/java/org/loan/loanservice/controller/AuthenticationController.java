package org.loan.loanservice.controller;

import lombok.RequiredArgsConstructor;
import org.loan.loanservice.model.entity.Customer;
import org.loan.loanservice.model.request.LoginRequest;
import org.loan.loanservice.model.request.RegisterRequest;
import org.loan.loanservice.model.response.CreateCustomerResponse;
import org.loan.loanservice.model.response.JwtAuthenticationResponse;
import org.loan.loanservice.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<CreateCustomerResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

}