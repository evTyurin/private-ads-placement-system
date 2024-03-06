package com.senlainc.warsaw.tyurin;

import com.senlainc.warsaw.tyurin.entity.Advertisement;
import com.senlainc.warsaw.tyurin.entity.QueryCriteria;

import java.util.List;
import java.util.Optional;

public interface IAdvertisementRepository {

    Optional<Advertisement> findById(long id);

    void create(Advertisement advertisement);

    List<Advertisement> findCertificatesByCriterion(List<QueryCriteria> searchQueryCriteria, List<QueryCriteria> sortQueryCriteria);

    void deleteById(long id);

    void update(Advertisement advertisement);

    void delete(Advertisement advertisement);

    List<Advertisement> findSalesHistory(long id);
}