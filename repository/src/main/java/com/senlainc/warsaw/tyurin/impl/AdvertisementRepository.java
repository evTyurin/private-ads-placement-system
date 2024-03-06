package com.senlainc.warsaw.tyurin.impl;

import com.senlainc.warsaw.tyurin.IAdvertisementRepository;
import com.senlainc.warsaw.tyurin.constants.SearchParameter;
import com.senlainc.warsaw.tyurin.entity.Advertisement;
import com.senlainc.warsaw.tyurin.entity.QueryCriteria;
import com.senlainc.warsaw.tyurin.enums.AdvertisementStatus;
import com.senlainc.warsaw.tyurin.enums.CriteriaInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdvertisementRepository extends AbstractRepository<Advertisement> implements IAdvertisementRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Advertisement> findCertificatesByCriterion(List<QueryCriteria> searchQueryCriteria, List<QueryCriteria> sortQueryCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Advertisement> criteriaQuery = criteriaBuilder.createQuery(Advertisement.class);
        Root<Advertisement> root = criteriaQuery.from(Advertisement.class);
        addOrdersAndPredicates(root, criteriaQuery, criteriaBuilder, searchQueryCriteria, sortQueryCriteria);
        return entityManager
                .createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public List<Advertisement> findSalesHistory(long id) {
        final String FIND_ADVERTISEMENT_BY_SELLER_ID = "SELECT advertisement FROM Advertisement advertisement WHERE advertisement.seller.id=:id and advertisement.soldDateTime IS NOT NULL ORDER BY advertisement.soldDateTime DESC";
        TypedQuery<Advertisement> query = entityManager.createQuery(FIND_ADVERTISEMENT_BY_SELLER_ID, Advertisement.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    private List<Predicate> getPredicates(CriteriaBuilder criteriaBuilder,
                                          List<QueryCriteria> searchQueryCriteria,
                                          Root<Advertisement> root) {
        List<Predicate> predicates = new ArrayList<>();
        for (QueryCriteria queryCriteria:searchQueryCriteria) {
            if (queryCriteria.getField().equals(SearchParameter.MIN_PRICE)) {
                predicates.add(criteriaBuilder.greaterThan(root
                                .get(CriteriaInfo.getEntityFieldName(queryCriteria.getField())),
                        queryCriteria.getValue()));
            }
            if (queryCriteria.getField().equals(SearchParameter.MAX_PRICE)) {
                predicates.add(criteriaBuilder.lessThan(root
                                .get(CriteriaInfo.getEntityFieldName(queryCriteria.getField())),
                        queryCriteria.getValue()));
            }
            if (queryCriteria.getField().equals(SearchParameter.TITLE)) {
                predicates.add(criteriaBuilder.like(root
                                .get(CriteriaInfo.getEntityFieldName(queryCriteria.getField())),
                        "%" + queryCriteria.getValue() + "%"));
            }
            if (queryCriteria.getField().equals(SearchParameter.STATUS)) {
                if (queryCriteria.getValue().equals(AdvertisementStatus.OPEN.name())) {
                    predicates.add(criteriaBuilder.equal(root
                                    .get(CriteriaInfo.getEntityFieldName(queryCriteria.getField())),
                            AdvertisementStatus.OPEN));
                } else {
                    predicates.add(criteriaBuilder.equal(root
                                    .get(CriteriaInfo.getEntityFieldName(queryCriteria.getField())),
                            AdvertisementStatus.CLOSED));
                }
            }
        }
        return predicates;
    }

    private List<Order> getOrders(CriteriaBuilder criteriaBuilder,
                                  List<QueryCriteria> orderQueryCriteria,
                                  Root<Advertisement> root) {
        List<Order> orders = new ArrayList<>();
        orders.add(criteriaBuilder.desc(root.get("isPaid")));
        orders.add(criteriaBuilder.asc(root.get("paidTillDateTime")));
        orders.add(criteriaBuilder.desc(root.get("seller").get("rating")));
        for (QueryCriteria queryCriteria:orderQueryCriteria) {
            if (queryCriteria.getValue().equals("asc")) {
                orders.add(criteriaBuilder.asc(root.get(CriteriaInfo.getEntityFieldName(queryCriteria.getField()))));
            } else {
                orders.add(criteriaBuilder.desc(root.get(CriteriaInfo.getEntityFieldName(queryCriteria.getField()))));
            }
        }
        return orders;
    }

    private void addOrdersAndPredicates(Root<Advertisement> root,
                                        CriteriaQuery criteriaQuery,
                                        CriteriaBuilder criteriaBuilder,
                                        List<QueryCriteria> searchQueryCriteria,
                                        List<QueryCriteria> orderQueryCriteria) {
        List<Predicate> predicates = getPredicates(criteriaBuilder, searchQueryCriteria, root);
        List<Order> orders = getOrders(criteriaBuilder, orderQueryCriteria, root);
        criteriaQuery.where(predicates.toArray(new Predicate[]{}));
        criteriaQuery.orderBy(orders);
        criteriaQuery.distinct(true);
    }
}
