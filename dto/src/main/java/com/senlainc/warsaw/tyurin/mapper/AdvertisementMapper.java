package com.senlainc.warsaw.tyurin.mapper;

import com.senlainc.warsaw.tyurin.dto.AdvertisementDto;
import com.senlainc.warsaw.tyurin.entity.Advertisement;
import com.senlainc.warsaw.tyurin.entity.User;
import com.senlainc.warsaw.tyurin.enums.AdvertisementStatus;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@UtilityClass
public class AdvertisementMapper {

    public Advertisement mapToEntity(AdvertisementDto dto) {
        return Advertisement
                .builder()
                .seller(User
                        .builder()
                        .id(dto.getSellerId())
                        .build())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .status(AdvertisementStatus.OPEN)
                .creationDateTime(LocalDateTime.now())
                .build();
    }

    public AdvertisementDto mapToDto(Advertisement advertisement) {
        return AdvertisementDto
                .builder()
                .id(advertisement.getId())
                .customerId(advertisement.getCustomer().getId())
                .sellerId(advertisement.getSeller().getId())
                .title(advertisement.getTitle())
                .description(advertisement.getDescription())
                .price(advertisement.getPrice())
                .creationDateTime(advertisement.getCreationDateTime())
                .status(advertisement.getStatus())
                .isPaid(advertisement.isPaid())
                .paidSum(advertisement.getPaidSum())
                .paidTillDateTime(advertisement.getPaidTillDateTime())
                .soldDateTime(advertisement.getSoldDateTime())
                .comments(advertisement
                        .getComments()
                        .stream()
                        .map(CommentMapper::mapToDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
