package com.senlainc.warsaw.tyurin.controller.util;

import com.senlainc.warsaw.tyurin.constants.ExceptionCode;
import com.senlainc.warsaw.tyurin.constants.SearchParameter;
import com.senlainc.warsaw.tyurin.entity.QueryCriteria;
import com.senlainc.warsaw.tyurin.enums.CriteriaInfo;
import com.senlainc.warsaw.tyurin.service.exception.InvalidUrlParameterException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The validator for QueryCriteria come from URL.
 * Checks if names of all criteria are correct.
 */
@Component
public class QueryCriteriaValidator {

    /**
     * Filter all criterion from url for criterion for sort
     *
     * @param criterion list of QueryCriteria
     * @throws InvalidUrlParameterException indicates that criterion spelling is incorrect
     */
    public void sortCriteriaValidation(List<QueryCriteria> criterion) throws InvalidUrlParameterException {
        for (QueryCriteria criteria : criterion) {
            if (!(criteria.getValue().equals("asc") || criteria.getValue().equals("desc"))) {
                throw new InvalidUrlParameterException(criteria.getField() + "=" + criteria.getValue(), ExceptionCode.EXPECTATION_FAILED_EXCEPTION_CODE);
            }
            if (!CriteriaInfo.isExist(criteria.getField())) {
                throw new InvalidUrlParameterException(criteria.getField() + "=" + criteria.getValue(), ExceptionCode.EXPECTATION_FAILED_EXCEPTION_CODE);
            }
        }
    }

    /**
     * Filter all criterion from url for criterion for search
     *
     * @param criterion list of QueryCriteria
     * @throws InvalidUrlParameterException indicates that criterion spelling is incorrect
     */
    public void searchCriteriaValidation(List<QueryCriteria> criterion) throws InvalidUrlParameterException {
        for (QueryCriteria criteria : criterion) {
            if (!CriteriaInfo.isExist(criteria.getField())) {
                throw new InvalidUrlParameterException(criteria.getField() + "=" + criteria.getValue(), ExceptionCode.EXPECTATION_FAILED_EXCEPTION_CODE);            }
            if (criteria.getField().equals(SearchParameter.STATUS)) {
                if (!(criteria.getValue().equals("OPEN") || criteria.getValue().equals("CLOSED"))) {
                    throw new InvalidUrlParameterException(criteria.getField() + "=" + criteria.getValue(), ExceptionCode.EXPECTATION_FAILED_EXCEPTION_CODE);
                }
            }
            if (criteria.getField().equals(SearchParameter.MIN_PRICE) || criteria.getField().equals(SearchParameter.MAX_PRICE)) {
                if (!StringUtils.isNumeric(criteria.getValue())) {
                    throw new InvalidUrlParameterException(criteria.getField() + "=" + criteria.getValue(), ExceptionCode.EXPECTATION_FAILED_EXCEPTION_CODE);
                }
            }
        }
    }
}