package com.dochub.api.converters;

import com.dochub.api.enums.ResourceHistoryActionType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ResourceHistoryActionTypeConverter implements AttributeConverter<ResourceHistoryActionType, String> {

    @Override
    public String convertToDatabaseColumn (ResourceHistoryActionType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public ResourceHistoryActionType convertToEntityAttribute (String dbData) {
        if (dbData == null) {
            return null;
        }

        for (ResourceHistoryActionType status : ResourceHistoryActionType.values()) {
            if (status.getCode().equals(dbData)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Unknown database value: " + dbData);
    }
}