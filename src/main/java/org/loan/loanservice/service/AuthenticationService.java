package org.loan.loanservice.service;

import org.loan.loanservice.model.response.CreateCustomerResponse;
import org.loan.loanservice.model.response.JwtAuthenticationResponse;
import org.loan.loanservice.model.request.LoginRequest;
import org.loan.loanservice.model.request.RegisterRequest;

public interface AuthenticationService {
    CreateCustomerResponse register(RegisterRequest registerRequest);
    JwtAuthenticationResponse login(LoginRequest loginRequest);
}
