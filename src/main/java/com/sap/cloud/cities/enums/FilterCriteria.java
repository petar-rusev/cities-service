package com.sap.cloud.cities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;

@Getter
@AllArgsConstructor
public enum FilterCriteria {
    CONTAINS("-"),
    EQUALS(":"),
    GREATER_THAN("gt"),
    LESS_THAN("lt"),
    GREATER_THAN_OR_EQUAL("gte"),
    LESS_THAN_OR_EQUAL("lte");

    private final String criteria;

    public static FilterCriteria fromString(String value) {
        for (FilterCriteria criteria : values()) {
            if (criteria.getCriteria().equals(value)) {
                return criteria;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid value '%s' for filters given; Has to be one of 'c, e, >, <, =>, <='", value));
    }

}
