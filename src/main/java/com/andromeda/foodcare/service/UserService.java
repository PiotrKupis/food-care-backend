package com.andromeda.foodcare.service;

import com.andromeda.dto.DetailedUserPayload;
import com.andromeda.foodcare.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final AuthService authService;
    private final UserMapper userMapper;

    public DetailedUserPayload getCurrentUserData() {
        return userMapper.toDetailedUserPayload(authService.getCurrentUser());
    }
}
