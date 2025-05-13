package com.AssetManagementSystem.Manager.dto;

<<<<<<< HEAD
import java.util.List;

public class AuthenticationResponse {
    private String token;
    private String email;
    private String name;
    private String role;
    private String tokenType = "Bearer";

    // Default constructor
    public AuthenticationResponse() {
    }
    
    // Constructor
    public AuthenticationResponse(String token, String email, String name, String role) {
        this.token = token;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    // Getters and setters
=======
public class AuthenticationResponse {
    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

>>>>>>> main
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
<<<<<<< HEAD
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
=======
>>>>>>> main
} 