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

        // Kiểm tra nếu người dùng đã tồn tại
        if (findByUsername(userDTO.getUsername()).isPresent()) {
            response.put("message", "Username already exists");
            return response;  // Trả về lỗi nếu tên người dùng đã tồn tại
        }

        // Đăng ký người dùng mới
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Mã hóa mật khẩu
        user.setEmail(userDTO.getEmail());
        user.setRole(UserRole.CUSTOMER); // Vai trò mặc định là CUSTOMER

        userRepository.save(user); // Lưu người dùng vào cơ sở dữ liệu

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
            // Kiểm tra mật khẩu
            if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                response.put("message", "Login successful");
                // Tạo token JWT sau khi đăng nhập thành công
                String token = jwtUtil.generateToken(userDTO.getUsername());
                response.put("token", token);  // Trả về token trong response
            } else {
                response.put("message", "Invalid username or password");
            }
        } else {
            response.put("message", "User not found");
        }

        return response;
    }

}
