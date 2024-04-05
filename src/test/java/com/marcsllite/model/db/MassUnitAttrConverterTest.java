package com.marcsllite.model.db;

import com.marcsllite.model.Isotope;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

class MassUnitAttrConverterTest {
    MassUnitAttrConverter converter = new MassUnitAttrConverter();

    @Test
    void testConvertToDatabaseColumn_NullMass() {
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    void testConvertToDatabaseColumn() {
        Isotope.MassUnit form = Isotope.MassUnit.GRAMS;
        String expected = form.getVal().toLowerCase();
        assertEquals(expected, converter.convertToDatabaseColumn(form));
    }

    @Test
    void testConvertToEntityAttribute_NullString() {
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    void testConvertToEntityAttribute() {
        Isotope.MassUnit expected = Isotope.MassUnit.GRAMS;
        String str = expected.getVal();
        assertEquals(expected, converter.convertToEntityAttribute(str));
    }
}