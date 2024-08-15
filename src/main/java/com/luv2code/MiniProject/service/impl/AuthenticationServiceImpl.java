package com.luv2code.MiniProject.service.impl;

import com.luv2code.MiniProject.Mapper.RegisterMapper;
import com.luv2code.MiniProject.entity.Role;
import com.luv2code.MiniProject.entity.User;
import com.luv2code.MiniProject.exception.BlogApiException;
import com.luv2code.MiniProject.payload.LoginDto;
import com.luv2code.MiniProject.payload.RegisterDto;
import com.luv2code.MiniProject.repository.RoleRepository;
import com.luv2code.MiniProject.repository.UserRepository;
import com.luv2code.MiniProject.security.JwtTokenProvider;
import com.luv2code.MiniProject.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    AuthenticationManager authenticationManager;

    UserRepository userRepository;

    RoleRepository roleRepository;

    RegisterMapper registerMapper;

    PasswordEncoder passwordEncoder;

    JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUserNameOrEmail(), loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail()))
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email is already existed");

        if (userRepository.existsByUsername(registerDto.getUsername()))
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username is already existed");


        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        registerMapper.toEntity(registerDto);

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully!.";
    }
}
