package com.senlainc.warsaw.tyurin.service.impl;

import com.senlainc.warsaw.tyurin.ISellerEvaluationRepository;
import com.senlainc.warsaw.tyurin.constants.ExceptionCode;
import com.senlainc.warsaw.tyurin.entity.Advertisement;
import com.senlainc.warsaw.tyurin.entity.SellerEvaluation;
import com.senlainc.warsaw.tyurin.entity.User;
import com.senlainc.warsaw.tyurin.service.IAdvertisementService;
import com.senlainc.warsaw.tyurin.service.ISellerEvaluationService;
import com.senlainc.warsaw.tyurin.service.IUserService;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SellerEvaluationService implements ISellerEvaluationService {

    private ISellerEvaluationRepository sellerEvaluationRepository;
    private IUserService userService;
    private IAdvertisementService advertisementService;

    public SellerEvaluationService(ISellerEvaluationRepository sellerEvaluationRepository,
                                   IUserService userService,
                                   IAdvertisementService advertisementService) {
        this.sellerEvaluationRepository = sellerEvaluationRepository;
        this.userService = userService;
        this.advertisementService = advertisementService;
    }

    @Override
    public SellerEvaluation find(long id) throws NotFoundByIdException {
        return sellerEvaluationRepository.findById(id)
                .orElseThrow(()
                        -> new NotFoundByIdException(id, ExceptionCode.NOT_FOUND_EXCEPTION_CODE));
    }

    @Override
    public void create(SellerEvaluation sellerEvaluation) throws NotFoundByIdException {
        Advertisement advertisement = advertisementService
                .find(sellerEvaluation
                        .getAdvertisement()
                        .getId());
        User sender = userService.find(sellerEvaluation
                .getUser()
                .getId());
        sellerEvaluation.setAdvertisement(advertisement);
        sellerEvaluation.setUser(sender);
        sellerEvaluationRepository.create(sellerEvaluation);
    }
}
