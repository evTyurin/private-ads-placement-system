package com.senlainc.warsaw.tyurin.controller;

import com.senlainc.warsaw.tyurin.dto.SellerEvaluationDto;
import com.senlainc.warsaw.tyurin.mapper.SellerEvaluationMapper;
import com.senlainc.warsaw.tyurin.service.ISellerEvaluationService;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * RestController that handles requests to /seller-evaluations url
 * Contains operations with seller evaluations
 */
@Slf4j
@RestController
@RequestMapping("seller-evaluations")
public class SellerEvaluationController {

    private ISellerEvaluationService sellerEvaluationService;

    public SellerEvaluationController(ISellerEvaluationService sellerEvaluationService) {
        this.sellerEvaluationService = sellerEvaluationService;
    }

    /**
     * Add new seller evaluation
     *
     * @param sellerEvaluationDto the seller evaluation dto
     * @throws NotFoundByIdException indicates that seller evaluation with this name already exist
     */
    @PostMapping()
    public ResponseEntity<Void> create(@Valid @RequestBody SellerEvaluationDto sellerEvaluationDto) throws NotFoundByIdException {
        sellerEvaluationService.create(SellerEvaluationMapper.mapToEntity(sellerEvaluationDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get seller evaluation dto by id
     *
     * @param id the seller evaluation id
     * @return the seller evaluation dto
     * @throws NotFoundByIdException indicates that seller evaluation with this id not exist
     */
    @GetMapping("{id}")
    public SellerEvaluationDto find(@PathVariable long id) throws NotFoundByIdException {
        return SellerEvaluationMapper.mapToDto(sellerEvaluationService.find(id));
    }
}
