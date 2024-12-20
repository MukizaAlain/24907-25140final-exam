package com.sandra.car_rentals.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sandra.car_rentals.Model.AuthenticationResponse;
import com.sandra.car_rentals.Model.Token;
import com.sandra.car_rentals.Model.User;
import com.sandra.car_rentals.Repository.TokenRepository;
import com.sandra.car_rentals.Repository.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthenticationService(
            UserRepository repository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            TokenRepository tokenRepository,
            AuthenticationManager authenticationManager,
            EmailService emailService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    public AuthenticationResponse register(User request) {
        // Check if user already exists
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, "User already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = repository.save(user);

        String jwt = jwtService.generateToken(user);
        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "User registration was successful");
    }

    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        User user = repository.findByUsername(request.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "User login was successful");
    }

    public void sendPasswordResetEmail(String email) {
        Optional<User> userOptional = repository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = UUID.randomUUID().toString();

            Token resetToken = new Token();
            resetToken.setToken(token);
            resetToken.setUser(user);
            resetToken.setLoggedOut(false);
            tokenRepository.save(resetToken);

            emailService.sendSimpleMessage(
                email,
                "Password Reset Request",
                "Use the following token to reset your password:: " + token
            );
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }


    public boolean verifyResetToken(String token) {
        Optional<Token> resetTokenOptional = tokenRepository.findByToken(token);
        return resetTokenOptional.isPresent() && !resetTokenOptional.get().isLoggedOut();
    }

    public void updatePassword(String token, String newPassword) {
        Optional<Token> resetTokenOptional = tokenRepository.findByToken(token);
        if (resetTokenOptional.isPresent()) {
            Token resetToken = resetTokenOptional.get();
            User user = resetToken.getUser();

            if (resetToken.isLoggedOut()) {
                throw new RuntimeException("Token is expired or already used");
            }

            user.setPassword(passwordEncoder.encode(newPassword));
            
            repository.save(user);

            resetToken.setLoggedOut(true);
            tokenRepository.save(resetToken);
        } else {
            throw new RuntimeException("Invalid or expired reset token");
        }
    }
    
    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if (validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t -> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }

    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}