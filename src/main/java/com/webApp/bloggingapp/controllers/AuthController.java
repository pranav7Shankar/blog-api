package com.webApp.bloggingapp.controllers;

import com.webApp.bloggingapp.entities.User;
import com.webApp.bloggingapp.payloads.UserDto;
import com.webApp.bloggingapp.security.LoginRequest;
import com.webApp.bloggingapp.security.LoginResponse;
import com.webApp.bloggingapp.security.JwtUtils;
import com.webApp.bloggingapp.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Login and Register endpoints")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<UserDto> register(@RequestBody @Valid UserDto userDto) {
        UserDto createdUser = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Returns JWT token on successful login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest){
        try {
            Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            User user = (User) authentication.getPrincipal();
            String token = jwtUtils.generateToken(user);

            return ResponseEntity.ok(
                    new LoginResponse(token, user.getEmail(), user.getName())
            );

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    java.util.Map.of(
                            "message", "Invalid email or password",
                            "success", false
                    )
            );
        }
    }
}
