package com.senlainc.warsaw.tyurin.service;

import com.senlainc.warsaw.tyurin.service.exception.EntityExistException;
import com.senlainc.warsaw.tyurin.service.exception.NotEnoughBalanceMoneyException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;

public interface IPaymentService {

    void payForTopPlacing(long payerId, long advertisementId, double paidSum) throws NotFoundByIdException, NotEnoughBalanceMoneyException, EntityExistException;
}
