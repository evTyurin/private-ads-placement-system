package com.senlainc.warsaw.tyurin.service;

import com.senlainc.warsaw.tyurin.ISellerEvaluationRepository;
import com.senlainc.warsaw.tyurin.entity.Advertisement;
import com.senlainc.warsaw.tyurin.entity.SellerEvaluation;
import com.senlainc.warsaw.tyurin.entity.User;
import com.senlainc.warsaw.tyurin.service.exception.EntityExistException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByIdException;
import com.senlainc.warsaw.tyurin.service.exception.NotFoundByNameException;
import com.senlainc.warsaw.tyurin.service.impl.SellerEvaluationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SellerEvaluationServiceTest {

    @InjectMocks
    private SellerEvaluationService sellerEvaluationService;
    @Mock
    private IUserService userService;
    @Mock
    private IAdvertisementService advertisementService;
    @Mock
    private ISellerEvaluationRepository sellerEvaluationRepository;
    private User sender;
    private User receiver;
    private SellerEvaluation sellerEvaluation;
    private Advertisement advertisement;

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(userService, sellerEvaluationRepository, advertisementService);
    }

    @BeforeEach
    void init() {
        sender = new User();
        sender.setId(1);

        receiver = new User();
        receiver.setId(2);

        sellerEvaluation = new SellerEvaluation();
        sellerEvaluation.setId(1);
        advertisement = new Advertisement();
        advertisement.setId(1);
        advertisement.setSeller(sender);
        advertisement.setCustomer(receiver);
        sellerEvaluation.setAdvertisement(advertisement);
        sellerEvaluation.setUser(sender);
    }

    @Test
    void find_findSellerEvaluationByCorrectId_returnSellerEvaluation() throws NotFoundByIdException {
        when(sellerEvaluationRepository.findById(1)).thenReturn(Optional.of(sellerEvaluation));

        SellerEvaluation actualSellerEvaluation = sellerEvaluationRepository.findById(1).orElseThrow(() -> new NotFoundByIdException(1, 404));
        assertEquals(sellerEvaluation, actualSellerEvaluation);
    }

    @Test
    void create_createSellerEvaluation_createSuccessfully() throws NotFoundByIdException, EntityExistException, NotFoundByNameException {
        when(advertisementService.find(1)).thenReturn(advertisement);
        when(userService.find(1)).thenReturn(sender);

        sellerEvaluationService.create(sellerEvaluation);

        verify(sellerEvaluationRepository).create(sellerEvaluation);
    }
}
