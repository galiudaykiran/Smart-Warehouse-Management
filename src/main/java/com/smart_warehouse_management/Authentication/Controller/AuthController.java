package com.smart_warehouse_management.Authentication.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.smart_warehouse_management.Authentication.Dto.ChangePasswordDto;
import com.smart_warehouse_management.Authentication.Dto.ForgotPasswordDto;
import com.smart_warehouse_management.Authentication.Dto.JwtResponseDto;
import com.smart_warehouse_management.Authentication.Dto.LoginRequestDto;
import com.smart_warehouse_management.Authentication.Dto.RegisterRequestDto;
import com.smart_warehouse_management.Authentication.Dto.UserResponseDto;
import com.smart_warehouse_management.Authentication.Service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    
    
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {

        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }
    
    
    

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login( @Valid @RequestBody LoginRequestDto request) {

        return ResponseEntity.ok(authService.login(request));
    }
    
    
    
    
    @PostMapping("/change-password/{userId}")
    public ResponseEntity<String> changePassword( @PathVariable Long userId, @Valid @RequestBody ChangePasswordDto request) {

        authService.changePassword(userId, request);

        return ResponseEntity.ok("Password changed successfully.");
    }
    
    
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword( @Valid @RequestBody ForgotPasswordDto request) {

        authService.forgotPassword(request);

        return ResponseEntity.ok("Password reset successfully.");
    }
    
    
    
    
    
    
    
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {

        return ResponseEntity.ok(authService.getAllUsers());
    }
    
    
    
    
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {

        return ResponseEntity.ok(authService.getUserById(id));
    }
    
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        authService.deleteUser(id);

        return ResponseEntity.ok("User deleted successfully.");
    }
    
    
    @PutMapping("/users/{id}/activate")
    public ResponseEntity<String> activateUser(@PathVariable Long id) {

        authService.activateUser(id);

        return ResponseEntity.ok("User activated successfully.");
    }
    
    
    @PutMapping("/users/{id}/deactivate")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {

        authService.deactivateUser(id);

        return ResponseEntity.ok("User deactivated successfully.");
    }
    
    
    

}