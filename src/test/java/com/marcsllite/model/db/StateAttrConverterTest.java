package com.marcsllite.model.db;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class StateAttrConverterTest {
    StateAttrConverter converter = new StateAttrConverter();

    @Test
    public void testConvertToDatabaseColumn_NullState() {
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    public void testConvertToDatabaseColumn() {
        LimitsModelId.State state = LimitsModelId.State.SOLID;
        String expected = state.val;
        assertEquals(expected, converter.convertToDatabaseColumn(state));
    }

    @Test
    public void testConvertToEntityAttribute_NullString() {
        assertNull(converter.convertToEntityAttribute(null));
    }
    
    @Test
    public void testConvertToEntityAttribute() {
        LimitsModelId.State expected = LimitsModelId.State.SOLID;
        String str = expected.val;
        assertEquals(expected, converter.convertToEntityAttribute(str));
    }
}