package com.marcsllite.model.db;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

class StateAttrConverterTest {
    StateAttrConverter converter = new StateAttrConverter();

    @Test
    void testConvertToDatabaseColumn_NullState() {
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    void testConvertToDatabaseColumn() {
        LimitsModelId.State state = LimitsModelId.State.SOLID;
        String expected = state.getVal();
        assertEquals(expected, converter.convertToDatabaseColumn(state));
    }

    @Test
    void testConvertToEntityAttribute_NullString() {
        assertNull(converter.convertToEntityAttribute(null));
    }
    
    @Test
    void testConvertToEntityAttribute() {
        LimitsModelId.State expected = LimitsModelId.State.SOLID;
        String str = expected.getVal();
        assertEquals(expected, converter.convertToEntityAttribute(str));
    }
}