package com.marcsllite.model.db;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

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
        return Stream.of(LimitsModelId.State.values())
            .filter(s -> s.getVal().equalsIgnoreCase(str))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
