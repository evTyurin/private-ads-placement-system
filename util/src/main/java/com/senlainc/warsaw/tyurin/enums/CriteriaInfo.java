package com.senlainc.warsaw.tyurin.enums;

public enum CriteriaInfo {
    MIN_PRICE("minPrice", "price"),
    MAX_PRICE("maxPrice", "price"),
    TITLE("title", "title"),
    STATUS("status", "status"),
    CREATION_TIME("creationTime", "creationDateTime"),
    PRICE("price", "price");

    private final String criteriaName;
    private final String entityFieldName;

    CriteriaInfo(String criteriaName, String entityFieldName) {
        this.criteriaName = criteriaName;
        this.entityFieldName = entityFieldName;
    }

    public static String getEntityFieldName(String criteriaName) {
        for (CriteriaInfo criteriaInfo:CriteriaInfo.values()) {
            if (criteriaInfo.criteriaName.equals(criteriaName)) {
                return criteriaInfo.entityFieldName;
            }
        }
        return criteriaName;
    }

    public static boolean isExist(String criteriaName) {
        for (CriteriaInfo criteriaInfo:CriteriaInfo.values()) {
            if (criteriaInfo.criteriaName.equals(criteriaName)) {
                return true;
            }
        }
        return false;
    }
}