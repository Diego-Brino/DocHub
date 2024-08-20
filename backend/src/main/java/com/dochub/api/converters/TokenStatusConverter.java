package com.dochub.api.converters;

import com.dochub.api.enums.TokenStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TokenStatusConverter implements AttributeConverter<TokenStatus, String> {

    @Override
    public String convertToDatabaseColumn (TokenStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public TokenStatus convertToEntityAttribute (String dbData) {
        if (dbData == null) {
            return null;
        }

        for (TokenStatus status : TokenStatus.values()) {
            if (status.getCode().equals(dbData)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Unknown database value: " + dbData);
    }
}