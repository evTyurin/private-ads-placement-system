package com.senlainc.warsaw.tyurin.controller.util;

import com.senlainc.warsaw.tyurin.constants.ExceptionCode;
import com.senlainc.warsaw.tyurin.constants.SearchParameter;
import com.senlainc.warsaw.tyurin.entity.QueryCriteria;
import com.senlainc.warsaw.tyurin.service.exception.InvalidUrlParameterException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QueryCriteriaBuilder {

    /**
     * Create part of query for sorting gift certificates by sort criterion
     *
     * @param criteria criteria used for sort
     * @return list of query criterion used for sort
     * @throws InvalidUrlParameterException indicates that criterion spelling is incorrect
     */
    public List<QueryCriteria> createSortOrder(String criteria) throws InvalidUrlParameterException {
        List<QueryCriteria> queryCriterion = new ArrayList<>();
        int separatorPosition;
        if (criteria != null) {
            List<String> sortCriterion = Arrays.stream(criteria.split(",")).collect(Collectors.toList());
            for (String sortCriteria : sortCriterion) {
                separatorPosition = sortCriteria.indexOf("-");
                if (separatorPosition > 0) {
                    queryCriterion.add(new QueryCriteria(
                            sortCriteria.substring(0, separatorPosition),
                            sortCriteria.substring(separatorPosition + 1))
                    );
                } else {
                    throw new InvalidUrlParameterException(sortCriteria, ExceptionCode.EXPECTATION_FAILED_EXCEPTION_CODE);
                }
            }
        }
        return queryCriterion;
    }

    /**
     * Create part of query for searching gift certificates by different criterion
     *
     * @param minPrice        min price of advertisement
     * @param maxPrice        max price of advertisement
     * @param title           title of advertisement
     * @return list of query criterion used for search
     */
    public List<QueryCriteria> createSearchCriteria(String minPrice, String maxPrice, String title) {
        List<QueryCriteria> queryCriterion = new ArrayList<>();
        if (minPrice != null) {
            queryCriterion.add(new QueryCriteria(SearchParameter.MIN_PRICE, minPrice));
        }
        if (maxPrice != null) {
            queryCriterion.add(new QueryCriteria(SearchParameter.MAX_PRICE, maxPrice));
        }
        if (title != null) {
            queryCriterion.add(new QueryCriteria(SearchParameter.TITLE, title));
        }
        return queryCriterion;
    }
}
