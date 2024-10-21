package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SIPrefixAttrConverterTest {
    final SIPrefixAttrConverter converter = new SIPrefixAttrConverter();

    @Test
    void testConvertToDatabaseColumn_NullState() {
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    void testConvertToDatabaseColumn() {
        Conversions.SIPrefix state = Conversions.SIPrefix.YOTTA;
        String expected = state.getVal().toLowerCase();
        assertEquals(expected, converter.convertToDatabaseColumn(state));
    }

    @Test
    void testConvertToEntityAttribute_NullString() {
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    void testConvertToEntityAttribute() {
        Conversions.SIPrefix expected = Conversions.SIPrefix.HECTO;
        String str = expected.getVal();
        assertEquals(expected, converter.convertToEntityAttribute(str));
        assertEquals(expected, converter.convertToEntityAttribute(str.toUpperCase()));
    }
}