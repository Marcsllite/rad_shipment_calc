package com.marcsllite.model.db;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class FormAttrConverter implements AttributeConverter<LimitsModelId.Form, String> {
    @Override
    public String convertToDatabaseColumn(LimitsModelId.Form form) {
        if(form == null) {
            return null;
        }
        return form.getVal().toLowerCase();
    }

    @Override
    public LimitsModelId.Form convertToEntityAttribute(String str) {
        if(str == null) {
            return null;
        }
        return LimitsModelId.Form.toForm(str);
    }
}
