package com.senlainc.warsaw.tyurin.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidUrlParameterException extends Exception {

    private final String name;
    private final int exceptionCode;
}
