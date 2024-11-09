package org.example.projectbackend.User.repository;

import org.example.projectbackend.User.entity.User;
import org.example.projectbackend.User.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByRole(UserRole userRole);
}
