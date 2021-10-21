package com.andromeda.foodcare.controller;


import com.andromeda.controller.AuthApi;
import com.andromeda.dto.AuthenticationResponse;
import com.andromeda.dto.LoginRequest;
import com.andromeda.foodcare.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<AuthenticationResponse> login(LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
