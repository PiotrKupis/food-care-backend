package com.andromeda.foodcare.service;

import com.andromeda.dto.AuthenticationResponse;
import com.andromeda.dto.LoginRequest;
import com.andromeda.foodcare.enums.UserRole;
import com.andromeda.foodcare.exceptions.AuthException;
import com.andromeda.foodcare.model.User;
import com.andromeda.foodcare.repository.UserRepository;
import com.andromeda.foodcare.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service responsible for managing objects of type {@link User}.
 */
@Slf4j
@AllArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    /**
     * Method responsible for handling login request.
     *
     * @param loginRequest object of type {@link LoginRequest}
     * @return object of type {@link AuthenticationResponse}
     */
    public AuthenticationResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                    loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtProvider.generateToken(loginRequest.getEmail());
            UserRole role = userRepository.findByEmail(loginRequest.getEmail())
                .map(User::getRole)
                .orElseThrow(AuthException::badCredentials);

            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setAuthToken(token);
            authenticationResponse.setEmail(loginRequest.getEmail());
            authenticationResponse.setRole(role.toString());
            log.info("Logged in user with email: " + loginRequest.getEmail());
            return authenticationResponse;
        } catch (AuthenticationException e) {
            throw AuthException.badCredentials();
        }
    }
}
