package com.dochub.api.converters;

import com.dochub.api.enums.ResourceHistoryAction;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ResourceHistoryActionConverter implements AttributeConverter<ResourceHistoryAction, String> {

    @Override
    public String convertToDatabaseColumn (ResourceHistoryAction attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public ResourceHistoryAction convertToEntityAttribute (String dbData) {
        if (dbData == null) {
            return null;
        }

        for (ResourceHistoryAction status : ResourceHistoryAction.values()) {
            if (status.getCode().equals(dbData)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Unknown database value: " + dbData);
    }
}