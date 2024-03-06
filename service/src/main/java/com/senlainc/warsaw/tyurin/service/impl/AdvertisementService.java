package com.senlainc.warsaw.tyurin.service.impl;

import com.senlainc.warsaw.tyurin.IAdvertisementRepository;
import com.senlainc.warsaw.tyurin.constants.ExceptionCode;
import com.senlainc.warsaw.tyurin.entity.Advertisement;
import com.senlainc.warsaw.tyurin.entity.QueryCriteria;
import com.senlainc.warsaw.tyurin.entity.User;
import com.senlainc.warsaw.tyurin.enums.AdvertisementStatus;
import com.senlainc.warsaw.tyurin.service.IAdvertisementService;
import com.senlainc.warsaw.tyurin.service.IUserService;
import com.senlainc.warsaw.tyurin.service.exception.EntityExistException;
import com.senlainc.warsaw.tyurin.service.exception.NotEnoughBalanceMoneyException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AdvertisementService implements IAdvertisementService {

    private IAdvertisementRepository advertisementRepository;
    private IUserService userService;

    public AdvertisementService(IAdvertisementRepository advertisementRepository,
                                IUserService userService) {
        this.advertisementRepository = advertisementRepository;
        this.userService = userService;
    }

    @Override
    public Advertisement find(long id) throws NotFoundByIdException {
        return advertisementRepository.findById(id)
                .orElseThrow(()
                        -> new NotFoundByIdException(id, ExceptionCode.NOT_FOUND_EXCEPTION_CODE));
    }

    @Override
    public void create(Advertisement advertisement) throws NotFoundByIdException {
        User seller = userService.find(advertisement.getSeller().getId());
        advertisement.setSeller(seller);
        advertisementRepository.create(advertisement);
    }

    @Override
    public void update(long id, Advertisement updatedAdvertisement) throws NotFoundByIdException {
        Advertisement advertisement = find(id);

        long sellerId = updatedAdvertisement.getSeller().getId();
        User updatedCustomer = userService.find(sellerId);
        advertisement.setSeller(updatedCustomer);
        advertisement.setTitle(updatedAdvertisement.getTitle());
        advertisement.setDescription(updatedAdvertisement.getDescription());
        advertisement.setPrice(updatedAdvertisement.getPrice());

        advertisementRepository.update(advertisement);
    }

    @Override
    public void delete(long id) throws NotFoundByIdException {
        Advertisement advertisement = find(id);
        advertisementRepository.delete(advertisement);
    }

    @Override
    public List<Advertisement> findAdvertisementsByCriterion(List<QueryCriteria> searchQueryCriteria, List<QueryCriteria> sortQueryCriteria) {
        return advertisementRepository.findCertificatesByCriterion(searchQueryCriteria, sortQueryCriteria);
    }

    @Override
    public List<Advertisement> findSalesHistory(long id) {
        return advertisementRepository.findSalesHistory(id);
    }

    @Override
    public void closeAdvertisement(long customerId, long advertisementId, double soldSum) throws NotFoundByIdException {
        Advertisement advertisement = find(advertisementId);
        User customer = userService.find(customerId);
        advertisement.setCustomer(customer);
        advertisement.setSoldSum(soldSum);
        advertisement.setStatus(AdvertisementStatus.CLOSED);
        advertisement.setSoldDateTime(LocalDateTime.now());
        advertisementRepository.update(advertisement);
    }

    @Transactional
    @Override
    public void payForTopPlacing(long payerId, long advertisementId, double paidSum) throws NotFoundByIdException, NotEnoughBalanceMoneyException, EntityExistException {
        Advertisement advertisement = find(advertisementId);
        User user = userService.find(payerId);
        double userBalance = user.getBalance() - paidSum;
        if (userBalance < 0) {
            throw new NotEnoughBalanceMoneyException(paidSum, 5555);
        }
        user.setBalance(userBalance);
        userService.update(user.getId(), user);
        advertisement.setPaid(true);
        advertisement.setSeller(user);
        advertisement.setPaidTillDateTime(LocalDateTime.now().plusDays(7));
        update(advertisementId, advertisement);
    }
}
