package org.example.projectbackend.User.Controller;

import org.example.projectbackend.User.Entity.User;
import org.example.projectbackend.User.Entity.UserDTO;
import org.example.projectbackend.User.Security.JwtUtil;
import org.example.projectbackend.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserDTO userDTO) {
        User user = userService.registerUser(userDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("userId", user.getUserId());

        return ResponseEntity.ok(response);
    }


    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateUser(@RequestBody UserDTO userDTO) {
        User user = userService.findByUsername(userDTO.getUsername()).orElse(null);
        if (user != null && user.getPassword().equals(userDTO.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok("JWT Token: " + token);
        }
        return ResponseEntity.status(401).body("Invalid username or password");
    }
}