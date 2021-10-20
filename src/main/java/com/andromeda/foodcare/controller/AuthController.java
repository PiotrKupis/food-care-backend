package com.andromeda.foodcare.controller;


import com.andromeda.controller.AuthApi;
import com.andromeda.dto.AuthenticationResponse;
import com.andromeda.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    @Override
    public ResponseEntity<AuthenticationResponse> login(LoginRequest loginRequest) {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setEmail("test");
        return ResponseEntity.ok(response);
    }
}
