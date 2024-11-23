package com.dochub.api.converters;

import com.dochub.api.enums.RequestStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RequestStatusConverter implements AttributeConverter<RequestStatus, String> {

    @Override
    public String convertToDatabaseColumn (RequestStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public RequestStatus convertToEntityAttribute (String dbData) {
        if (dbData == null) {
            return null;
        }

        for (RequestStatus status : RequestStatus.values()) {
            if (status.getCode().equals(dbData)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Unknown database value: " + dbData);
    }
}