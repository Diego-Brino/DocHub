package com.dochub.api.converters;

import com.dochub.api.enums.ProcessStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProcessStatusConverter implements AttributeConverter<ProcessStatus, String> {

    @Override
    public String convertToDatabaseColumn (ProcessStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public ProcessStatus convertToEntityAttribute (String dbData) {
        if (dbData == null) {
            return null;
        }

        for (ProcessStatus status : ProcessStatus.values()) {
            if (status.getCode().equals(dbData)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Unknown database value: " + dbData);
    }
}