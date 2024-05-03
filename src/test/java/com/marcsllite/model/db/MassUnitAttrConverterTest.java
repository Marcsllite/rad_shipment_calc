package com.marcsllite.model.db;

import com.marcsllite.util.Conversions;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

class MassUnitAttrConverterTest {
    final MassUnitAttrConverter converter = new MassUnitAttrConverter();

    @Test
    void testConvertToDatabaseColumn_NullMass() {
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    void testConvertToDatabaseColumn() {
        Conversions.MassUnit form = Conversions.MassUnit.GRAMS;
        String expected = form.getVal().toLowerCase();
        assertEquals(expected, converter.convertToDatabaseColumn(form));
    }

    @Test
    void testConvertToEntityAttribute_NullString() {
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    void testConvertToEntityAttribute() {
        Conversions.MassUnit expected = Conversions.MassUnit.GRAMS;
        String str = expected.getVal();
        assertEquals(expected, converter.convertToEntityAttribute(str));
    }
}