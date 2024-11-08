package org.example.projectbackend.User.service;

import org.example.projectbackend.User.entity.User;
import org.example.projectbackend.User.dto.UserDTO;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    Map<String, Object> registerUser(UserDTO userDTO);
    Map<String, String> loginUser(UserDTO userDTO);
}
