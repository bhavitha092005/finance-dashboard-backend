package com.financeapp.controller;

import com.financeapp.dto.AuthRequest;
import com.financeapp.dto.AuthResponse;
import com.financeapp.model.User;
import com.financeapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // REGISTER
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {
        String message = authService.register(user);
        return Map.of("message", message);
    }

    // LOGIN
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return new AuthResponse(token);
    }
}