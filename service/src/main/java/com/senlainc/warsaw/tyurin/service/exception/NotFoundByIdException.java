package com.senlainc.warsaw.tyurin.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundByIdException extends Exception {

    private final long id;
    private final int exceptionCode;
}