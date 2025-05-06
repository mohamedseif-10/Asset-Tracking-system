package com.AssetManagementSystem.Manager.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.AssetManagementSystem.Manager.model.entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}