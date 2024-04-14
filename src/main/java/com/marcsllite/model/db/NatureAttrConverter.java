package com.marcsllite.model.db;

import com.marcsllite.model.Nuclide;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class NatureAttrConverter implements AttributeConverter<Nuclide.Nature, String> {
    @Override
    public String convertToDatabaseColumn(Nuclide.Nature nature) {
        if(nature == null) {
            return null;
        }
        return nature.getVal().toLowerCase();
    }

    @Override
    public Nuclide.Nature convertToEntityAttribute(String str) {
        if(str == null) {
            return null;
        }
        return Nuclide.Nature.toNature(str);
    }
}
