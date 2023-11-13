package com.sap.cloud.cities.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.sap.cloud.cities.entities.City;
import com.sap.cloud.cities.enums.FilterCriteria;
import com.sap.cloud.cities.helper.SearchCriteria;

import javax.naming.OperationNotSupportedException;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class CitySearchPredicate {
    private final SearchCriteria criteria;

    public BooleanExpression getPredicate() {
        PathBuilder<City> entityPath = new PathBuilder<>(City.class, "city");

        if (isNumeric(criteria.getValue().toString())) {
            NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
            int value = Integer.parseInt(criteria.getValue().toString());
            return switch (criteria.getOperation()) {
                case EQUALS -> path.eq(value);
                case GREATER_THAN -> path.gt(value);
                case LESS_THAN -> path.lt(value);
                case GREATER_THAN_OR_EQUAL -> path.goe(value);
                case LESS_THAN_OR_EQUAL -> path.loe(value);
                default -> throw new IllegalArgumentException("Operation not supported");
            };
        }
        else {
            StringPath path = entityPath.getString(criteria.getKey());
            return switch (criteria.getOperation()) {
                case EQUALS -> path.eq(criteria.getValue().toString());
                case CONTAINS -> path.containsIgnoreCase(criteria.getValue().toString());
                default -> throw new IllegalArgumentException("Operation not supported");
            };
        }
    }

    public CitySearchPredicate(final SearchCriteria criteria) {
        this.criteria = criteria;
    }
}
