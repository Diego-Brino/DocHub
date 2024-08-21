package com.dochub.api.converters;

import com.dochub.api.enums.RoleStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleStatusConverter implements AttributeConverter<RoleStatus, String> {

    @Override
    public String convertToDatabaseColumn (RoleStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public RoleStatus convertToEntityAttribute (String dbData) {
        if (dbData == null) {
            return null;
        }

        for (RoleStatus status : RoleStatus.values()) {
            if (status.getCode().equals(dbData)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Unknown database value: " + dbData);
    }
}