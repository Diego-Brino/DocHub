package com.dochub.api.converters;

import com.dochub.api.enums.ResourceOrigin;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ResourceOriginConverter implements AttributeConverter<ResourceOrigin, String> {

    @Override
    public String convertToDatabaseColumn (ResourceOrigin attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public ResourceOrigin convertToEntityAttribute (String dbData) {
        if (dbData == null) {
            return null;
        }

        for (ResourceOrigin status : ResourceOrigin.values()) {
            if (status.getCode().equals(dbData)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Unknown database value: " + dbData);
    }
}