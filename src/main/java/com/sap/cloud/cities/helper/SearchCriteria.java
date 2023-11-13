package com.sap.cloud.cities.helper;

import com.sap.cloud.cities.enums.FilterCriteria;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private FilterCriteria operation;
    private Object value;
}
