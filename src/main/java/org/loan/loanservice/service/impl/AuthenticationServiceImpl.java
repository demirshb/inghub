package org.loan.loanservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.loan.loanservice.exception.UserDoesNotExistException;
import org.loan.loanservice.exception.UserExistException;
import org.loan.loanservice.model.entity.Customer;
import org.loan.loanservice.model.enums.Role;
import org.loan.loanservice.model.request.LoginRequest;
import org.loan.loanservice.model.request.RegisterRequest;
import org.loan.loanservice.model.response.CreateCustomerResponse;
import org.loan.loanservice.model.response.JwtAuthenticationResponse;
import org.loan.loanservice.repository.CustomerRepository;
import org.loan.loanservice.service.AuthenticationService;
import org.loan.loanservice.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public CreateCustomerResponse register(RegisterRequest registerRequest) {
        Optional<Customer> existingUser = customerRepository.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new UserExistException();
        }
        Customer user = Customer.builder()
                .name(registerRequest.getFirstName())
                .surname(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .creditLimit(new BigDecimal("1000000"))
                .usedCreditLimit(BigDecimal.ZERO)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.CUSTOMER).build();
        Customer createdCustomer = customerRepository.save(user);
        return CreateCustomerResponse.builder()
                .customerId(createdCustomer.getId()).build();
    }

    public JwtAuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword())
        );

        var user = customerRepository.findByEmail(loginRequest.getEmail()).orElseThrow(UserDoesNotExistException::new);
        var jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponse.builder()
                .token(jwt).build();
    }

}