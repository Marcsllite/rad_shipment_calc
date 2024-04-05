package com.marcsllite.model.db;

import com.marcsllite.model.Isotope;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MassUnitAttrConverter implements AttributeConverter<Isotope.MassUnit, String> {
    @Override
    public String convertToDatabaseColumn(Isotope.MassUnit massUnit) {
        if(massUnit == null) {
            return null;
        }
        return massUnit.getVal().toLowerCase();
    }

    @Override
    public Isotope.MassUnit convertToEntityAttribute(String str) {
        if(str == null) {
            return null;
        }
        return Isotope.MassUnit.toMass(str);
    }
}
