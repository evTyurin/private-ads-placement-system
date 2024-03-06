package com.senlainc.warsaw.tyurin;

import com.senlainc.warsaw.tyurin.entity.SellerEvaluation;

import java.util.Optional;

public interface ISellerEvaluationRepository {

    Optional<SellerEvaluation> findById(long id);

    void create(SellerEvaluation sellerEvaluation);
}