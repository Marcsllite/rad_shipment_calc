package com.marcsllite.model.db;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StateAttrConverter implements AttributeConverter<LimitsModelId.State, String> {
    @Override
    public String convertToDatabaseColumn(LimitsModelId.State state) {
        if(state == null) {
            return null;
        }
        return state.getVal().toLowerCase();
    }

    @Override
    public LimitsModelId.State convertToEntityAttribute(String str) {
        if(str == null) {
            return null;
        }
        return LimitsModelId.State.toState(str);
    }
}
