package com.AssetManagementSystem.Manager.service;

import com.AssetManagementSystem.Manager.dto.RegisterRequest;
<<<<<<< HEAD
import com.AssetManagementSystem.Manager.model.User;
import com.AssetManagementSystem.Manager.repository.UserRepository;
=======
import com.AssetManagementSystem.Manager.model.entity.User;
import com.AssetManagementSystem.Manager.repository.UserRepo;
>>>>>>> main
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
<<<<<<< HEAD
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
=======
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(
            UserRepo userRepo,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepo = userRepo;
>>>>>>> main
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {
        var user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

<<<<<<< HEAD
        return userRepository.save(user);
=======
        return userRepo.save(user);
>>>>>>> main
    }
} 