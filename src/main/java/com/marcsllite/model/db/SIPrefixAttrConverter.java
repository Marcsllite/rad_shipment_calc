package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import jakarta.persistence.AttributeConverter;

public class SIPrefixAttrConverter implements AttributeConverter<Conversions.SIPrefix, String> {

    @Override
    public String convertToDatabaseColumn(Conversions.SIPrefix siPrefix) {
        if(siPrefix == null) {
            return null;
        }
        return siPrefix.getVal().toLowerCase();
    }

    @Override
    public Conversions.SIPrefix convertToEntityAttribute(String str) {
        if(str == null) {
            return null;
        }
        return Conversions.SIPrefix.toSIPrefix(str);
    }
}
