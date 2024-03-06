package com.senlainc.warsaw.tyurin.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotEnoughBalanceMoneyException extends Exception {

    private final double paidSum;
    private final int exceptionCode;
}
