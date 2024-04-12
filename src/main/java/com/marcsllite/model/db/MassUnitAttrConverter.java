package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MassUnitAttrConverter implements AttributeConverter<Conversions.MassUnit, String> {
    @Override
    public String convertToDatabaseColumn(Conversions.MassUnit massUnit) {
        if(massUnit == null) {
            return null;
        }
        return massUnit.getVal().toLowerCase();
    }

    @Override
    public Conversions.MassUnit convertToEntityAttribute(String str) {
        if(str == null) {
            return null;
        }
        return Conversions.MassUnit.toMass(str);
    }
}
