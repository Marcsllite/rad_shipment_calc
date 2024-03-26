package com.marcsllite.model.db;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter
public class FormAttrConverter implements AttributeConverter<LimitsModelId.Form, String> {
    @Override
    public String convertToDatabaseColumn(LimitsModelId.Form form) {
        if(form == null) {
            return null;
        }
        return form.getVal();
    }

    @Override
    public LimitsModelId.Form convertToEntityAttribute(String str) {
        if(str == null) {
            return null;
        }
        return Stream.of(LimitsModelId.Form.values())
            .filter(f -> f.getVal().equalsIgnoreCase(str))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
