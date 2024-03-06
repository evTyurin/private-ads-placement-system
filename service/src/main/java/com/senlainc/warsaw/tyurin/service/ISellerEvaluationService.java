package com.senlainc.warsaw.tyurin.service;

import com.senlainc.warsaw.tyurin.entity.SellerEvaluation;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;

public interface ISellerEvaluationService {

    SellerEvaluation find(long id) throws NotFoundByIdException;

    void create(SellerEvaluation sellerEvaluation) throws NotFoundByIdException;
}