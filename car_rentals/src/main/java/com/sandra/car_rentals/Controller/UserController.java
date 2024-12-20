package com.sandra.car_rentals.Controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandra.car_rentals.Model.AuthenticationResponse;
import com.sandra.car_rentals.Model.LoginResponse;
import com.sandra.car_rentals.Model.User;
import com.sandra.car_rentals.Services.AuthenticationService;
import com.sandra.car_rentals.Services.UserServices;

@RestController
@RequestMapping(value = "/users")

public class UserController {

    @Autowired
    private final UserServices userService;
    private final AuthenticationService authService;

    public UserController(AuthenticationService authService, UserServices userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User request) {
        AuthenticationResponse authResponse = authService.authenticate(request);
        Optional<User> user = userService.findByUsername(request.getUsername());
        LoginResponse loginResponse = new LoginResponse(authResponse, user.get());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            authService.sendPasswordResetEmail(email);
            return ResponseEntity.ok().body(Map.of("message", "Password reset email sent"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/verify-token")
    public ResponseEntity<?> verifyToken(@RequestBody Map<String, String> request) {
        try {
            String token = request.get("token");
            boolean isValid = authService.verifyResetToken(token);
            if (isValid) {
                return ResponseEntity.ok().body(Map.of("message", "Token is valid"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid or expired token"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> request) {
        try {
            String token = request.get("token");
            String newPassword = request.get("newPassword");

            authService.updatePassword(token, newPassword);
            return ResponseEntity.ok().body(Map.of("message", "Password updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}