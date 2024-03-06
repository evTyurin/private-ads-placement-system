package com.senlainc.warsaw.tyurin.mapper;

import com.senlainc.warsaw.tyurin.dto.SellerEvaluationDto;
import com.senlainc.warsaw.tyurin.entity.Advertisement;
import com.senlainc.warsaw.tyurin.entity.SellerEvaluation;
import com.senlainc.warsaw.tyurin.entity.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class SellerEvaluationMapper {

    public SellerEvaluation mapToEntity(SellerEvaluationDto dto) {
        return SellerEvaluation
                .builder()
                .user(User
                        .builder()
                        .id(dto.getCustomerId())
                        .build())
                .advertisement(Advertisement
                        .builder()
                        .id(dto.getAdvertisementId())
                        .build())
                .evaluation(dto.getEvaluation())
                .creationDateTime(LocalDateTime.now())
                .build();
    }

    public SellerEvaluationDto mapToDto(SellerEvaluation sellerEvaluation) {
        return SellerEvaluationDto
                .builder()
                .id(sellerEvaluation.getId())
                .customerId(sellerEvaluation.getUser().getId())
                .advertisementId(sellerEvaluation.getAdvertisement().getId())
                .evaluation(sellerEvaluation.getEvaluation())
                .build();
    }
}
