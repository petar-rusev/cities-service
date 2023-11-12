package com.sap.cloud.cities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;
@Getter
@AllArgsConstructor
public enum SortField {
    NAME("name"),
    AREA("area"),
    POPULATION("population"),
    DENSITY("density");

    private final String databaseField;

    public static SortField fromString(String value) {
        try {
            return valueOf(value.toUpperCase(Locale.US));
        } catch (Exception exception) {
            throw new IllegalArgumentException(String.format("Invalid value '%s' for orders given; Has to be one of 'name, area, population' (case insensitive)", value), exception);
        }
    }
}
