package com.sap.cloud.cities.config;

import org.springframework.batch.item.file.separator.SimpleRecordSeparatorPolicy;

public class BlankLineRecordSeparationPolicy extends SimpleRecordSeparatorPolicy {
    public BlankLineRecordSeparationPolicy() {
    }

    public boolean isEndOfRecord(final String line) {
        return !line.trim().isEmpty() && super.isEndOfRecord(line);
    }

    public String postProcess(final String record) {
        return record.trim().isEmpty() ? null : super.postProcess(record);
    }
}
