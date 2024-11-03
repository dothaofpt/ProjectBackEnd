package org.example.projectbackend.User.Filter;

import org.example.projectbackend.User.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain; // Cập nhật import để sử dụng jakarta
import jakarta.servlet.ServletException; // Cập nhật import để sử dụng jakarta
import jakarta.servlet.http.HttpServletRequest; // Cập nhật import để sử dụng jakarta
import jakarta.servlet.http.HttpServletResponse; // Cập nhật import để sử dụng jakarta

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService; // Thêm UserDetailsService để tải thông tin người dùng

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        // Nếu người dùng đã xác thực, thiết lập SecurityContext
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); // Tải thông tin người dùng
            if (jwtUtil.validateToken(jwt, userDetails)) { // Xác thực JWT
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication); // Thiết lập SecurityContext
            }
        }

        chain.doFilter(request, response);
    }
}
