package com.sachin.UserAPI.controllers;

import com.sachin.UserAPI.Entity.UserEntity;
import com.sachin.UserAPI.payload.ApiResponse;
import com.sachin.UserAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserEntity>>> getUsers() {
        List<UserEntity> users = new ArrayList<>(userService.getUsers());
        return ResponseEntity.ok(new ApiResponse<>(true, "Users retrieved successfully", users));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserEntity>> createUser(@RequestBody UserEntity user) {
        user.setDate(LocalDateTime.now());
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "User created successfully", user));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        UserEntity user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User not found", null));

        }
        return ResponseEntity.ok(new ApiResponse<>(true, "User retrieved successfully", user));
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateUserById(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        try {
            UserEntity updatedUser = userService.updateUser(id, updates);
            if (updatedUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User not found", null));
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Updated user successfully", updatedUser));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id) {
        UserEntity user = userService.deleteUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User not found", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", null));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> replaceCurrentUser(@PathVariable String id, @RequestBody UserEntity updatedUser) {
        UserEntity user = userService.replaceUser(id, updatedUser);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User not found", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "User replaced successfully", user));
    }
}


