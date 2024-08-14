package com.luv2code.MiniProject.controller;

import com.luv2code.MiniProject.payload.JwtAuthResponse;
import com.luv2code.MiniProject.payload.LoginDto;
import com.luv2code.MiniProject.payload.RegisterDto;
import com.luv2code.MiniProject.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/auth")
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping(value = {"/login", "/sign-in"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authenticationService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/register", "/sign-up"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authenticationService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}










