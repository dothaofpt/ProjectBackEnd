package org.example.projectbackend.User.service;

import org.example.projectbackend.User.entity.User;
import org.example.projectbackend.User.dto.UserDTO;
import org.example.projectbackend.User.entity.UserRole;
import org.example.projectbackend.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceIml {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceIml(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Mã hóa mật khẩu
        user.setEmail(userDTO.getEmail());
        user.setRole(UserRole.CUSTOMER); // Vai trò mặc định là CUSTOMER
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }
}

