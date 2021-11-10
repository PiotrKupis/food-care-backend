package com.andromeda.foodcare.service;

import com.andromeda.dto.DetailedUserPayload;
import com.andromeda.dto.UpdateUserResponse;
import com.andromeda.foodcare.exceptions.AuthException;
import com.andromeda.foodcare.mapper.UserMapper;
import com.andromeda.foodcare.model.User;
import com.andromeda.foodcare.repository.UserRepository;
import com.andromeda.foodcare.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;

    public DetailedUserPayload getCurrentUserData() {
        log.info("Getting current user data");
        return userMapper.toDetailedUserPayload(authService.getCurrentUser());
    }

    public UpdateUserResponse updateUser(DetailedUserPayload detailedUserPayload) {
        log.info("Updating current user data");
        User currentUser = authService.getCurrentUser();

        if (detailedUserPayload.getName() != null) {
            currentUser.setName(detailedUserPayload.getName());
        }
        if (detailedUserPayload.getEmail() != null) {
            userRepository.findByEmail(detailedUserPayload.getEmail())
                .ifPresent((user) -> {
                    throw AuthException.emailAlreadyTaken(detailedUserPayload.getEmail());
                });
            currentUser.setEmail(detailedUserPayload.getEmail());
        }
        if (detailedUserPayload.getPassword() != null) {
            currentUser.setPassword(encoder.encode(detailedUserPayload.getPassword()));
        }
        if (detailedUserPayload.getPhoneNumber() != null) {
            currentUser.setPhoneNumber(detailedUserPayload.getPhoneNumber());
        }

        currentUser = userRepository.save(currentUser);
        UpdateUserResponse updateUserResponse = userMapper.toUpdateUserResponse(currentUser);
        updateUserResponse.setAuthToken(jwtProvider.generateToken(currentUser.getEmail()));
        return updateUserResponse;
    }
}
