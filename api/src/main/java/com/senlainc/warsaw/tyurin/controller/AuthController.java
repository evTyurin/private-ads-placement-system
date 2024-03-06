package com.senlainc.warsaw.tyurin.controller;

import com.senlainc.warsaw.tyurin.dto.JwtRequestDto;
import com.senlainc.warsaw.tyurin.dto.JwtResponseDto;
import com.senlainc.warsaw.tyurin.service.IAuthService;
import com.senlainc.warsaw.tyurin.service.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestController that handles requests to /auth url
 * Contains authentication operations
 */
@Slf4j
@RestController
@RequestMapping("auth")
public class AuthController {

    private IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    /**
     * Create authentication token
     *
     * @param jwtRequestDto  the jwt request dto that include email and password
     * @return               jwt response dto that includes authentication token
     * @throws AuthException indicates that authentication failed and email and password should be checked
     */
    @PostMapping()
    public JwtResponseDto createAuthToken(@RequestBody JwtRequestDto jwtRequestDto) throws AuthException {
        return new JwtResponseDto(authService.createAuthToken(jwtRequestDto.getEmail(), jwtRequestDto.getPassword()));
    }
}
