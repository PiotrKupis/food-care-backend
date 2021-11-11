package com.andromeda.foodcare.controller;

import com.andromeda.controller.UserApi;
import com.andromeda.dto.DetailedUserPayload;
import com.andromeda.dto.UpdateUserResponse;
import com.andromeda.foodcare.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<DetailedUserPayload> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUserData());
    }

    @Override
    public ResponseEntity<UpdateUserResponse> updateUser(DetailedUserPayload detailedUserPayload) {
        return ResponseEntity.ok(userService.updateUser(detailedUserPayload));
    }
}
