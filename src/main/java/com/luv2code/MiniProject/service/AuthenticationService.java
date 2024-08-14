package com.luv2code.MiniProject.service;

import com.luv2code.MiniProject.payload.LoginDto;
import com.luv2code.MiniProject.payload.RegisterDto;

public interface AuthenticationService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
