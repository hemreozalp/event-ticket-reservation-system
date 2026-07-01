package com.hemreozalp.event_ticket_reservation_system.auth.service;

import com.hemreozalp.event_ticket_reservation_system.auth.dto.AuthResponse;
import com.hemreozalp.event_ticket_reservation_system.auth.dto.LoginRequest;
import com.hemreozalp.event_ticket_reservation_system.auth.dto.RegisterRequest;
import com.hemreozalp.event_ticket_reservation_system.config.jwt.JwtService;
import com.hemreozalp.event_ticket_reservation_system.config.security.CustomUserDetailsService;
import com.hemreozalp.event_ticket_reservation_system.user.entity.Role;
import com.hemreozalp.event_ticket_reservation_system.user.entity.User;
import com.hemreozalp.event_ticket_reservation_system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    public void register(RegisterRequest request) {

        validateRegisterRequest(request);

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .enabled(true)
                .build();

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.identifier())
                .or(() -> userRepository.findByEmail(request.identifier()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        request.password()
                )
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(user.getUsername());

        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(
                token,
                user.getUsername(),
                user.getRole().name()
        );
    }

    private void validateRegisterRequest(RegisterRequest request) {

        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }
    }
}
