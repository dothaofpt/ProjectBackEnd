package org.example.projectbackend.User.controller;

import org.example.projectbackend.User.dto.UserDTO;
import org.example.projectbackend.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // Đăng ký người dùng
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserDTO userDTO) {
        Map<String, Object> response = userService.registerUser(userDTO);
        if (response.containsKey("message") && response.get("message").equals("Username already exists")) {
            return ResponseEntity.status(400).body(response);  // Trả về lỗi nếu tên người dùng đã tồn tại
        }
        return ResponseEntity.ok(response);
    }


    // Đăng nhập người dùng
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody UserDTO userDTO) {
        Map<String, String> response = userService.loginUser(userDTO);

        if (response.containsKey("message") && response.get("message").equals("Invalid username or password")) {
            return ResponseEntity.status(401).body(response);  // Trả về lỗi 401 khi thông tin đăng nhập không đúng
        }

        if (response.containsKey("message") && response.get("message").equals("User not found")) {
            return ResponseEntity.status(404).body(response);  // Trả về lỗi 404 khi người dùng không tồn tại
        }

        // Trả về thông báo thành công cùng với token trong message
        return ResponseEntity.ok(response);
    }




}
