package com.sachin.UserAPI.services;

import com.sachin.UserAPI.Entity.UserEntity;
import com.sachin.UserAPI.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity deleteUserById(String id) {
        Optional<UserEntity> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            userRepository.deleteById(id);
            return userOpt.get();
        }
        return null;
    }

    public void createUser(UserEntity user) {
        userRepository.save(user);
    }

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public UserEntity updateUser(String id, Map<String, Object> updates) {
        Optional<UserEntity> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return null;
        }


        if (updates.containsKey("email")) {
            throw new IllegalArgumentException("Email update is not allowed");
        }

        UserEntity user = userOpt.get();

        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> user.setName((String) value);
                case "gender" -> user.setGender((String) value);
                case "job_title" -> user.setJobTitle((String) value);
            }
        });

        return userRepository.save(user);
    }

    public UserEntity replaceUser(String id, UserEntity updatedUser) {
        Optional<UserEntity> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isEmpty()) {
            return null;
        }

        UserEntity existingUser = existingUserOpt.get();
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setGender(updatedUser.getName());
        existingUser.setJobTitle(updatedUser.getEmail());

        return userRepository.save(existingUser);
    }
}
