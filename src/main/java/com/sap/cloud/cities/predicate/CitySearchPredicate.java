package com.sap.cloud.cities.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.sap.cloud.cities.entities.City;
import com.sap.cloud.cities.helper.SearchCriteria;

public class CitySearchPredicate {
    private final SearchCriteria criteria;

    public BooleanExpression getPredicate() {
        PathBuilder<City> entityPath = new PathBuilder<>(City.class, "city");
        StringPath path = entityPath.getString(this.criteria.getKey());
        return this.criteria.getOperation().equalsIgnoreCase("-") ? path.containsIgnoreCase(this.criteria.getValue().toString()) : null;
    }

    public CitySearchPredicate(final SearchCriteria criteria) {
        this.criteria = criteria;
    }
}
