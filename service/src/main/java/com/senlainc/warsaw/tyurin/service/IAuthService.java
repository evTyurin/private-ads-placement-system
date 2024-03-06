package com.senlainc.warsaw.tyurin.service;

import com.senlainc.warsaw.tyurin.service.exception.AuthException;

public interface IAuthService {
    String createAuthToken(String email, String password) throws AuthException;
}