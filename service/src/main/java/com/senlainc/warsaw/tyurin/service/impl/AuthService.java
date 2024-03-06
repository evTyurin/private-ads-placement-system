package com.senlainc.warsaw.tyurin.service.impl;

import com.senlainc.warsaw.tyurin.security.JwtTokenUtils;
import com.senlainc.warsaw.tyurin.service.IAuthService;
import com.senlainc.warsaw.tyurin.service.IUserService;
import com.senlainc.warsaw.tyurin.service.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService implements IAuthService {

    private IUserService userService;
    private JwtTokenUtils jwtTokenUtils;
    private AuthenticationManager authenticationManager;

    public AuthService(IUserService userService,
                       JwtTokenUtils jwtTokenUtils,
                       AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    public String createAuthToken(String email, String password) throws AuthException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (AuthenticationException e) {
            throw new AuthException(HttpStatus.UNAUTHORIZED.value());
        }
        UserDetails userDetails = userService.loadUserByUsername(email);
        return jwtTokenUtils.generateToken(userDetails);
    }
}
