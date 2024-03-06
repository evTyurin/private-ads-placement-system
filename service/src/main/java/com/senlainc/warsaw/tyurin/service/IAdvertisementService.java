package com.senlainc.warsaw.tyurin.service;

import com.senlainc.warsaw.tyurin.entity.Advertisement;
import com.senlainc.warsaw.tyurin.entity.QueryCriteria;
import com.senlainc.warsaw.tyurin.service.exception.EntityExistException;
import com.senlainc.warsaw.tyurin.service.exception.NotEnoughBalanceMoneyException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;

import java.util.List;

public interface IAdvertisementService {

    Advertisement find(long id) throws NotFoundByIdException;

    void create(Advertisement advertisement) throws NotFoundByIdException;

    void update(long id, Advertisement advertisement) throws NotFoundByIdException;

    void delete(long id) throws NotFoundByIdException;

    List<Advertisement> findAdvertisementsByCriterion(List<QueryCriteria> searchQueryCriteria, List<QueryCriteria> sortQueryCriteria);

    List<Advertisement> findSalesHistory(long id);

    void closeAdvertisement(long customerId, long advertisementId, double soldSum) throws NotFoundByIdException;

    void payForTopPlacing(long payerId, long advertisementId, double paidSum) throws NotFoundByIdException, NotEnoughBalanceMoneyException, EntityExistException;
}