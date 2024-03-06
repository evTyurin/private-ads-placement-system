package com.senlainc.warsaw.tyurin.controller;

import com.senlainc.warsaw.tyurin.controller.util.QueryCriteriaBuilder;
import com.senlainc.warsaw.tyurin.controller.util.QueryCriteriaValidator;
import com.senlainc.warsaw.tyurin.dto.AdvertisementDto;
import com.senlainc.warsaw.tyurin.dto.PaymentDto;
import com.senlainc.warsaw.tyurin.dto.SoldAdvertisementDto;
import com.senlainc.warsaw.tyurin.entity.QueryCriteria;
import com.senlainc.warsaw.tyurin.mapper.AdvertisementMapper;
import com.senlainc.warsaw.tyurin.service.IAdvertisementService;
import com.senlainc.warsaw.tyurin.service.exception.EntityExistException;
import com.senlainc.warsaw.tyurin.service.exception.InvalidUrlParameterException;
import com.senlainc.warsaw.tyurin.service.exception.NotEnoughBalanceMoneyException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RestController that handles requests to /advertisements url
 * Contains operations with advertisements
 */
@RestController
@RequestMapping("advertisements")
public class AdvertisementController {

    private IAdvertisementService advertisementService;
    private QueryCriteriaBuilder queryCriteriaBuilder;
    private QueryCriteriaValidator queryCriteriaValidator;

    public AdvertisementController(IAdvertisementService advertisementService,
                                   QueryCriteriaBuilder queryCriteriaBuilder,
                                   QueryCriteriaValidator queryCriteriaValidator) {
        this.advertisementService = advertisementService;
        this.queryCriteriaBuilder = queryCriteriaBuilder;
        this.queryCriteriaValidator = queryCriteriaValidator;
    }

    /**
     * Search advertisements by params that come from url.
     * If params for search are empty this method returns all advertisements.
     *
     * @param minPrice        min price of advertisement
     * @param maxPrice        max price of advertisement
     * @param title           title of advertisement
     * @return list of advertisement dto
     * @throws InvalidUrlParameterException indicates that invalid parameters in url used for search
     */
    @GetMapping()
    public List<AdvertisementDto> findAll(@RequestParam(name = "minPrice", required = false) String minPrice,
                                          @RequestParam(name = "maxPrice", required = false) String maxPrice,
                                          @RequestParam(name = "title", required = false) String title,
                                          @RequestParam(name = "sortCriteria", required = false) String sortCriteria) throws InvalidUrlParameterException {
        List<QueryCriteria> searchQueryCriteria = queryCriteriaBuilder.createSearchCriteria(minPrice, maxPrice, title);
        queryCriteriaValidator.searchCriteriaValidation(searchQueryCriteria);
        List<QueryCriteria> sortQueryCriteria = queryCriteriaBuilder.createSortOrder(sortCriteria);
        queryCriteriaValidator.sortCriteriaValidation(sortQueryCriteria);

        return advertisementService
                .findAdvertisementsByCriterion(searchQueryCriteria, sortQueryCriteria)
                .stream()
                .map(AdvertisementMapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Create new payment
     *
     * @param paymentDto the payment dto
     * @return
     * @throws NotFoundByIdException          indicates that user with this id not exist
     * @throws EntityExistException           indicates that user with this email already exist
     * @throws NotEnoughBalanceMoneyException indicates that user do not have enough money on balance for purchase
     */
    @PostMapping("pay")
    public ResponseEntity<Void> create(@Valid @RequestBody PaymentDto paymentDto) throws NotFoundByIdException,
            EntityExistException,
            NotEnoughBalanceMoneyException {
        advertisementService.payForTopPlacing(paymentDto.getPayerId(),
                paymentDto.getAdvertisementId(),
                paymentDto.getPaidSum());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Update of advertisement by changing status from OPEN to CLOSED
     * and adding customer id and sold sum
     *
     * @param closeAdvertisementDto the close advertisement dto
     * @return
     * @throws NotFoundByIdException indicates that user with this id not exist
     */
    @PutMapping("close")
    public ResponseEntity<Void> closeAdvertisement(@Valid @RequestBody SoldAdvertisementDto closeAdvertisementDto) throws NotFoundByIdException {
        advertisementService.closeAdvertisement(closeAdvertisementDto.getCustomerId(),
                closeAdvertisementDto.getAdvertisementId(),
                closeAdvertisementDto.getSoldSum());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get advertisement dto by id
     *
     * @param id the advertisement id
     * @return the advertisement dto
     * @throws NotFoundByIdException indicates that advertisement with this id not exist
     */
    @GetMapping("{id}")
    public AdvertisementDto find(@PathVariable long id) throws NotFoundByIdException {
        return AdvertisementMapper.mapToDto(advertisementService.find(id));
    }

    /**
     * Create new advertisement
     *
     * @param advertisementDto the advertisement dto
     * @throws NotFoundByIdException indicates that advertisement with this id not exist
     */
    @PostMapping()
    public ResponseEntity<Void> create(@Valid @RequestBody AdvertisementDto advertisementDto) throws NotFoundByIdException {
        advertisementService.create(AdvertisementMapper.mapToEntity(advertisementDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update of advertisement
     *
     * @param id               the advertisement id
     * @param advertisementDto the advertisement dto
     * @throws NotFoundByIdException    indicates that advertisement with this id not exist
     */
    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @Valid @RequestBody AdvertisementDto advertisementDto) throws NotFoundByIdException {
        advertisementService.update(id, AdvertisementMapper.mapToEntity(advertisementDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete advertisement by id
     *
     * @param id the advertisement id
     * @throws NotFoundByIdException  indicates that advertisement with this id not exist
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) throws NotFoundByIdException {
        advertisementService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
