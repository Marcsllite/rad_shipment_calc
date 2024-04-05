package com.marcsllite.model.db;

import com.marcsllite.model.Isotope;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class NatureAttrConverter implements AttributeConverter<Isotope.Nature, String> {
    @Override
    public String convertToDatabaseColumn(Isotope.Nature nature) {
        if(nature == null) {
            return null;
        }
        return nature.getVal().toLowerCase();
    }

    @Override
    public Isotope.Nature convertToEntityAttribute(String str) {
        if(str == null) {
            return null;
        }
        return Isotope.Nature.toNature(str);
    }
}
