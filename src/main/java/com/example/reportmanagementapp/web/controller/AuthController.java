package com.example.reportmanagementapp.web.controller;

import com.example.reportmanagementapp.application.dto.AuthResponse;
import com.example.reportmanagementapp.application.dto.LoginRequest;
import com.example.reportmanagementapp.application.dto.UserDto;
import com.example.reportmanagementapp.application.user.commands.RegisterUserCommandHandler;
import com.example.reportmanagementapp.application.user.queries.FindUserByEmailQueryHandler;
import com.example.reportmanagementapp.domain.entity.User;
import com.example.reportmanagementapp.infrastructure.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final FindUserByEmailQueryHandler findUserByEmailQueryHandler;
    private final RegisterUserCommandHandler registerUserCommandHandler;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userDetailsService.loadUserByUsername(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        
        java.util.List<String> roles = user.getAuthorities().stream()
                .map(org.springframework.security.core.GrantedAuthority::getAuthority)
                .collect(java.util.stream.Collectors.toList());

        return ResponseEntity.ok(AuthResponse.builder()
                .token(jwtToken)
                .roles(roles)
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDto userDto) {
        User existing = findUserByEmailQueryHandler.handle(userDto.getEmail());
        if (existing != null) {
            return ResponseEntity.badRequest().body("There is already an account registered with that email");
        }
        registerUserCommandHandler.handle(userDto);
        return ResponseEntity.ok("User registered successfully");
    }
}
