package org.example.projectbackend.User.repository;

import org.example.projectbackend.User.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser_UserId(Long userId); // Sử dụng userId nếu thuộc tính trong User là userId
}

