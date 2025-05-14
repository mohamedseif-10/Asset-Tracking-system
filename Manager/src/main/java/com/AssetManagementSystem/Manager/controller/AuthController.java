package com.AssetManagementSystem.Manager.controller;

import com.AssetManagementSystem.Manager.dto.AuthenticationRequest;
import com.AssetManagementSystem.Manager.dto.AuthenticationResponse;
import com.AssetManagementSystem.Manager.dto.RegisterRequest;
import com.AssetManagementSystem.Manager.model.entity.User;
import com.AssetManagementSystem.Manager.repository.UserRepo;
import com.AssetManagementSystem.Manager.security.JwtUtils;
import com.AssetManagementSystem.Manager.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {    private final AuthenticationService authenticationService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final JwtUtils jwtUtils;    public AuthController(
            AuthenticationService authenticationService,
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            UserRepo userRepo,
            JwtUtils jwtUtils) {
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.jwtUtils = jwtUtils;
    }    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testPublicAccess() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "This is a public endpoint that doesn't require authentication");
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/me")    public ResponseEntity<?> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            User user = userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            Map<String, Object> response = new HashMap<>();
            response.put("email", user.getEmail());
            response.put("name", user.getName());
            response.put("role", user.getRole());
            response.put("authorities", userDetails.getAuthorities());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Not authenticated"));
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        System.out.println("Registration request received for: " + request.getEmail());
        return ResponseEntity.ok(authenticationService.register(request));
    }    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        System.out.println("Login request received for: " + request.getEmail());
        
        try {
            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            
            // Verify password
            if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
            }
            
            // Create authentication token for JWT
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
            
            // Set the authentication in the context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Generate JWT token
            String jwt = jwtUtils.generateJwtToken(authentication);
              // Get user details for the response
            User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Create JWT response with user info
            AuthenticationResponse response = new AuthenticationResponse(
                jwt, 
                user.getEmail(),
                user.getName(),
                user.getRole()
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials: " + e.getMessage()));
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // In a stateless JWT implementation, the client is responsible for discarding the token
        // Server-side we just clear the SecurityContext
        SecurityContextHolder.clearContext();
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        
        return ResponseEntity.ok(response);
    }
}
