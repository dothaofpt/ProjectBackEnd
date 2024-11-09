package org.example.projectbackend.User.service;

import org.example.projectbackend.User.entity.User;
import org.example.projectbackend.User.dto.UserDTO;
import org.example.projectbackend.User.entity.UserRole;
import org.example.projectbackend.User.jwt.JwtUtil;
import org.example.projectbackend.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Map<String, Object> registerUser(UserDTO userDTO) {
        Map<String, Object> response = new HashMap<>();


        if (findByUsername(userDTO.getUsername()).isPresent()) {
            response.put("message", "Username already exists");
            return response;
        }


        UserRole role = UserRole.CUSTOMER;
        if (userDTO.getRole() != null && userDTO.getRole().equalsIgnoreCase("ADMIN")) {

            Optional<User> existingAdmin = userRepository.findByRole(UserRole.ADMIN);
            if (existingAdmin.isPresent()) {
                response.put("message", "Only one ADMIN allowed");
                return response;
            }
            role = UserRole.ADMIN;
        } else if (userDTO.getRole() != null && userDTO.getRole().equalsIgnoreCase("STAFF")) {
            role = UserRole.STAFF;
        }


        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setRole(role);

        userRepository.save(user);

        response.put("message", "User registered successfully");
        response.put("userId", user.getUserId());
        return response;
    }



    @Override
    public Map<String, String> loginUser(UserDTO userDTO) {
        Map<String, String> response = new HashMap<>();

        Optional<User> userOptional = findByUsername(userDTO.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                response.put("message", "Login successful");
                String token = jwtUtil.generateToken(userDTO.getUsername());
                response.put("token", token);
                response.put("role", user.getRole().toString());  // Trả về role
            } else {
                response.put("message", "Invalid username or password");
            }
        } else {
            response.put("message", "User not found");
        }

        return response;
    }
}
