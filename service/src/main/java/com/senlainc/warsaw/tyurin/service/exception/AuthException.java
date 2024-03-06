package com.senlainc.warsaw.tyurin.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthException extends Exception {

    private final int exceptionCode;
}
