package com.sap.cloud.cities.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.sap.cloud.cities.helper.SearchCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CityPredicateBuilder {
    private final List<SearchCriteria> params = new ArrayList<>();

    public CityPredicateBuilder() {
    }

    public void with(String key, String operation, Object value) {
        this.params.add(new SearchCriteria(key, operation, value));
    }

    public BooleanExpression build() {
        if (this.params.isEmpty()) {
            return null;
        } else {
            List<BooleanExpression> predicates = this.params.stream().map((param) -> {
                CitySearchPredicate predicate = new CitySearchPredicate(param);
                return predicate.getPredicate();
            }).filter(Objects::nonNull).toList();

            BooleanExpression result = Expressions.asBoolean(true).isTrue();
            for (BooleanExpression predicate : predicates) {
                result = result.and(predicate);
            }

            return result;
        }
    }
}
